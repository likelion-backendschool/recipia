## 팀 구성원

---

• [팀장] 김지훈, 도성구, 김상훈, 이소영 서진수

## 회고 내용 요약 (최소 500자 이상)

---

팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂
<details>
  <summary>지훈님 회고록</summary>
  <div markdown="1">

## Spring Batch(스프링 배치)

---

### Batch란

대용량의 데이터를 실시간으로 처리하는 것이 아니라 일괄적으로 모아서 작업하는 것을 배치 작업이라고 한다. 예를 들어 로그인은 각각 개별적인 처리를 하는 것이 효율적이지만 모든 회원에게 메일을 보내는 작업은 일괄적으로 처리하는 것이 효율적이다. 이렇듯 배치 작업은 일반적으로, 정해진 시간에 대량의 데이터를 일괄적으로 처리한다라고도 정의할 수 있다.

### Spring Batch 구조

![https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%84%B1%E3%85%88%E3%85%8E.PNG](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%8E1.png)

- JobRepostitory나 JobLauncher는 건드리지 않아도 된다.
- job이나 step, 하위 클래스들을 개발한다.
- job은 여러개의 step들로 구성된다.
- job은 똑같은 조건이면 다시 실행되지 않는다.
    - 예를들어 한번 실행했던 작업이 있다면 그 정보를 따로 저장해 재실행 여부를 정한다.

### Batch 주요 용어

1. **Job**
    
    실행시킬 작업을 의미로, 논리적인 Job 실행의 개념이다.
    
2. **batch 메타 테이블**
    - spring batch가 제공하는 가장 기본적인 기능
    - 배치 작업 하는동안 사용되는 모든 메타정보들을 저장
        - 예를들어 작업시간, 파라미터, 정상수행 여부
    - 이를 통한 모니터링 용도로 사용가능
3. Step
    
    Job 내부에서 수행될 1개의 step
    

<aside>
🌟 **Tasklet vs Chunk
- 간단하게는 Tasklet, 스프링배치는 Chunk 지향

Task 기반: 하나의 작업 기반으로 실행
Chunk 기반: 하나의 큰 덩어리를 n번씩 나눠서 실행**

</aside>

### 배치 실습 환경

![https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%8E2.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%8E2.png)

### 배치앱으로 등록

@EnableBatchProcessing 애너테이션을 꼭 붙여줘야지 작업이 진행된다. 

```java
**@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchTestApplication.class, args);
	}

}**
```

### 배치 Job을 생성

```java
**@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("helloWorldJob")
                .start(helloWorldStep())
                .build();
    }

    @JobScope
    @Bean
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet(helloWorldTasklet())
                .build();
    }

    @StepScope
    @Bean
    public Tasklet helloWorldTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("스프링 배치");
                return RepeatStatus.FINISHED;
            }
        };
    }
}**
```

### ${job.name} 의미

Job 의 환경변수명을 참조하는 구문이다. 만일 없다면 조건을 걸어줄 수 있는데 이번 HelloWorld 실습에선 helloworldJob을 적어줬다.

```yaml
**spring:
  batch:
    job:
      names: ${job.name:helloWorldJob}
    jdbc:
      initialize-schema: ALWAYS
  datasource:
    url: jdbc:mariadb://127.0.0.1:3306/app_2022_09_22?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul
    driver-class-name:
    username: root
    password: roger3495
  jpa:
    show-sql: true**
```

### 결과화면

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e74410c6-ee8e-4eab-8c61-e4ef528650c1/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-09-22_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_10.25.03.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%8E3.png)

<aside>
🌟 Tip. **결과화면 알록달록하게 꾸며주기**

```yaml
**spring:
  output:
    ansi:
      enabled: always**
```

</aside>

### DB 복사 명령어

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/72d75d7c-36d1-4112-bed5-7b482029aaf0/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%8E4.png)

```bash
**mysqldump -u root app_2022_09_22 > app_2022_09_22.sql
mysql -u root app_2022_09_22_backup < app_2022_09_22.sql**
```

### Scope의 뜻

스프링부트는 객체마다 생명주기를 관리해줄 수 있다. Bean보다 생명주기가 더 짧은 단위가 스코프다. 

- 스프링에서 생명주기 단위
    
    싱글톤 => 스프링부트앱이 꺼지기 전까지 살아있음
    세션 => 브라우저당 객체가 1개씩
    레쿼스트 => 요청당 객체가 1개
    프로토타입 => 그냥 매번 새로 만듬
    

**추천 링크**

