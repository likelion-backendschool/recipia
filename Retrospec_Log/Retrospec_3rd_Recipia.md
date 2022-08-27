## **팀 구성원**

- [팀장] 김지훈, 도성구, 김상훈, 이소영 서진수

---

## **회고 내용 요약 (최소 500자 이상)**

팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂
## 이소영

## GIT

- 커밋 갖고 놀기#2
    - 문제 : C2의 커밋 내용을 정리하되 rebase -i를 써선 안됨
    - 현재 상태
        
        ![image1](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/1.png)
        
    - 목표
        
        ![image2](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/2.png)
        
    
    ```java
    git checkout main // main브랜치로 이동함
    git cherry-pick c2 //cherry-pick로 c2이동
    git cherry-pick c3 //cherry-pick로 c3이동
    ```
    
- 깃 tag
    - 브랜치에 태그 만들기
    - 문제 c1, c2브랜치에 각각 v0, v1태그 달기
    
    ```java
    git checkout c2 //c2로 브랜치 이동
    git tag v0 c1 // c1브랜치를 지정해 v0 태그 지정
    git tag // 따로 브랜치를 지정하지 않으면 현재 head 브랜치에 태그 생성
    				//현재의 경우에는 c2
    ```
    

- 깃 describe
    - 깃 describe 사용하기
    
    ```java
    git describe main
    -> v0_2_gC2 //V0태그로부터 2칸 떨어진 C2브랜치이다.
    git describe side
    -> v1_1_gC4 //V1태그로부터 1칸 떨어진 C4브랜치이다.
    git describe bugFix
    ->v1_2_gC6 //V1태그로부터 2칸 떨어진 C6브랜치이다.
    ```
    
- 9천번이 넘는 리베이스
    - 여러브랜치를 리베이스 하여 한줄로 만들기
    - git merge 나를 중심으로 다른 브랜치 흡수
    - git cherry-pick 나를 중심으로 다른 브랜치 가져와서 붙임
    - git rebase 내가 다른 곳으로 감

```java
1)
git checkout bugFix
git rebase main

2)
git rebase main bugFix

1과 2는 같은 것

git rebase main bugFix
git rebase bugFix side
git rebase side another
git rebase another main
```

- 다수의 부모
    - 부모가 다수일 때 브랜치 이동
    - 주의할점
        - HEAD ~2 = 두칸 올라감
        - HEAD^^ = 두칸 올라감
        - HEAD^ = 위로 한칸
        - HEAD^2 = 위로 한칸 올라가되, 주경로가 아닌 다른 경로로 올라감

```java
git branch bugWork HEAD~^2~
```

## Spring Boot

주요 구현 기능

- 수정하기 기능
    
    ```java
    @PreAuthorize("isAuthenticated()")
        @PostMapping("/modify/{id}")
        public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                @PathVariable("id") Integer id, Principal principal) {
            if (bindingResult.hasErrors()) {
                return "answer_form";
            }
            Answer answer = this.answerService.getAnswer(id);
            if (!answer.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            this.answerService.modify(answer, answerForm.getContent());
            return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        }
    ```
    
    - @preAuthorize 로 인증이 되었는지 확인한 후 수정을 한다.
    - 만약 작성자와 유저이름, 그리고 수정하려는 사람의 이름이 같지 않으면 수정 권한이 없다고 출력한다.
    - 그렇지 않다면 수정한 내용을 새로 post하고 리턴한다.
    
- 답변 삭제 기능
    
    ```java
    @PreAuthorize("isAuthenticated()")
        @GetMapping("/delete/{id}")
        public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
            Answer answer = this.answerService.getAnswer(id);
            if (!answer.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            }
            this.answerService.delete(answer);
            return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        }
    ```
    
    - 위의 수정기능과 같이 인증이 되었는지 확인 한다.
    - 작성자, 이름, 수정하려는 사람의 이름이 같지 않으면 삭제 권한을 갖지 못한다.
    - 그렇지 않다면 답변을 삭제할 수 있다.
    
- 게시글, 답변 추천 기능
    
    ```java
    @PreAuthorize("isAuthenticated()")
        @GetMapping("/vote/{id}")
        public String answerVote(Principal principal, @PathVariable("id") Integer id) {
            Answer answer = this.answerService.getAnswer(id);
            SiteUser siteUser = this.userService.getUser(principal.getName());
            this.answerService.vote(answer, siteUser);
            return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
        }
    ```
    
    - 추천을 클릭한 사람의 이름을 받아 답변의 추천수를 늘인다.
    