[Quick Guide to Spring Bean Scopes | Baeldung](https://www.baeldung.com/spring-bean-scopes)

### JobScope & StepScope

생명주기가 짧으므로 메모리 낭비가 없다. 특정 job이랑 step이 실행될때 그때 객체가 만들어진다.

Bean은 프로그램이 실행되는 순간 다 등록된다. 그래서 메모리 낭비가 Bean보다 덜하다.

**참고자료**

[SpringBoot Batch 정리](https://velog.io/@mooh2jj/SpringBoot-Batch)
    
  </div>
</details>


<details>
  <summary>상훈님 회고록</summary>
  <div markdown="1">

### 젠킨스-webhook

<aside>
💡 **젠킨스에 Repository등록하기**

- ngrok 사이트 로그인 하고 ngrok설치후에는 **cmd 에 config 키**를 입력해줘야 
포트접속 도메인 생성가능!
- ngrok http 8081⇒**Window** 에서 **CentOs** 로 가는 8081 포트 생성⇒**젠킨스 포트번호**가 8081 이기때문에 외부에서 젠킨스로 도메인 이동 가능
- 소스코드관리→Git Repository URL 등록
</aside>

<aside>
💡 **github webhook 으로 젠킨스 빌드 설정**

- Repository Setting 안에 Webhooks 에서 Payload URL 를 상대주소(192.168.56…)대신
ngrok 주소+/github-webhook/ 로작성
- 젠킨스에 GitHub hook trigger 속성 추가

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/52af6827-35cf-4680-92a6-5ae2d35084ec/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%85%8E1.png)

</aside>

<aside>
💡 **github webhook 으로 젠킨스 빌드 설정(private)**

- 젠킨스 설정인 Git Repository URL 에 
**github access token** +**@github.com/유저/repostirory** 로 작성
- github webhook에 기능은 단순히 수정한 코드가 push 되면 빌드 해구기!
</aside>

<aside>
💡 **github webhook 으로 젠킨스 빌드 설정(대체키)**

- 한정된 Repository와 젠킨스가 서로이용할수있게해주는 비밀키+대체키 이용방식

```bash
#도커 내부 젠킨스로 들어가 비밀키 발급
docker exec -it jenkins_1 bash -c "ssh-keygen -t rsa -b 4096"

#비밀키 확인
docker exec jenkins_1 cat /root/.ssh/id_rsa

#공개키 확인
docker exec jenkins_1 cat /root/.ssh/id_rsa.pub
```

- 젠킨스 관리→manage credentials→enter directly에 비밀키 추가
- settings→deploy keys 에 공개키 추가
- **SSH 방식**은 Credentials 에 이전에 비밀키를 입력한것을 사용하면 된다!
</aside>

<aside>
💡 **젠킨스 안에 빌드하기 위해선 자바와 도커 설치필요**

- java11버전→최신버전으로 변경
- **2가지 방법 있음**

```bash
#1.젠킨스 컨테이너에 접속해서 설치
docker exec -it jenkins_1 bash

apt-get update

apt-get install openjdk-17-jdk -y

exit

#2.HOST OS 에서 컨테이너로 명령 전송해서 설치
docker exec jenkins_1 apt-get update

docker exec jenkins_1 apt-get install openjdk-17-jdk -y
```

</aside>

<aside>
💡 **젠킨스 컨테이너 JAVA_HOME 환경변수 변경(쉘+브라우저)**

- 쉘 방식

```bash
#초고속 검색 mlocate 설치
apt-get install mlocate

#검색 업데이트
updatedb

#검색
locate java | fgrep 17 | fgrep javac
#찾음 : /usr/lib/jvm/java-17-openjdk-amd64

#JAVA_HOME 변경
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
#환경변수 출력
echo $JAVA_HOME
```

- 브라우저 방식
브라우저로 젠킨스 관리에 접속하여
JDK 설정을  /usr/lib/jvm/java-17-openjdk-amd64  변경
</aside>

<aside>
💡 **젠킨스 컨테이너안에 도커 설치**

```bash
apt-get update -y
apt-get install -y ca-certificates curl gnupg lsb-release
mkdir -p /etc/apt/keyrings
rm /etc/apt/keyrings/docker.gpg
curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
apt-get update
apt-get install -y docker-ce docker-ce-cli docker-compose-plugin
exit
```

- **젠킨스 안에 도커 설치된지 확인하기(젠킨스 컨테이너에 들어가서 도커 설치된지확인)**

```bash
#젠킨스 컨테이너로 들어가기
docker exec -it jenkins_1 bash
#docker 작동 시켜보기
docker
#docker compose 작동 시켜복
docker compose

exit
```

</aside>

<aside>
💡 **도커에 mariadb 설치및 실행**

```bash
docker run \
  --name mariadb_1 \
  -d \
  --restart unless-stopped \
  -e MARIADB_ROOT_PASSWORD=lldj123414 \
  -e TZ=Asia/Seoul \
  -p 3306:3306 \
  -v /docker_projects/mariadb_1/conf.d:/etc/mysql/conf.d \
  -v /docker_projects/mariadb_1/mysql:/var/lib/mysql \
  -v /docker_projects/mariadb_1/run/mysqld:/run/mysqld/ \
  mariadb:latest
```

- **mariadb 보안설정**

```bash
#mariadb 에 접속하여 보안설정진행실행
docker exec -it mariadb_1 /usr/bin/mariadb-secure-installation
#Switch to unix_socket authentication [Y/n] n 하기
#Change the root password? [Y/n] n 하기
#나머지 Y 하기
```

</aside>

<aside>
💡 **마스터계정및 DB생성**

```bash
#mysql 접속하여 마스터 계정으로 로그인
docker exec -it mariadb_1 mysql -u (root) -p(비밀번호)

```

```sql
#계정 생성
GRANT ALL PRIVILEGES ON *.* TO lldj@'%' IDENTIFIED BY 'lldj123414';
GRANT ALL PRIVILEGES ON *.* TO lldjlocal@127.0.0.1 IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO lldjlocal@'172.17.%.%' IDENTIFIED BY '1234';

#DB생성
CREATE DATABASE nginx;
CREATE DATABASE app20220916_2__test;
CREATE DATABASE app20220916_2;

USE app20220916_2;

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert  into `article`(`id`,`subject`) values 
(1,'제목1'),
(2,'제목2');
```

</aside>

<aside>
💡 **스프링 어플리케이션 빌드 및 컨테이너 생성**

- **사전준비**

```bash
#git 설치
sudo yum install git -y

#java-17-openjdk 설치
#만약에 yum 다운로드 속도가 많이 느리다면
#Ctrl + C 후 다시 설치시도하면 빨리됨
sudo yum install java-17-openjdk -y
sudo yum install java-17-openjdk-devel -y
```

- **프로젝트 폴더 경로 지정**⇒/docker_projects/sbdb_1/project 에설치할꺼
- **프로젝트 새로 설정 및 생성**

```bash
#디렉토리 생성
sudo mkdir /docker_projects

#프로젝트 삭제
sudo rm -rf /docker_projects/sbdb_1/project

#프로젝트 디렉토리 생성
sudo mkdir -p /docker_projects/sbdb_1/project

#폴더 권한부여
sudo chown lldj: -R /docker_projects/sbdb_1/project

#프로젝트 경로로 이동
cd /docker_projects/sbdb_1/project

#프로젝트 깃으로 복사(root 아래 디렉토리로 )
git clone https://github.com/jhs512/app20220916_3 .
```

- **프로젝트 빌드**

```bash
#./gardlew에대한 작업 권한 부여
chmod 744 ./gradlew

#클린빌드
./gradlew clean build
```

- **실행중인 프로젝트 컨테이너 중지**

```bash
#프로젝트 컨테이너 멈추기
docker stop sbdb_1

#안되면 : docker kill sbdb_1

#프로젝트 컨테이너 제거
docker rm -f sbdb_1

```

- ****새 sbdb 이미지 만들기****

```bash
# git pull
git pull origin master

#클린빌드
./gradlew clean build

#도커 이미지 만들기
docker build -t sbdb .

# 이미지 실행
docker run \
  --name=sbdb_1 \
  -p 8080:8080 \
  -v /docker_projects/sbdb_1/volumes/gen:/gen \
  --restart unless-stopped \
  -e TZ=Asia/Seoul \
  -d \
  sbdb
```

</aside>

<aside>
💡 **젠킨스  내부에서 어플리케이션 빌드시 작동안되는이유⇒
이전에는 같은 위치에 Mysql 이 있었지만 현재는 어플리케이션은
젠킨스 내부에 있어서 Mysql 접속방식 변화가 필요**

</aside>

<aside>
💡 **젠킨스 내부에서 외부Mysql 과 통신하기**

- 아래 yml 파일 처럼 ${변수:} 선언하면 build시 변수로 여러 설정 동적변경가능

```xml

spring:
  datasource:
    url: jdbc:mariadb://${testDbIp:127.0.0.1}:3306/app20220916_2__test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul
    username: ${testDbId:root}
    password: ${testDbPw:}
  jpa:
    hibernate:
      ddl-auto: create
```

- 젠킨스에 들어가서 build 시 yml 설정해주기

```bash
#젠킨스 컨테이너 안으로 접속
docker exec -it jenkins_1 bash

#build 디렉토리접근
cd /var/jenkins_home/workspace/sbdb_1

#JAVA_HOME 설정 변경
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

#그래들 권한 부여
chmod 744 ./gradlew

#아래조건으로 그래들 실행!!
testDbIp=172.17.0.1 testDbId=lldjlocal testDbPw=1234 ./gradlew clean build

```

</aside>

  </div>
</details>

<details>
  <summary>진수님 회고록</summary>
  <div markdown="1">

    # 젠킨스

### 젠킨스란?

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ede66275-19cf-4244-92a7-7cfdb7da87e2/%EC%A0%A0%ED%82%A8%EC%8A%A4.jpg](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%851.png)

- 젠킨스와 같은 CI툴이 등장하기 전에는 일정시간마다 빌드를 실행하는 방식이 일반적이었다

- Java 로 제작된 오픈 소스 CI(Continuous Integration) 툴, Apache Tomcat 같은 Servlet Container 위에 돌아가는 서버 기반 시스템

- 젠킨스는 정기적인 빌드에서 한발 나아가 서브버전, Git 과 같은 버전관리시스템과 연동하여 소스의 커밋을 감지하면 자동적으로 자동화 테스트가 포함된 빌드가 작동되도록 설정할 수 있다.

- 거의 모든 언어의 조합과 소스코드 리포지토리(Repository)에 대한 지속적인 통합과 지속적인 전달 환경을 구축하기 위한 간단한 방법을 제공

### 이점

- 프로젝트 표준 컴파일 환경에서의 컴파일 오류 검출

- 자동화 테스트 수행

- 정적 코드 분석에 의한 코딩 규약 준수여부 체크

- 프로파일링 툴을 이용한 소스 변경에 따른 성능 변화 감시

- 결합 테스트 환경에 대한 배포작업

### 파이프라인 생명주기

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/387740e8-889e-457b-8dcd-e9fd04c5a7f5/111222.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%88%E3%85%852.png)

1. Jenkins(with kubernetes plugin) master에게 Jenkins job 수행요청
2. Jenkins master는 kubernetes에게 slave를 생성하도록 선언
3. 새로 생성된 Jenkins slave pod은 job을 수행(빌드, 배포 등)
4. job이 완료되면 해당 Jenkins slave pod을 삭제

# nginx

### nginx란?

- 웹 서버 소프트웨어로, 가벼움과 높은 성능을 목표로 한다. 웹 서버, 리버스 프록시 및 메일 프록시 기능을 가진다

- 요청에 응답하기 위해 비동기 이벤트 기반 구조를 가진다. 이것은 아파치 HTTP 서버의 스레드/프로세스 기반 구조를 가지는 것과는 대조적이다. 이러한 구조는 서버에 많은 부하가 생길 경우의 성능을 예측하기 쉽게 해준다.

- Nginx는 기본적인 웹 서버 기능 외에 추가로 ‘캐싱(caching)’, ‘트래픽 부하 균형(load balancing)’, ‘TLS/SSL 보안’, ‘메일 프록시’ 등의 기능을 제공한다.

출처