- 답변 후  해당 답변에 스크롤 멈추기
    
    ```java
    Answer answer = this.answerService.create(question, 
                    answerForm.getContent(), siteUser);
            return String.format("redirect:/question/detail/%s#answer_%s", 
                    answer.getQuestion().getId(), answer.getId());
    ```
    
    - redirect:/question/detail 은 질문글 상세페이지의 url이다. 여기에 /%s#answer_%s을 더하면 해당 페이지의 부분 id를 의미한다.
    - return 시 해당 위치를 반환해 해당 부분으로 바로 갈 수 있도록 한다.
    
## 김지훈 
# 인프라

## Nginx

---

[Installing NGINX Open Source](https://docs.nginx.com/nginx/admin-guide/installing-nginx/installing-nginx-open-source/)

# SpringBoot

## JPA

---

### JPA의 save 관련

JPA Repository의 save 메서드는 insert와 update를 동시에 수행할 수 있다. 이는 PathValue 값이 null인지 아닌지로 판단한다.

### save의 종류

JPA에선 save로 두가지 일을 처리할 수 있다. insert같은 데이터를 값을 저장해주는 쿼리 뿐만 아니라 update 쿼리문도 수행해주는 JPA 메서드이다. 각 상황에 따라 save를 사용해보자

## URL Mapping 어노테이션

---

### DeleteMapping vs GetMapping

실습을 진행하면서 Delete를 사용할 때 왜 DeleteMapping 말고 GetMapping 만을 사용하는지 의문점이 들었다. 실습을 진행하면서 알아볼까한다.

먼저 sbb 실습 코드에서 아래와 같이 Delete메소드 위에 GetMapping 말고 DeleteMapping 어노테이션으로 바꿔 실행을 해보고 질문을 삭제해본다.

![image3](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/3.png)
질문을 삭제하려고 보니 위와 같이 405 에러가 발생한다! 에러 메시지를 읽어보니 GET 메소드가 없다고 뜬다. 왜그럴까? 구글링을 해보니 StackOverflow에서도 같은 질문이 있었다.

![image4](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/4.png)
### 해결책

아래의 답변과 같이 DeleteMapping을 사용하려면 HTTP Delete 오퍼레이션을 만들어서 보내줘야한다고 한다. 왜냐하면 브라우저에서 url로 서버에 접근하게되면 브라우저는 항상 GET으로 요청을 하기 때문이다. 이를 PostMan같은 툴을 사용하거나 자바스크립트로 delete 요청을 만들어 보내면 된다고 한다.

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/5.png)
### 해결 실습

1. [application.properties](http://application.properties) 설정
    
    ```java
    **# For DeleteMapping & PutMapping
    spring.mvc.hidden-method.filter.enabled=true**
    ```
    
    - 여러 글에서 hiddenmethod로 했었는데 스프링부트 환경에선 dash를 사이에 붙여줘야했다…..
2. http form에서 메서드 타입 수정 (question_detail.html 파일)
    
    ```java
    **<form th:action="@{|/question/delete/${question.id}|}" th:method="delete">
           <button type="submit">삭제</button>
    </form>**
    ```
    
3. DeleteMapping Annotation 붙여주기
    
    ```java
    **@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable Long id, Principal principal){
         Question question = this.questionService.getQuestion(id);
         if (!question.getAuthor().getUsername().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
         }
         this.questionService.delete(question);
         return "redirect:/";
    }**
    ```
    
4. 잘 실행되는지 확인
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/6.png)
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/7.png)
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/8.png)
    잘 삭제된다👍
    

# 추가 간단 정리

## 용어 정리

---

### Config란 무엇인가

프로젝트를 생성하면서 config란 파일도 많이 접해봤고 스프링시큐리티에서도 클래스명을 config라고도 한다. 이는 설정이라는 뜻이며 각종 사용전 설정을 담아두는 파일이나 클래스에 붙는다👍