[https://www.itworld.co.kr/news/107527](https://www.itworld.co.kr/news/107527)

[https://ict-nroo.tistory.com/31](https://ict-nroo.tistory.com/31)

[https://namu.wiki/w/Jenkins](https://namu.wiki/w/Jenkins)

[https://blog.voidmainvoid.net/140](https://blog.voidmainvoid.net/140)
    
  </div>
</details>

<details>
  <summary>소영님 회고록</summary>
  <div markdown="1">

    ## Batch

Spring Batch란?

- 엔터프라이즈 시스템의 운영에 있어 대용량 일괄처리의 편의를 위해 설계된 가볍고 포괄적인 배치 프레임워크
- DI, AOP, 서비스 추상화 등 스프링 프레임워크의 3대 요소를 모두 사용할 수 있다.

Spring Batch를 사용하는 경우

- 대용량의 비즈니스 데이터를 복잡한 작업으로 처리해야 하는 경우
- 특정한 시점에 스케쥴러를 통해 자동화된 작업이 필요한 경우
- 대용량 데이터의 포맷을 변경, 유효성 검사등의 작업을 처리후 기록해야 하는 경우

Spring Batch의 조건

- 대용량 데이터
- 자동화
- 견고성
- 신뢰성
- 성능

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/28243dd0-48cd-4fff-abab-2d43671a9c53/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%85%871.png)

- 하나의 job은 여러개의 step으로 이루어져 있다.
- 각각의 스텝은 성공할수도 실패할수도 있으며 한개의 스텝만 실행하는 것도 가능함
- 스텝을 처리하는 방법
    - Item Reader
    - Item processor
    - Item Writer

application.yml 세팅

```java
spring:
  batch:
    job:
      names: ${job.name:NONE}
    jdbc:
      initialize-schema: ALWAYS
  datasource:
    url: jdbc:mariadb://127.0.0.1:3306/app_2022_09_22?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul
    driver-class-name:
    username: root
    password:
  jpa:
    show-sql: true
```

Spring boot 앱을 배치앱으로 설정

```java
@SpringBootApplication
@EnableBatchProcessing
public class App20220922Application {

	public static void main(String[] args) {
		SpringApplication.run(App20220922Application.class, args);
	}
}
```

Job 만들기

```java
@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("helloWorldJob")
                .start(helloWorldStep1())
                .build();
    }

    @Bean
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("helloWorldStep1")
                .tasklet(helloWorldTasklet())
                .build();
    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("헬로월드!");

            return RepeatStatus.FINISHED;
        };
    }
}
```

DB 백업 및 복원

```java
//복원
mysqldump -u root app_2022_09_22 > app_2022_09_22.sql

mysqldump -u root DB명 > 백업파일명

//복원
mysql -u root app_2022_09_22_backup < app_2022_09_22.sql

mysql -u root DB명 < 백업파일명.sql
```

스프링 빈의 생명주기

- 스프링 컨테이너 생성
- 스프링 빈 생성
- 의존관계 주입
- 초기화 콜백
- 사용
- 소멸 전 콜백
- 스프링 종료

스코프의 종류

- 싱글톤 스코프
    - 애플리케이션 생명주기 동안 빈의 요청이 발생할 때마다 항상 동일한 빈을 제공해주어야 하므로 스프링 컨테이너의 시작과 종료, 생명주기를 함께하는 가장 넓은 범위의 스코프
- 프로토타입스코프
    - 요청할 때마다 새로운 빈이 필요한 경우에 사용함.
    - 스프링 컨테이너의 의존관계 주입, 초기화 까지만 관여하고 이후의 프로세스는 관리하지 않음
    - 모든 책임은 클라이언트가 지게된다.
- 세션
    - 브라우저 당 객체가 한개씩 생성됨.
- 리퀘스트
    - 동시에 많은 HTTP 요처이 들어오면 어떤 요청이 남긴 로그인지 구분하기 위해 사용됨.

## Mybatis

Mybatis란?

- 자바의 관계형 데이터베이스 프로그래밍을 쉽게 할 수 있도록 돕는 개발 프레임워크
- JDBC를 통해 데이터베이스에 엑세스하는 작업을 캡슐화 하고 일반 SQL 쿼리, 저장 프로시저 및 고급 매핑을 지원함.
- SQL 쿼리들을 한 구성파일에 구성해 프로그램 코드와 SQL 코드를 분리할 수 있음

Mybatis 특징

- 복잡한 쿼리에 강하다.
- 좀 더 세심하게 쿼리문을 다룰 수 있다.
- 프로그램 코드와 SQL쿼리의 분리로 코드가 간결해지고 유지보수성이 향상됨.
- 빠른 개발이 가능하다.
- 그러나 비슷한 쿼리는 남발하게 되는 경향이 있다.

Mybatis - 게시물 리스트 조회

```java
@Mapper
public interface ArticleRepository {

    @Select("""
            <script>
            SELECT *
            FROM article
            </script>
            """)
    List<Article> getArticles();
}
```

- 쿼리문을 파일에서 직접 다룰 수 있다.
- article 테이블의 칼럼을 모두 받아와 getArticles에 넣는다.

Mybatis - 게시물 작성

```java
@Insert("""
            <script>
            INSERT INTO article
            SET createDate = NOW(),
            modifyDate = NOW(),
            subject = #{subject},
            content = #{content}
            </script>
            """)
    void write(@Param("subject") String subject, @Param("content") String content);
```
    
  </div>
</details>

<details>
  <summary>성구님 회고록</summary>
  <div markdown="1">

    19일 강의가 끝나고… 오류해결 정리

### ****Please set the JAVA_HOME variable in your environment to match thelocation of your Java installation.****

- **상황**

spring boot을 intelij로 개발중, .\gradlew.bat clean 명령어를 사용했을때 발생

**JAVA_HOME과 프로젝트에서 사용하는 Java version이 달라서 발생**

끝나고 강사님과 함께 이것저것 보면서 작업을 해보았다.

기본적으로 jdk11로 설정해놓은 것을 PATH를 바꿔도 변경이 안되어서 이것저것 삭제 및 설정을 해봄..

아래의 방법들은 다 구글링으로 나와있는 방법들이다

(원래는 다 되는 방법들임)

**1) jdk 11 삭제** 

jdk 11이 깔려있는 경로에서 삭제 해봄 (default 값 변경됨)

jdk 1.8로 되어서 `**실패**`

**2) Environment variables 설정 - `실패`**

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dd73bc8a-fcc8-4d3d-b61e-28de24f74ba9/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B11.png)

**3) 시스템 변수에서 JAVA_HOME 설정 - `실패`**

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cf940a6b-199a-4668-844a-9715fe81ad1a/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B12.png)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7cd22880-c4a5-4bd3-807d-276de9c7763b/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B13.png)

4) Project Structure 에서 다른 jdk를 모두 삭제 - `**실패**`

이 방법은 project default값을 변경해줌.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d15d3256-0ec7-4e9c-99bf-c5f07785d34f/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B14.png)

5) 사용자 변수에서 시스템변수와 동일하게 수행 

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4c9b8fe9-c1c7-418f-ad52-eb3409e540e8/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B15.png)

**`성공..`**

보통은 시스템 환경변수 path만 변경하면 변경이 된다고 하셨다.

(초기 jdk11 설정도 그렇게 해놨었음.)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ee2ff393-0305-47db-ae3d-d24223a8dee5/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B16.png)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ef3844b8-e78c-4809-ba8d-a511de0e6e25/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%8A%E3%84%B17.png)

---

### 도커 따라잡기

**41강~**

서버에서 빌드 성공을 위해서 

DB를 설치했고 

이제 그 DB의 올바른 접속 정보를 넣기위해 작업!

### 다 지우고 처음부터 다시 시도하기. *docker, DB*

STEP1) ****MariaDB 삭제****

```bash
sudo yum remove mariadb

sudo rm -rf /var/lib/mysql
# 잔존 데이터 삭제

sudo rm -rf /etc/my.cnf.d*
#잔존 설정파일 삭제
```

STEP2) ****기존 도커이미지, 컨테이너 전부 삭제****

```bash
# 컨테이너 삭제
docker rm -f $(docker ps -qa)

# 이미지 삭제
docker rmi -f $(docker images -qa)

# 안쓰는 네트워크 삭제
docker network prune -f

# 안쓰는 볼륨 삭제
docker volume prune -f
```

STEP3) ****MariaDB 설치****

```bash
sudo yum install mariadb-server -y

sudo systemctl enable mariadb

sudo systemctl start mariadb
```

STEP4) ****DB가 생성될 때 기본적으로 charset이 utf8mb4가 되도록 설정변경****

- `sudo vim /etc/my.cnf.d/mariadb-server.cnf`

```bash
[mysqld]
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
log-error=/var/log/mariadb/mariadb.log
pid-file=/run/mariadb/mariadb.pid
character-set-server=utf8mb4 # 추가
collation-server=utf8mb4_unicode_ci # 추가
skip-character-set-client-handshake # 추가
```

- `• sudo systemctl restart mariadb`

STEP5) ****보안설정****

```bash
sudo /usr/bin/mariadb-secure-installation
#Switch to unix_socket authentication [Y/n] n
#Change the root password? [Y/n] y
#새 비번 설정, 2번 입력, lldj123414 로 입력
#나머지 Y
```

STEP6) ****마스터계정(lldj 생성) 생성****

```bash
mysql -u root -plldj123414
#내 pc에서는 root 비밀번호 설정안했음
GRANT ALL PRIVILEGES ON *.* TO lldj@'%' IDENTIFIED BY 'lldj123414';
GRANT ALL PRIVILEGES ON *.* TO lldjlocal@127.0.0.1 IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO lldjlocal@'172.17.%.%' IDENTIFIED BY '1234';
exit
```

STEP7) ****테스트용 DB, 운영 DB 생성****

- `mysql -u root -plldj123414`

```sql
CREATE DATABASE app20220916_2__test;
CREATE DATABASE app20220916_2;

USE app20220916_2;

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert  into `article`(`id`,`subject`) values 
(1,'제목1'),
(2,'제목2');
```

`• exit`

STEP8) ****기존 프로젝트 폴더 제거 후, 다시 클론****

```bash
mkdir -p /docker_projects/sbdb_1/project

cd /docker_projects/sbdb_1/project

rm -rf *

rm -rf .g*

git clone https://github.com/jhs512/app20220916_3 .
```

STEP9) ****gradlew 를 소유자가 실행 가능한 상태로 변경****

```bash
#권한 변경
chmod 744 gradlew
```

STEP10) 빌드

`./gradlew clean build`

STEP11) ****Dockerfile 생성 - in project****

- ****vim Dockerfile****

```bash
# m1 이라면 openjdk:17-jdk-alpine 대신 openjdk:17 사용
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/app20220916-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
```

STEP12) ****sbdb 이미지 생성****

`docker build -t sbdb .`

STEP13) ****sbdb 이미지 실행 - OK****

```bash
docker run \
  --name=sbdb_1 \
  -p 8080:8080 \
  -d \
  sbdb
```

STEP14) 크롬에서 확인하기

****`http://192.168.56.109:8080/article/getList`****

---

- `**도커라이즈**`