[file) *.config 파일은 무엇인가?](https://hi098123.tistory.com/332)

**참고자료**

[Request method 'GET' not supported] error for method using @DeleteMapping](https://stackoverflow.com/questions/63002191/request-method-get-not-supported-error-for-method-using-deletemapping)
## 도성구
### git branch

[위캔 | Ken 9398](https://wiken.io/ken/9398)

1. 원격 문제  : 1, 2 ,3 풀어도 되고, 원격 문제 3번만 푸셔도 됩니다.

- **git fetch?**

git checkout feature/1

 `**git pull origin master**` : 

git fetch(origin/master최신화) + git merge origin/master

`**git pull origin master --rebase**` :

git fetch(origin/master 최신화) + git rebase origin/master master

`**pull**` 을 실행하면, 원격 저장소의 내용을 가져와 자동으로 병합 작업을 실행하게 된다. 

그러나 단순히 **원격 저장소의 내용을 확인만 하고 로컬 데이터와 병합은 하고 싶지 않은 경우에는 fetch 명령어를 사용**할 수 있다.

`**fetch**` 를 실행하면, 원격 저장소의 최신 이력을 확인할 수 있다. 

이 때 가져온 최신 커밋 이력은 이름 없는 브랜치로 로컬에 가져오게 된다. 
이 브랜치는 `'FETCH_HEAD'`의 이름으로 **체크아웃** 할 수도 있다.

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/9.png)

---

**실험**

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/10.png)

git fetch - 원격지 commit 된 내용들을 로컬에 반영 (로컬 브랜치 이동 X)

**`git switch`**로 브런치를 이동하면서 
**`merge`**를 통해 최신 내용들을 전부 병합해보는 실험을 해보았다.

<aside>
✅ **항상 내가 있는 상태에서 master와 merge하고 싶으면** 

**git pull origin master로 중간 중간 최신화를 하고**

작업내용을 push하는 것이 중요하다는 것을 명심하자

</aside>

- **강사님이 들어주신 예시**
    
    ```java
    # GITHUB FLOW
    깃허브 이슈번호 생성 7
    [master] git checkout -b bugFix/7 # master 5
    [bugFix/7] 작업
    [bugFix/7] git add .
    [bugFix/7] git commit -m "작업"
    **[bugFix/7] git pull origin master**
    [bugFix/7] git push origin bugFix/7
    GITHUB : PR : origin/bugFix/7 => origin/master
    GITHUB : MERGE : origin/bugFix/7 => origin/master
    GITHUB : origin/bugFix/7 브랜치 삭제
    [bugFix/7] git checkout master
    [master] git branch -D bugFix/7
    **[master] git pull origin master**
    [master] git prune
    깃허브 이슈번호 생성 9 => 아무개
    깃허브 이슈번호 생성 10 => 내가 하게
    [master] git checkout -b feature/10
    [feature/10] 작업
    [feature/10] git add .
    [feature/10] git commit -m "작업"
    [feature/10] git pull origin master --rebase
    [feature/10] 작업
    [feature/10] git add .
    [feature/10] git commit -m "작업"
    [feature/10] git pull origin master --rebase
    [feature/10] 작업
    [feature/10] git add .
    [feature/10] git commit -m "작업"
    [feature/10] git pull origin master --rebase
    [feature/10] git push origin feature/10
    ```
    

Tip. 필요 없는 branch 자르기 : `**git prune**` 

---

### Cent OS

 # 문제 1

 - HOST OS에서 그림판을 연다.

 - 그림판을 연다

 - 하트를 그린다.

 - heart.jpg로 추출한다.

 - 해당 파일을 CentOS9으로 복사한다.

 - HOST OS 크롬에서 [http://site3.com/heart.jpg](http://site3.com/heart.jpg) 

 (or [http://192.168.56.102:8023](http://192.168.56.102:8023)/heart.jpg : 호스트 OS의 hosts 파일 수정 안되는사람.)

하트를 표시한다.

hint 1 : wget 사용 (웹 이미지가 있는 주소를 입력하면 가져와짐)

hint 2 : SFTP 사용 (== ssh) 파일질라 클라이언트

> **WinSCP를 이용해서 windows → CentOS로 파일 옮기기**
> 

windows ~

[https://winscp.net/eng/download.php](https://winscp.net/eng/download.php) -설치 

[lldj@192.168.56.102](mailto:lldj@192.168.56.102) - host에 입력하면 알아서 됨 (비밀번호는 작성)

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/11.png)

Tip. 디렉토리를 하나 팠는데 퍼미션 오류가 발생한다?

**`sudo chmod 777 [디렉토리 or 파일명]`** << 으로 권한을 다 주고 옮기면 퍼미션 오류 안남.

Tip2. SSH Service 확인하기.

**`service sshd status` << SSH 서비스 상태 확인**

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/12.png)

**`service sshd start` << SSH 서비스 시작**

만약. SSH가 설치되어 있지 않다면, 다음 명령어를 통해 설치한다.

`sudo **yum -y install openssh-server openssh-clients**`

방벙 2) 아래 링크에서 **드래그 앤 드롭으로 url 생성해서 사용할 수 있음.**

wget으로 가져오기. 

[OnPaste - Online Screenshot and Drawing Tool](https://onpaste.com/)

**확인하기.**

sudo nano ****/etc/nginx/conf.d/vhost.conf****

sudo systemctl start nginx

http://192.168.56.102:8023/heart.jpg

http://site3.com/heart.jpg

+추가 원래 도메인으로 오류가 나서 불가능했는데 참고 자료를 통해서 가능함

---

### SBB

[위캔 | Ken 9392](https://wiken.io/ken/9392)

### **쿼리 구현하기 step 1. 검색을 해보자**

```java
# 1. 제목 검색
SELECT Q.*
FROM question AS Q
WHERE Q.subject LIKE '%sbb%';

# 2. 제목 + 내용 검색
SELECT Q.*
FROM question AS Q
WHERE (
    Q.subject LIKE '%sbb%'
    OR
    Q.content LIKE '%sbb%'
);

# 3. 제목 + 내용 + 작성자 검색
SELECT Q.*
FROM question AS Q
LEFT JOIN site_user AS SU
ON Q.author_id = SU.id
WHERE (
    Q.subject LIKE '%user1%'
    OR
    Q.content LIKE '%user1%'
    OR
    SU.username LIKE '%user1%'
);
```

### `# 문제점 : 1번 질문 2번 나옴, 2번 질문 안나옴`

`DISTINCT Q.*` <<를 기준으로 모은다.

`GROUP BY Q.id` <<와 같은 것을 모은다. 그래서 둘다 사용해도 됨.

```java
# 문제점 : 1번 질문 2번 나옴, 2번 질문 안나옴
SELECT Q.*
FROM question AS Q
INNER JOIN answer AS A
ON Q.id = A.question_id;

# 문제점 : 1번 질문 2번 나옴[해결], 2번 질문 안나옴
SELECT DISTINCT Q.*  #Q.id, A.id 큰 것을 뽑아 쓰려면 MAX를 걸어주자.
FROM question AS Q
INNER JOIN answer AS A
ON Q.id = A.question_id;

# 문제점 : 1번 질문 2번 나옴[해결], 2번 질문 안나옴
SELECT Q.*
FROM question AS Q
INNER JOIN answer AS A
ON Q.id = A.question_id
GROUP BY Q.id;

# 문제점 : 1번 질문 2번 나옴[해결], 2번 질문 안나옴[해결]
SELECT Q.id, A.id
FROM question AS Q
LEFT JOIN answer AS A
ON Q.id = A.question_id
GROUP BY Q.id, A.id;
```

### `# 제목 + 내용 + 작성자 + 답변내용 검색`

작성자가 웬만하면 질문에 있기때문에 `inner`를 해줘도 되지만

답변은 작성자가 무조건 있기 때문에 `left`해준다.

`GROUP BY`로 그루핑까지 해준다. (DISTINCT 키워드를 사용해도 된다.)

```java
SELECT Q.*
FROM question AS Q
LEFT JOIN site_user AS SU
ON Q.author_id = SU.id
LEFT JOIN answer AS A
ON Q.id = A.question_id
WHERE (
    Q.subject LIKE '%sbb는 질문답변 게시판 입니다.%'
    OR
    Q.content LIKE '%sbb는 질문답변 게시판 입니다.%'
    OR
    SU.username LIKE '%sbb는 질문답변 게시판 입니다.%'
    OR
    A.content LIKE '%sbb는 질문답변 게시판 입니다.%'
)
GROUP BY Q.id;
```

### `제목 + 내용 + 작성자 + 답변내용 + 답변작성자 검색`

admin은 1번회원임.

1번 답변은 1번 회원이 썼다 (admin)

1번 답변은 1번 질문에 대한 글이다.

```
SELECT Q.*
FROM question AS Q
LEFT JOIN site_user AS SU
ON Q.author_id = SU.id
LEFT JOIN answer AS A
ON Q.id = A.question_id
LEFT JOIN site_user AS A_SU # 질문에 대한 답변자
ON A.author_id = A_SU.id #답변에 대한 작성자
WHERE (
    Q.subject LIKE '%admin%' 
    OR
    Q.content LIKE '%admin%'
    OR
    SU.username LIKE '%admin%'
    OR
    A.content LIKE '%admin%'
    OR
    A_SU.username LIKE '%admin%'
)
GROUP BY Q.id;
```

---

참고자료 : 

hosts 파일 수정에 관한 글 (상훈님 감사합니다)

[[윈도우10팁#004]호스트(hosts) 파일 수정하는 방법 알아보기](https://allstall.tistory.com/35)
## 김상훈
# linux-포트포워딩

<aside>
📢  **포트포워딩**
포트포워딩은 단순하게 호스트OS 의 ip+포트번호가 게스트OS 의 ip+포트번호로 
이동할수 있도록 다리를 놓아주는것과 같다.

</aside>

# CentOs 포트포워딩 설정방법

- **VirtualBox의 설정⇒네트워크⇒어댑터⇒고급⇒포트포워딩으로 이동하기**
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/13.png)
    
- **포트포워딩 이름과 연결해주고싶은  host,guest의 ip와 포트번호 작성**
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/14.png)
    
- **Mysql을 HostOs의 3308 포트로 접속해보기 
(먼저 Mysql 안에 lldj 유저를 만들어 줘야함)**
    
    ```sql
    GRANT ALL PRIVILEGES ON *.* TO 유저이름@'%' IDENTIFIED BY '비밀번호';
    ```
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/15.png)
    
- **Putty로 HostOs의 2022포트로 linux에 접속해보기**
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/16.png)
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/17.png)
    