[1단계, mariadb 도커라이즈 계획](https://youtu.be/SrJG8MCxhdg)

[2단계, 기존 mariadb 삭제 후, 도커라이즈](https://youtu.be/6pDi7AtBDWs)

- ****MariaDB 삭제****

```bash
sudo yum remove mariadb

sudo rm -rf /var/lib/mysql
잔존 데이터 삭제

sudo rm -rf /etc/my.cnf.d*
잔존 설정파일 삭제
```

- ****MariaDB 최신 이미지 실행****

```bash
# 컨테이너 실행
docker run \
  --name mariadb_1 \
  -d \
  --restart unless-stopped \
  -e MARIADB_ROOT_PASSWORD=lldj123414 \
  -e TZ=Asia/Seoul \
  -p 3306:3306 \
  -v /docker_projects/mariadb_1/conf.d:/etc/mysql/conf.d \
  -v /docker_projects/mariadb_1/mysql:/var/lib/mysql \
  -v /docker_projects/mariadb_1/run/mysqld:/run/mysqld/ \
  mariadb:latest
```

[3단계, 설치된 mariadb 보안설정, 계정생성, DB 생성](https://youtu.be/4CsuFjsaGp8)

- ****보안설정****

```bash
docker exec -it mariadb_1 /usr/bin/mariadb-secure-installation
#Switch to unix_socket authentication [Y/n] n
#Change the root password? [Y/n] n

# 나머지 Y
```

- ****마스터계정(lldj 생성) 및 DB 생성****

`docker exec -it mariadb_1 mysql -u root -p`

`GRANT ALL PRIVILEGES ON *.* TO lldj@'%' IDENTIFIED BY 'lldj123414';`

`GRANT ALL PRIVILEGES ON *.* TO lldjlocal@127.0.0.1 IDENTIFIED BY '1234';`

`GRANT ALL PRIVILEGES ON *.* TO lldjlocal@'172.17.%.%' IDENTIFIED BY '1234';`

yog~ 

```sql
CREATE DATABASE app20220916_2__test;
CREATE DATABASE app20220916_2;

USE app20220916_2;

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert  into `article`(`id`,`subject`) values 
(1,'제목1'),
(2,'제목2');
```

`exit`

- 크롬에서 확인

****`http://192.168.56.109:8080/article/getList`****

[위캔 | Ken 10274](https://wiken.io/ken/10274)

**45강**

---

### nginx proxy manager

[1단계, nginx proxy manager 개요](https://youtu.be/uOUE25KM1Ss)

[Nginx Proxy Manager](https://nginxproxymanager.com/)

[2단계, hosts 파일 수정](https://youtu.be/qzqQOWh3hJo)

• 경로 : C:\Windows\System32\drivers\etc\hosts

```sql
192.168.56.109	site1.com
192.168.56.109	site2.com
192.168.56.109	site3.com
192.168.56.109	site4.com
192.168.56.109	site5.com
192.168.56.109	site6.com
192.168.56.109	site7.com
192.168.56.109	site8.com

192.168.56.109	java.site1.com
192.168.56.109	java.site2.com
192.168.56.109	java.site3.com
192.168.56.109	java.site4.com
192.168.56.109	java.site5.com
192.168.56.109	java.site6.com
192.168.56.109	java.site7.com
192.168.56.109	java.site8.com
```

[3단계, nginx proxy manager 설치](https://youtu.be/0ouVujyK5hc)

- ****디렉토리 생성****

`mkdir -p /docker_projects/nginx_2`

`cd /docker_projects/nginx_2`

- ****docker-compose.yml 생성****

`vim docker-compose.yml`

```sql
version: "3"
services:
  app:
    image: 'jc21/nginx-proxy-manager:latest'
    restart: unless-stopped
    ports:
      - '80:80' # Public HTTP Port
      - '443:443' # Public HTTPS Port
      - '81:81' # Admin Web Port
    environment:
      TZ: "Asia/Seoul"
      DB_MYSQL_HOST: "172.17.0.1"
      DB_MYSQL_PORT: 3306
      DB_MYSQL_USER: "lldjlocal"
      DB_MYSQL_PASSWORD: "1234"
      DB_MYSQL_NAME: "nginx"
    volumes:
      - ./data:/data
      - ./letsencrypt:/etc/letsencrypt
```

- ****DB 생성****

```bash
docker exec -it mariadb_1 mysql -u root -plldj123414

//sql
CREATE DATABASE nginx;

exit
```

- ****실행****

`docker compose up -d`

- ****관리 콘솔 접속****

`http://192.168.56.103:81`

크롬에서 확인

임시 관리자 계정

- admin@example.com
- changeme

- ****관리 콘솔 접속 후 관리자 계정정보 변경****

*이메일 변경*

- 본인이 사용하는 이메일로 변경

*비밀번호 변경*

- lldj123414

[4단계, http://java.site2.com/ 호스트 추가, http://172.17.0.1:8080 으로 포워드](https://youtu.be/LijFnpuDhZo)

- ****관리 콘솔 접속****

`http://192.168.56.109:81`

- 로그인

- ****호스트 추가****

```
Hosts 버튼 클릭(상단메뉴)

Add Proxy Host 버튼 클릭

Domain Names : java.site2.com

Forward Hostname / IP : 172.17.0.1

Forward Port : 8080

Save
```

- **접속**

`http://java.site2.com`

크롬에서 확인

[5단계, 소스코드 업데이트로인한 재배포 방법](https://youtu.be/rB0UwyRYlow)

- ****프로젝트 폴더로 이동****

`d /docker_projects/sbdb_1/project`

- **소스코드 최신화**

`git pull origin master`

- **빌드**

`./gradlew clean build`

- **기존 컨테이너 끄고 삭제**

`docker kill sbdb_1`

`docker rm sbdb_1`

- **기존 이미지 제거**

`docker rmi sbdb`

- **sbdb 이미지를 다시 생성**

`docker build -t sbdb .`

- **sbdb 이미지를 다시 실행**

```
docker run \
  --name=sbdb_1 \
  -p 8080:8080 \
  -d \
  sbdb

```

- **http://java.site2.com**

→ 크롬에서 확인

- 55강 readme.md에 추가할 내용

```bash
# 프로젝트 폴더 경로
/docker_projects/sbdb_1/project

# 프로젝트 새로 구성
rm -rf /docker_projects/sbdb_1/project
mkdir -p /docker_projects/sbdb_1/project
cd /docker_projects/sbdb_1/project
git clone https://github.com/jhs512/app20220916_3 .
chmod 744 ./gradlew

# 그래들 빌드
./gradlew clean build

# 현재 실행중인 컨테이너 중지 및 삭제
docker stop sbdb_1
	안되면 : docker kill sbdb_1
docker rm -f sbdb_1

# 새 sbdb 이미지 만들기
git pull origin master

# 그래드 빌드
docker build -t sbdb .

# sbdb 이미지 실행
docker run \
  --name=sbdb_1 \
  -p 8080:8080 \
  -v /docker_projects/sbdb_1/volumes/gen:/gen \
  --restart unless-stopped \
  -e TZ=Asia/Seoul \
  -d \
  sbdb
```

[8단계, 그래들 빌드, Dockerfile 생성, 도커이미지 생성을 대신해주는 한방명령어 ./gradlew bootBuildImage](https://youtu.be/SIGARoIfc1A)

- **gradlew를 이용해서 소스코드 빌드, 이미지 빌드 한번에 처리**

`./gradlew bootBuildImage`

- 이 때 수행되는 소스코드빌드에서는 테스트가 스킵된다.

- **만들어진 이미지는 이름이 `앱이름:앱버전` 의 형태**

`app20220916:0.0.1-SNAPSHOT`

- 앱버전은 build.gralde 에 적혀 있다.

**⭐ 이미지 실행 전에 지금 실행 중인 것을 멈춰주기**

`docker stop sbdb_1`

`docker rm sbdb_1`

- **만들어진 이미지 실행**

```
docker run \
  --name=sbdb_1 \
  -p 8080:8080 \
  -v /docker_projects/sbdb_1/volumes/gen:/gen \
  --restart unless-stopped \
  -e TZ=Asia/Seoul \
  -e "SPRING_PROFILES_ACTIVE=prod" \
  -d \
  app20220916:0.0.1-SNAPSHOT

```

- SPRING_PROFILES_ACTIVE 환경변수를 꼭 추가해줘야 운영모드로 실행

[위캔 | Ken 10274](https://wiken.io/ken/10274)

**46강 ~** 

---

### 젠킨스

[위캔 | Ken 10451](https://wiken.io/ken/10451)

- **개요**
    
    [NAVER CLOUD PLATFORM](https://www.ncloud.com/product/devTools/jenkins)
    

- **환경 세팅✅**

> **체크사항  1 : 호스트전용어댑터**
> 
- 강사의 IP : 192.168.56.109
- 나의 IP : 192.168.56.103

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9fb5e592-61e4-41c5-90db-16c6a8d5bd4f/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B11.png)

> **체크사항 2 : lldj 계정에 sudoer 권한이 있는지**
> 

```bash
lldj에 sudo 권한 주기

무조건 root 계정으로 진행 해야합니다.
su
roo 비번 입력
이렇게 하면 exit 를 하기 전까지 root 계정으로서 활동할 수 있습니다.

vim /etc/sudoers
root ALL=(ALL) ALL # 이 라인 밑 부분에
lldj ALL=(ALL) ALL # 이 라인을 추가

저장시 꼭 wq! 로 저장
```

> **체크사항 3 : SELINUX 꺼져있는지**
> 
- `sudo vi /etc/selinux/config`
    - SELINUX=disabled
        - 설정이 안되어 있다면, 설정 후 reboot

> **체크사항 4 : firewalld 꺼지있고, 비활성화 되어 있는지**
> 

`sudo systemctl stop firewalld`

`sudo systemctl disable firewalld`

> **체크사항 5 : yum epel-release 설치, yum update**
> 

`sudo yum install epel-release`

`sudo yum update -y`

> **체크사항 6 : 도커설치 확인 및 도커관련 초기화**
> 

```
# 컨테이너 삭제
docker rm -f $(docker ps -qa)

# 이미지 삭제
docker rmi -f $(docker images -qa)

# 안쓰는 네트워크 삭제
docker network prune -f

# 안쓰는 볼륨 삭제
docker volume prune -f

# 도커 프로젝트 삭제
sudo rm -rf /docker/projects
sudo rm -rf /docker_projects
```

---

[4강, basic_work_1 라는 이름의 프리스타일 프로젝트 생성, touch ABC 명령을 수행, 빌드 NOW 수행하여 확인](https://youtu.be/PQeqQo-bGZQ)

- **아이템 생성**

New Item 버튼 클릭

- **`basic_work_1` 이라는 이름의 프리스타일 프로젝트 생성**

이름 : basic_work_1

- **Build Steps**

Execute shell

명령어 : touch ABC

- **Build Now**

Build Now

- **확인 - file 생성**

`ls -alh` /docker_projects/jenkins_1/var/jenkins_home/workspace/basic_work_1

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/885ce982-4c3d-4436-8e1c-5c32ce76fc7e/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B12.png)

---

[5강, 공개된 깃허브 리포지터리에서 프로젝트 가져오는 프로젝트 basic_work_2](https://youtu.be/Nd7hQ7S1u98)

- **아이템 생성**

New Item 버튼 클릭

- **`basic_work_2` 이라는 이름의 프리스타일 프로젝트 생성**

이름 : basic_work_2

- **소스 코드 관리**

GIT

리포지터리 주소 : https://github.com/jhs512/app20220916_3

- **Build Now**

Build Now

- **확인 - git pull이 당겨져 와있다!**

`ls -alh` /docker_projects/jenkins_1/var/jenkins_home/workspace/basic_work_2

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/95519047-8638-4abd-95cf-bfd51a4b0a6f/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B13.png)

---

[6강, personal access tokens 활용하여, private 리포지터리 소스코드 받아오기](https://youtu.be/D7--GG-lWVw)

⭐private project에 그냥 주소만 가져와서 빌드를 하면 실패한다

**private 프로젝트에서 토큰**을 가져와서 빌드해보자!

- **personal access tokens**

https://github.com/settings/tokens

- **소스 코드 관리**

깃허브 주소 : https://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@github.com/jhs512/private_sample

https://토큰@github.com/jhs512/private_sample

- **Build Now**

Build Now

- **확인**

ls -alh /docker_projects/jenkins_1/var/jenkins_home/workspace/basic_work_3

**토큰 추가 후 빌드**

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c98572c5-638b-4a3b-bc56-a04945697686/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B14.png)

---

### **ngrok**

[ngrok - Online in One Line](https://ngrok.com/)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a6b1b529-26d2-473b-acfb-80e6e64d193f/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B15.png)

git hub의 신호가 jenkins까지 접근해야한다.

pc에 공인 IP가 존재하지 않아서 프로젝트에서 접근할 수 없다.

그래서 **ngrok 사용한다.** 

**→(mac, windows) 둘다 사용 가능** 

**→ 개인 PC에 도메인을 부여할 수 있다**

[ngrok 외부에서 로컬 개발환경 localhost 접근하기(mac)](https://wildeveloperetrain.tistory.com/140)

---

[8강, ngrok 실행, VirtualBox 포트포워딩으로 젠킨스 서비스에 PUBLIC 도메인 부여](https://youtu.be/ZWKxaxMo5p4)

- homepage 가입 : [https://ngrok.com/](https://ngrok.com/)

아래 창처럼 work 폴더에 넣어줬음.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/93220324-6b02-4da0-a0e7-51eb48780eb0/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B16.png)

- 실행 : `**ngrok http 8081`**

포트 8081? 상관X (포트포워딩할 것임)

- **아래 창은 종료하면 안된다.**

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bd9c9c29-e5cc-440a-a7c4-1ff45b35ed45/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B17.png)

나의 IP : [https://fb57-112-165-249-208.jp.ngrok.io](https://fb57-112-165-249-208.jp.ngrok.io/)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/eedf627e-06e1-4534-9ab7-eb0fc8506d1b/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B18.png)

- **HTTP 8081 포트포워딩 - centOS - 설정 - 네트워크 - 고급**

![[https://s3-us-west-2.amazonaws.com/secure.notion-static.com/5c3f06d1-a4a2-4502-a26a-b505c947e129/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B19.png)

이름 : HTTP 8081

프로토콜 : TCP

호스트 IP : 0.0.0.0

호스트 포트 : 8081

게스트 IP : 10.0.2.15

게스트 포트 : 8081

⭐ 도메인으로 접속하기 전에 토큰 값을 추가해주어야한다. 

1) [https://dashboard.ngrok.com/get-started/your-authtoken](https://dashboard.ngrok.com/get-started/your-authtoken)

토큰을 copy해온다.

2) 명령 프롬프트를 켜주고 

`**ngrok config add-authtoken {토큰값}**`

`**ngrok authtoken {토큰값}**`

두 명령어 모두 사용 가능하니 편한 것으로 사용하면 된다.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a22b748c-d8c8-40fe-86d8-a2d842a12248/Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B19.png)

- **public 도메인으로 접속**

![https://fb57-112-165-249-208.jp.ngrok.io/](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B110.png)

각자 본인이 ngrok 에서 부여받은 도메인으로 접속

![https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B18.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B111.png)

TIP) **ngrok 실행 시 발행받은 도메인은 매 실행마다 바뀌는 것 같다.**

200, 403같이 리퀘스트가 어떻게 되었는지 확인 가능하다.

![https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B19.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%84%B112.png)
    
  </div>
</details>

## 회고 과정에서 나왔던 질문 (최소 200자 이상)

---

**서로 피드백한 댓글을 첨부합니다.**

### 지훈님 회고록 피드백
![image/week7/ㄱㅈㅎ.PNG](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%84%B1%E3%85%88%E3%85%8E.PNG)
### 상훈님 회고록 피드백
![image/week6/6.PNG](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%84%B1%E3%85%85%E3%85%8E.PNG)
### 진수님 회고록 피드백
![image/week6/4.PNG](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%85%E3%85%88%E3%85%85.PNG)
### 소영님 회고록 피드백
![image/week6/3.PNG](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%85%87%E3%85%85%E3%85%87.PNG)
### 성구님 회고록 피드백
![image/week6/2.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/%E3%84%B7%E3%85%85%E3%84%B1.PNG)
## 회고 인증샷 & 팀 자랑

---

### 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
 
![image/week6/1.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week7/qq.png)

### 필수) 자랑 멘트는 ‘팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지’에 대해 간단하게 작성해 주시면 됩니다 😊


- 현재 한 주당 1회 회고를 필수로 작성하고 있는데 이 회고를 다른 팀원들이 읽고 댓글을 자유롭게 남기는 형태로 진행하고 있습니다! 📝
- 팀원들의 회고는 하루동안 혹은 중요한 부분을 기술하고 있으며, 팀원들의 댓글은 피드백과 배운 점을 남김으로써 
서로서로 **공부하고 있는 내용이나 강의에 대한 정확한 이해를 돕는 점이** 
가장 큰 효과로 작용하고 있습니다. 😁