# SpringBoot-QueryDsl

<aside>
📢 **QueryDsl**
JPA 네이밍쿼리로 만들기 복잡하거나 네이티브쿼리나JPQL로 작성할경우 문자열로 작성해서 어느 부분에서 오류가 발생한지 확인하기 어려운경우 사용하도록 만들어진 라이브러리

</aside>

**QueryDsl 설정을위한 추가 application.properties**

```java
// 1. queryDsl version 정보 추가
buildscript {
	ext {
		queryDslVersion = "5.0.0"
	}
}
plugins {
	...
	// 2. querydsl plugins 추가
	id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
	...
}

dependencies {
...
	// 3. querydsl dependencies 추가
	implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
	implementation "com.querydsl:querydsl-apt:${queryDslVersion}"
...
}
...
//4.querydsl 추가 시작(QEntity를 보관할 장소 지정)
def querydslDir = "$buildDir/generated/querydsl"

querydsl {
	jpa = true
	querydslSourcesDir = querydslDir
}
sourceSets {
	main.java.srcDir querydslDir
}

configurations {
	querydsl.extendsFrom compileClasspath
}

compileQuerydsl {
	options.annotationProcessorPath = configurations.querydsl
}

```

**QueryDsl 설정을 위한 Custom,Impl파일 생성**

<aside>
📢 **핵심**
interface 인 JpaRepository를 상속받으면 EntityRepository가 기능을 사용할수 있는것처럼 QueryDsl 또한 interface로 생성하고(Custom) 구현체(Impl)를 만들어주면 
자동으로 스프링부트가 EntityRepository가  사용할수 있도록 설정해준다.
(프록시패턴으로 구현되었다고함 )

</aside>

**Custom**

```java
public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
}
```

**EntityRepository 가** **Custom 상속**

```java
public interface SiteUserRepository extends JpaRepository<SiteUser,Long>,SiteUserRepositoryCustom {
}
```

**Custom 구현체 (Impl)**

```java
@RequiredArgsConstructor
public class SiteUserRepositoryImpl implements  SiteUserRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public SiteUser getQslUser(Long id) {
        return jpaQueryFactory
                .select(QSiteUser.siteUser)
                .from(QSiteUser.siteUser)
                .where(QSiteUser.siteUser.id.eq(id))
                .fetchOne();
    }
}
```

**QueryDsl 생성을위한 Config**

```java
@Configuration
public class AppConfig {
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}
```

**QueryDsl Test**

```java
@Test
    public void getQslUser(){
        SiteUser siteUser=siteUserRepository.getQslUser(1L);
        assertEquals("user1",siteUser.getUsername());
    }
```

## **회고 과정에서 나왔던 질문 (최소 200자 이상)**
서로 피드백한 댓글을 첨부합니다.
### 이소영님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/review_1.PNG)
### 김지훈님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/review_2.PNG)
### 도성구님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/review_3.PNG)
### 김상훈님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/review_4.PNG)
### 서진수님 회고록 댓글
---
## **회고 인증샷 & 팀 자랑**
- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- 이번주는 다같이 회의한 사진으로 대체합니다!!
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2340/Retrospec_Log/image/week3/screenshot.png)
    
- 필수) 자랑 멘트는 **팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지**에 대해 간단하게 작성해 주시면 됩니다 😊
     - 요즘 기능구현 과 학습 진도를 동시에 따라 가기 버거웠던 부분이 많았는데 각자의 회고록을 보면서 진도에대한 복습및 이해를 다시금 할수있는 기회가 되었습니다.
