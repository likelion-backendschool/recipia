## **팀 구성원**
- [팀장] 김지훈, 도성구, 김상훈, 이소영 서진수
---
## **회고 내용 요약 (최소 500자 이상)**
팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂
## 이소영
## 인프라
### pure-ftpd
- 클라이언트가 서버에 접속할 수 있도록 요청한다면?
    - 방법1(SFTP방법) : 사용자 계정을 하나 만들어서 준다.
    - 방법2(FTP): pure-ftpd를 사용해 부분적으로 접근이 가능하도록한다.
- 방법1의 경우는 사용자가 모든 서버의 기능에 접촉할수 있기 때문에 리스크가 크다.
- 따라서 pure-ftpd를 이용해 가상 사용자를 만들어 서버의 필요한 부분만 접속할 수 있도록 한다.
- MySQL에 외주를 준다!
    - 내가 모든 계정을 관리할 수는 없기 때문에 MySQL에 외주를 준다.
    - 특정 아이디와 비밀번호를 입력하면 특정 부분에만 접근할 수 있다.
    - 이 과정에서 요청이 들어오면 MySQL에서 사용자 정보의 유무를 확인한 뒤 결과를 내보냄
- pure-ftpd 설치 및 활성화
```java
sudo yum install pure-ftpd -y
sudo vim /etc/pure-ftpd/pure-ftpd.conf
MySQLConfigFile /etc/pure-ftpd/pureftpd-mysql.conf
위 라인에서 주석해제하시고, pureftpd-mysql.conf 설정파일위치도 하드코딩
sudo vim /etc/pure-ftpd/pureftpd-mysql.conf
MYSQLSocket /var/lib/mysql/mysql.sock
mysql 소켓 파일 확인은 mysql이 돌아가고 있는 상태에서 sudo updatedb 명령 후 sudo locate mysql | fgrep sock 로 경로를 찾을 수 있다.
MYSQLUser pureftpd
pure-ftpd 에서 mysql 에 접속할 때 사용할 ID
MYSQLPassword lldj123414
pure-ftpd 에서 mysql 에 접속할 때 사용할 PW
MYSQLDatabase pureftpd
ftp 사용자 정보 DB
MYSQLCrypt cleartext
비번은 평문으로 저장 하겠다.
```
- 테스트
    - 외부에서 먼저 테스트를 하면 안된다. → 문제가 생겼을 때 방화벽 문제인지, 설정 문제인지 알 수 없기 때문
- ftps 적용하기
    - 인증서 생성
    
    ```java
    sudo mkdir -p /etc/ssl/private
    
    sudo openssl req -x509 -nodes -days 7300 -newkey rsa:2048 -keyout /etc/ssl/private/pure-ftpd.pem -out /etc/ssl/private/pure-ftpd.pem
    
    엔터로 넘김
    ```
    
    - TLS 설정 활성화
    
    ```java
    sudo vim /etc/pure-ftpd/pure-ftpd.conf
    
    TLS 1
    
    CertFile /etc/ssl/private/pure-ftpd.pem
    ```
    
### POST MAN
- 일반 웹 브라우저에서는 POST, GET만 가능하다
- POST MAN은 개발자들이 쓰는 일종의 브라우저임.
- GET, POST, PATCH까지 가능해 웹 개발에서 많이 이용됨.
    
## 김지훈 
## 목차
### 인프라
1. Linux
    1. 리눅스 실습 단계
    2. 파일로 영속화
    3. DB로 영속화
### 스프링부트
1. QDSL
    1. 특**정 회원이 팔로우 중인 회원들의 관심사 문제**
2. 파일 업로드
    1. 파일 업로드에 도입된 내용
    2. BaseEntity.class
    3. GenFile.class
    4. Yml 확장자
---
# 인프라
## Linux
---
### 리눅스 실습 단계
1. vim을 이용해 웹페이지 생성
2. 파일질라로 외부에서 파일 업로드
3. sh로 html 템플릿 만들어 생성
4. php로 html 템플릿
5. nginx의 요청과 php를 연동(php-fpm이 중간다리로 이용)
6. 쿠키
7. 세션
### 파일로 영속화
여태까지는 세션으로 사용자의 정보를 저장해왔다면 보다 더 오래 저장하고 싶었고 그 수단 중 하나로 파일을 사용하여 여러 정보들을 관리하기 시작했다. 파일로 영속화를 진행하는 실습을 해보자.
1. **메모리(하드디스크)에 저장하는 php 파일 생성**
    
    파일 경로: `**/web/site4/store_by_file.php**`
    
    ```php
    **<?php
    $name = "홍길동";
    file_put_contents('data/1.txt', $name);**
    ```
    
2. **메모리(하드디스크)에 저장된 php 파일 내용 불러오기**
    
    파일 경로: `**/web/site4/get_by_file.php**`
    
    ```php
    **<?php
    $name = file_get_contents('data/1.txt');
    echo $name;**
    ```
    
    <aside>
    💡 **Tip. 파일이 생성됐지만 내용이 저장되지 않는 이유는?
    디렉토리, 파일 모두 접근 허용범위, 즉 퍼미션을 가진다. 파일에 접근했는데 써지지 않거나 또는 디렉토리에 접근하지 못했을 때 해당 파일, 디렉토리의 퍼미션을 한번 확인해보자.**
    
    **파일, 디렉토리 퍼미션 확인하는 명령어**
    
    ```bash
    **ls -alh**
    ```
    
    ![1.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/1.png)
    
    **파일, 디렉토리 퍼미션 변경하는 명령어**
    
    ```bash
    chmod [원하는퍼미션범위] [적용할 파일이나 디렉토리] 
    # 예시: **chmod 777 data** "data 디렉토리에게 777에 해당하는 퍼미션을 부여한다."
    **# 퍼미션은 2진법으로 표현한다. r = 4(100), w = 2(010), x = 1(001)**
    ```
    
    **파일, 디렉토리 퍼미션**
    
    | 권한 | 파일 | 디렉토리 |
    | --- | --- | --- |
    | r(읽기) | 파일 내용 볼 수 있는 권한 | 디렉토리 내부를 볼 수 있는 권한 |
    | w(쓰기) | 파일 내용 수정할 수 있는 권한 | 디렉토리 내부에 파일을 생성 또는 수정할 수 있는 권한 |
    | x(실행) | 파일 실행할 수 있는 권한 | 디렉토리 내부에 접근할 수 있는 권한 |
    </aside>
    
3. **크롬으로 확인**
    1. [site4.com/store_by_file.php?name=성공입니다!!](http://site4.com/store_by_file.php?name=성공입니다!!fh)로 파일 생성
        
     ![2.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/2.png)
        
    2. [site4.com/get_by_file.php](http://site4.com/get_by_file.php로) 로 생성한 파일 내용 조회
        
      ![3.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/3.png)
        
### DB로 영속화
파일로 저장하다보니 데이터를 좀 더 체계적으로 관리하고 싶어졌고 DB로 저장하면서 이를 해결할 수 있다. 
1. **DB 및 테이블 생성**
    
    DB 사용자명: `**lldj**`
    
    DataBase명: `**site4_name**`
    
    Table명: `**name**`
    
    ```sql
    **mysql -u lldj -p
    # DB lldj 비번 입력
    
    DROP DATABASE IF EXISTS site4_name;
    CREATE DATABASE site4_name;
    USE site4_name;
    
    CREATE TABLE `name` (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        PRIMARY KEY(id),
        create_date DATETIME NOT NULL,
        `name` VARCHAR(100) NOT NULL
    );
    
    exit or Ctrl + C**
    ```
    
2. **DB에 현재 시간과 url 파라미터 name을 저장하는 php 파일 생성**
    
    파일 경로: `**/web/site4/store_by_db.php**`
    
    ```php
    **<?php
    $name = $_GET['name'];
    
    // 접속 정보
    $dbHost = '127.0.0.1';
    $dbId = 'lldj';
    $dbPass = 'lldj123414';
    $dbName = 'site4_name';
    
    // DB 연결
    $dbConn = mysqli_connect($dbHost, $dbId, $dbPass, $dbName) or die("DB CONNECTION ERROR");
    
    // SQL INJECTION 방어
    $name = mysqli_real_escape_string($dbConn, $name);
    
    $sql = "
    INSERT INTO name
    SET create_date = NOW(),
    `name` = '{$name}'
    ";
    
    mysqli_query($dbConn, $sql);
    $id = mysqli_insert_id($dbConn);
    ?>
    <meta charset="UTF-8">
    <h1><?=$id?>번 이름이 저장되었습니다</h1>**
    ```
    
    - DB에 접속할 수 있게 접속 정보를 적어줘야한다.
        - `**$dbHost**`: DB의 ip 주소
        - `**$dbId**`: DB 사용자명
        - `**$dbPass**`: DB 사용자의 패스워드
        - `**$dbName**`: DB 명
3. **DB에 저장된 데이터를 웹페이지에 표시해주는 php 파일 생성**
    
    파일 경로: `**/web/site4/get_by_db.php**`
    
    ```php
    **<?php
    // 접속 정보
    $dbHost = '127.0.0.1';
    $dbId = 'lldj';
    $dbPass = 'lldj123414';
    $dbName = 'site4_name';
    
    // DB 연결
    $dbConn = mysqli_connect($dbHost, $dbId, $dbPass, $dbName) or die("DB CONNECTION ERROR");
    
    $sql = "
    SELECT *
    FROM `name`
    ORDER BY id DESC
    LIMIT 100
    ";
    $rs = mysqli_query($dbConn, $sql);
    
    $rows = [];
    while ( $row = mysqli_fetch_assoc($rs) ) {
            $rows[] = $row;
    }
    ?>
    
    <meta charset="UTF-8">
    <h1>이름들</h1>
    
    <table border="1">
    <thead>
            <tr>
                    <th>ID</th>
                    <th>날짜</th>
                    <th>이름</th>
            </tr>
    </thead>
    <tbody>
            <?php foreach ( $rows as $row ) { ?>
            <tr>
                    <td><?=$row['id']?></td>
                    <td><?=$row['create_date']?></td>
                    <td><?=$row['name']?></td>
            </tr>
            <?php } ?>
    </tbody>
    </table>**
    ```
    
    - DB 테이블에 있는 내용을 HTML 파일에서 표로 나타냈다.
4. [**site4.com/get_by_db.php](http://site4.com/get_by_db.php로) 로 DB 테이블 웹사이트에서 확인**
    
   ![4.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/4.png)
   
# 스프링 부트
## QDSL
---
### 특**정 회원이 팔로우 중인 회원들의 관심사**
1. SQL문으로 문제풀이
    
    ```sql
    **SELECT DISTINCT IK.content
    FROM interest_keyword AS IK
    INNER JOIN site_user AS SU1
    ON IK.user_id = SU1.id
    INNER JOIN site_user_followers AS SUFR
    ON SU1.id = SUFR.site_user_id
    INNER JOIN site_user AS SU2
    ON SUFR.followers_id = SU2.id
    WHERE SU2.id = 8;**
    ```
    
2. QDSL문으로 문제풀이
    
    파일위치: `**package com.example.sbb_qsl.user.repository.UserRepositoryImpl;**`
    
    ```java
    **jpaQueryFactory
        .select(interestKeyword.content)
        .distinct()
        .from(interestKeyword)
        .innerJoin(interestKeyword.user, siteUser) // site_user
        .innerJoin(siteUser.followers, siteUser2)
        .where(siteUser2.id.eq(user.getId()))
        .fetch();**
    ```
    
## 파일 업로드
---
### 파일 업로드에서 도입된 내용
- BaseEntity.class
- GenFile.class
- yml 확장자
### BaseEntity.class
말그대로 기본 엔티티다. 보통 데이터베이스 테이블, 즉 엔티티들은 각자의 고유의 번호, 생성 날짜, 수정 날짜를 가진다고 하면 매번 적는 일이 귀찮을 것이다. 이때 BaseEntity에 선언해 클래스를 상속하듯 다른 엔티티에 상속해주면 편하다. 
파일 경로: `**package com.ll.exam.app10.app.base.entity.BaseEntity.class;**`
```java
**@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;
}**
```
**사용된 어노테이션 정리**
- `@CreateDate` : 스프링에서 알아서 현재 시간을 주입시켜주는 어노테이션
- `@LastModifiedeDate` : 스프링에서 알아서 최근 수정된 시간을 주입시켜주는 어노테이션
### GenFile.class
자바에선 File이란 클래스가 이미 존재하기 때문에 GenFile이라고 클래스명을 정했다. 파일 업로드를 위한 엔티티클래스이며 앞선 BaseEntity를 상속 받은 것을 볼 수 있다. 
파일 경로: `**package com.ll.exam.app10.app.fileUpload.entity.GenFile.class;**`
```java
**@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class GenFile extends BaseEntity {
    private String relTypeCode;
    private int relId;
    private String typeCode;
    private String type2Code;
    private String fileExtTypeCode;
    private String fileExtType2Code;
    private int fileSize;
    private int fileNo;
    private String fileExt;
    private String fileDir;
    private String originFileName;
}**
```
### Yml 확장자
프로그램을 세팅하는 파일의 확장자이다. 이전까지는 예를들어 아래와 같이 **`[application.properties](http://application.properties)` 를 많이 사용했다.**   

![5.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/5.png)
간단히 설명하자면 이와 같은 프로퍼티 파일을 좀 더 사람이 보기 쉽게 만든 마크업 문법이다. 자세한 문법은 여기서는 언급하지 않고 대략의 형식만 익혀보자.
```yaml
**spring:
  profiles:
    active: dev, base-addi
  thymeleaf:
    cache: false
    prefix: file:src/main/resources/templates/
  devtools:
    livereload:
      enabled: true
    restart:
      enabled: true
  datasource:
    url: jdbc:mariadb://127.0.0.1:3306/app10?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul
    username: root
    password: 
    driver-class-name: org.mariadb.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: create
  custom:
    genFileDirPath: /temp/app10**
```
<aside>
💡 **Tip. Properties와 yml 변환 사이트**
[Yaml Converter - MAGEDDO](http://mageddo.com/tools/yaml-converter)
</aside>

## 도성구
### coding game
mode : Java
[Practice your coding skills on short coding contests - Clash of Code](https://www.codingame.com/multiplayer/clashofcode)
---
### Cent OS 90강~
> **각 단계 살펴보기**
> 
1) vim
2) 파일질라로 업로드
3) sh로 HTML 템플릿
4) php로 HTML 템플릿
5) nginx의 요청과 php를 연동 (php-fpm이 중간다리)
쿠키(js에서 온 것 웹은 기억력이 없다.) - **클라이언트**
6) 세션 사용해보기 - **서버**
<aside>
✅ **php 안에 있는 모든 함수는 메모리에 있다**
DB의 본질은 영속 저장소
</aside>
---
**91강**

> # 문제 

https://perfectdomain.com/domain/site4.com
// 임꺽정

https://perfectdomain.com/domain/site4.com
// 임꺽정

https://perfectdomain.com/domain/site4.com
// 홍길순
> 
힌트 1)
```yaml
$cd /web/site4 
 $mkdir data
$chmod 777 data        
```
Tip. chmod → 나 / 그룹 / other 읽기(R) 쓰기(W) 실행(X)
    
![Untitled.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled.png)
    
- *힌트 2)*
file_put_contents('data/1.txt', $name);
$name = file_get_contents('data/1.txt');
    
    
    stroe_by_file.php
    
    ![Untitled2.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled2.png)
    
    get_by_file.php
    
    ![Untitled3.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled3.png)
    
> **result**
> 
![Untitled4.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled4.png)
---
**93강** 
> DB 및 테이블 생성
> 
![Untitled5.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled5.png)
> PHP에 mysqli 모듈 추가설치
> 
```java
sudo yum install php-mysqli
sudo systemctl restart php-fpm
```
![Untitled6.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled6.png)
> /web/site4/store_by_db.php 생성
> 
- **code**
    
    ```java
    <?php
    $name = $_GET['name'];
    
    // 접속 정보
    $dbHost = '127.0.0.1';
    $dbId = 'lldj';
    $dbPass = 'lldj123414';
    $dbName = 'site4_name';
    
    // DB 연결
    $dbConn = mysqli_connect($dbHost, $dbId, $dbPass, $dbName) or die("DB CONNECTION ERROR");
    
    // SQL INJECTION 방어
    $name = mysqli_real_escape_string($dbConn, $name);
    
    $sql = "
    INSERT INTO name
    SET create_date = NOW(),
    `name` = '{$name}'
    ";
    
    mysqli_query($dbConn, $sql);
    $id = mysqli_insert_id($dbConn);
    ```
    
> 크롬에서 테스트
> 
```java
site4.com/store_by_db.php?name=Paul
site4.com/store_by_db.php?name=Sam
```
> DB 접속에서 데이터가 잘 들어가 있는지 테스트
> 
![Untitled7.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled7.png)
---
**94강** 
- ***Linux 한글 깨질 때?***
[[MariaDB] Setting utf8mb4 Character Set](https://medium.com/oldbeedev/mysql-utf8mb4-character-set-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0-da7624958624)
$ sudo updatedb
$ sudo locate mariadb | fgrep cnf //**경로 찾기** 
$ sudo nano /etc/my.cnf.d/mariadb-server.cnf 
```java
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
skip-character-set-client-handshake
```
$ sudo systemctl restart mariadb
$ sudo systemctl restart nginx
$ sudo systemctl restart php-fpm
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/57444144-86c2-4af1-9e4a-ad47a359876c/Untitled.png)
**95강** 
8단계 DB에서 데이터 단건 조회
> /web/site4/get_by_db.php 생성
> 
- code
    
    ```java
    <?php
    // 접속 정보
    $dbHost = '127.0.0.1';
    $dbId = 'lldj';
    $dbPass = 'lldj123414';
    $dbName = 'site4_name';
    
    // DB 연결
    $dbConn = mysqli_connect($dbHost, $dbId, $dbPass, $dbName) or die("DB CONNECTION ERROR");
    
    $sql = "
    SELECT *
    FROM `name`
    ORDER BY id DESC //내림차순
    LIMIT 1 //제일 위에 있는 것 (최신)
    ";
    $rs = mysqli_query($dbConn, $sql);
    $lastNameRow = mysqli_fetch_assoc($rs);
    ?>
    <meta charset="UTF-8">
    <h1>$lastNameRow['name']</h1>
    ```
    
**96강** 
- ***가장 최근에 저장된 이름 출력하기***
192.168.56.102:8024/get_by_db.php 
> /web/site4/get_by_db.php 생성
> 
- **code**
    
    ```java
    <?php
    // 접속 정보
    $dbHost = '127.0.0.1';
    $dbId = 'lldj';
    $dbPass = 'lldj123414';
    $dbName = 'site4_name';
    
    // DB 연결
    $dbConn = mysqli_connect($dbHost, $dbId, $dbPass, $dbName) or die("DB CONNECTION ERROR");
    
    $sql = "
    SELECT *
    FROM `name`
    ORDER BY id DESC
    LIMIT 100
    ";
    $rs = mysqli_query($dbConn, $sql);
    
    $rows = [];
    while ( $row = mysqli_fetch_assoc($rs) ) {
            $rows[] = $row;
    }
    ?>
    
    <meta charset="UTF-8">
    <h1>이름들</h1>
    
    <table border="1">
    <thead>
            <tr>
                    <th>ID</th>
                    <th>날짜</th>
                    <th>이름</th>
            </tr>
    </thead>
    <tbody>
            <?php foreach ( $rows as $row ) { ?>
            <tr>
                    <td><?=$row['id']?></td>
                    <td><?=$row['create_date']?></td>
                    <td><?=$row['name']?></td>
            </tr>
            <?php } ?>
    </tbody>
    </table>
    ```
    
> **result**
> 
![Untitled8.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/Untitled8.png)
---
### ****JPA QueryDSL****
[위캔 | Ken 9943](https://wiken.io/ken/9943)
https://github.com/SG1515/Sb_QueryDSL
[JPA QueryDSL](https://www.notion.so/JPA-QueryDSL-d277b8aa0c504c229b2bf65ff0444df4) 
---
### 스프링부트 기초 - 파일 업로드
[위캔 | Ken 10164](https://wiken.io/ken/10164)
https://github.com/SG1515/Sb_file_upload
---
참고자료 :
https://data-make.tistory.com/658
## 김상훈

<aside>
💡 **9/6**

### linux-ftp

**ftp란**

파일 전송 프로토콜은 하나의 호스트에서 다른 호스트로 파일을 복사 하기위해 TCP/IP에 의해 제공되는 표준 기능이다

**ftp 사용이유**

sftp는 너무 큰 권한을 주는 것이다.
ftp로는 특정 폴더만 공개할 수 있다.

**ftp 흐름**

pure-ftpd 사용→ 사용자 관리를 MySQL에 위임→요청하는(아쉬운)쪽은 pureftpd이기 
때문에 mysql 접속정보를 ftpd에 설정해줘야한다.

****pure-ftpd 설치 및 활성화****

```bash
//설치
1. sudo yum install pure-ftpd -y

//pure-ftpd 설정파일 접속
2. sudo vim /etc/pure-ftpd/pure-ftpd.conf 
-> MySQLConfigFile /etc/pure-ftpd/pureftpd-mysql.conf 
//주석해제후 pureftpd-mysql.conf 설정파일위치 하드코딩

//사용자 관리 설정파일 접속
3.sudo vim /etc/pure-ftpd/pureftpd-mysql.conf 
->MYSQLSocket /var/lib/mysql/mysql.sock //소켓통신 설정
->MYSQLUser pureftpd  //mysql접속아이디
->MYSQLPassword pureftpd//mysql접속 비밀번호
->MYSQLDatabase pureftpd //mysql DB 설정
->MYSQLCrypt cleartext //비밀번호 설정

//루트계정으로 접속후 접속유저생성
4. mysql -u root -p root 
GRANT ALL PRIVILEGES ON pureftpd.* TO pureftpd@'localhost' IDENTIFIED BY 'pureftpd';

//MySQL에서 pureftpd DB 세팅
5.
DROP DATABASE IF EXISTS pureftpd;

CREATE DATABASE pureftpd;

USE pureftpd;
//테이블생성
CREATE TABLE users (
	uidx int(10) unsigned NOT NULL AUTO_INCREMENT,
    `user` varchar(100) NOT NULL,
    `password` varchar(100) NOT NULL,
    `gid` int(10) unsigned NOT NULL,
    `uid` int(10) unsigned NOT NULL,
    `occurDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` tinyint(1) unsigned NOT NULL DEFAULT '1',
    `ipaccess` varchar(15) NOT NULL,
    `comment` varchar(100) NOT NULL,
    `ulBandWidth` smallint(5) unsigned NOT NULL,
    `dlBandWidth` smallint(5) unsigned NOT NULL,
    `quotaSize` smallint(5) unsigned NOT NULL,
    `quotaFiles` int(10) unsigned NOT NULL,
    `dir` varchar(100) NOT NULL,
    PRIMARY KEY (`uidx`)
);
//유저생성
insert  into `users`(`uidx`,`user`,`password`,`gid`,`uid`,`occurDate`,`status`,`ipaccess`,`comment`,`ulBandWidth`,`dlBandWidth`,`quotaSize`,`quotaFiles`,`dir`) values 
(1,'site1','1234',1000,1000,NOW(),1,'*','',0,0,0,0,'/web/site1'),
(2,'site2','1234',1000,1000,NOW(),1,'*','',0,0,0,0,'/web/site2'),
(3,'site3','1234',1000,1000,NOW(),1,'*','',0,0,0,0,'/web/site3');

//파일질라로 접속테스트
6.
HOST : 192.168.56.106
PORT : 21
PROTOCOL : FTP
ID : site1
PW : 1234
```

---

## springboot-파일저장,파일호출

### 외부저장경로를 URL로 호출하기

**application.yml 설정**

```
//application.yml
...

custom:
  genFileDirPath: c:/upload/app10 //c드라이브에 upload 폴더안에 app10 경로

...

```

application.yml 파일로 이미지저장소의 실제 경로 명시해준것이다.

---

**WebMvcConfig.class 설정**

```
package com.ll.exam.app10.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	//경로를 변수에 지정
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	//addResourceHandler->URL지정
        registry.addResourceHandler("/gen-file/**")
        		//addResourceLocations->저장소위치-> file:///경로/ 형식이 규칙임!
                .addResourceLocations("file:///" + genFileDirPath + "/");
    }
}

```

WebMvcConfigurer 를 구현해서 URL을 통해 외부 저장소를 접근할수있도록 설정해준것이다.

---

**파일 저장 및 로그인 구현**

**MemberController.class**

```bash
package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

		//join 작성폼 으로 이동
    @GetMapping("/join")
    public String memberJoinForm(Model model){
        return "member/joinForm";
    }

		//회원가입
    @PostMapping("/join")
    public String memberJoin(String membername,String password,String email,  MultipartFile profileImg,HttpSession session){
        Member findMember=memberService.getMemberBymembername(membername);
			
				//중복로그인
        if(findMember!=null){
            return "redirect:/?errorMsg=Already done.";
        }
				//회원가입
        Member member=memberService.join(membername,"{noop}"+password,email, profileImg);
				
				//로그인 정보 세션에 저장
        session.setAttribute("loginedMemberId",member.getId());

        return "redirect:/member/profile";
    }

		//프로필 보기로 이동
    @GetMapping("/profile")
    public String memberProfile(HttpSession session,Model model){
        Long loginedMemberId=(Long)session.getAttribute("loginedMemberId");
        boolean isLogined=loginedMemberId!=null;

        if(isLogined==false){
            return "redirect:/?errorMsg=Need to login!";
        }
				//세션으로 memberId를 가져와서 member 찾기
        Member loginedMember=memberService.getMemberById(loginedMemberId);
        model.addAttribute("loginedMember",loginedMember);
        return "/member/profile";
    }
}
```

**MemberService.class**

```bash
package com.ll.exam.app10.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
		//파일 경로
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final MemberRepository memberRepository;

		//member를 이름으로 찾기
    public Member getMemberBymembername(String membername) {
        return memberRepository.findByMembername(membername);
    }
		
	//이미지 파일저장및 회원저장
    public Member join(String membername, String password, String email, MultipartFile  profileImg) {
        String profileImgPath="member/"+ UUID.randomUUID()+".png";
        File profileImgFIle =new File(genFileDirPath+"/"+profileImgPath);
        profileImgFIle.mkdirs();
        try{
            profileImg.transferTo(profileImgFIle);
        }catch (IOException e){
            throw new RuntimeException();
        }

        Member member=new Member(null,membername,email,password,profileImgPath);

        return memberRepository.save(member);
    }

		//id로 member 찾기
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}
```

</aside>
  
## 서진수
  
인프라
FTP
- 빠른 파일 전송이 주된 목적이라 동작 방식이 단순하고 직관적
- 기본 포트는 21번(명령어 전달), 20번(데이터 전달)
- 데이터 전달 방식
- Active Mode
- 클라이언트가 서버에게 포트번호를 알려주면 서버가 접속
- Passive Mode
- 서버가 클라이언트에게 포트번호를 알려주면 클라이언트가 접속
- 20번 포트 대신 1024번 이후의 임의 포트번호를 사용
FTPS
- https 와 동일한 개념
- FTP에 TLS/SSL를 결합해 https 방식과 마찬가지로 공개키 암호화 방식을 사용하는 프로토콜, FTP 확장판
    
    
- SSL 위에서 FTP를 수행하는 것으로 명령어와 데이터 모두 암호화
**Postman**
- API를 개발, 테스트, 공유 및 문서화하는 데 사용되는 API 클라이언트로, 엔드 포인트 **URL을 입력하는 백엔드 테스트에 사용되며 서버로 요청을 보내고 서버에서 응답을 받는다**.
=> POSTMAN은 REST API형태로 사용 가능하다.
HTTP 메서드(Hypertext Transfer Protocol)
: 리소스에 수행해야 하는 작업을 서버에 알려준다.
1. GET
- 데이터를 읽어올 때 사용
- GET 요청을 캐싱(저장)하고 REST API 요청에 파라미터(SW나 시스템 상의 작동에 영향을 미치며, 외부로부터 투입되는 데이터)를 넣어 전송하여 전송 전에 데이터를 필터링하도록 서버에 지시 가능
- 사용방법
```
(1) 전부(원하는 정보) 다 읽어오는 방법
1) GET 선택
2) URL 입력 / 'URL /읽고 싶은 데이터의 PK값' 입력
3) Send
```
2. POST
- 데이터를 추가, 입력할 때 사용
- 동일한 POST 요청을 여러번 전송하면 동일한 리소스를 여러 번 생성하는 부작용
- 사용 방법
```
(1) POST 선택
(2) URL 입력
(3) 입력할 데이터 작성
(4) Body-raw-JSON 선택
(5) Send
```
3.PUT
- 데이터를 수정할 때 사용
```
(1) PUT 선택 
(2) 'URL/수정하고 싶은 데이터의 PK값' 입력 
(3) Body-raw-JSON 선택 
(4) Send 
```
4. DELETE
- 데이터를 삭제할 때 사용
```
(1) DELETE 선택
(2) 'URL/삭제하고 싶은 데이터의 PK값' 입력
(3) Send
```
 HTTP 헤더
- 요청 헤더는 클라이언트와 서버 간에 교환되는 메타데이터이다. 예를 들어, 요청 헤더는 요청 및 응답의 형식을 나타내고 요청 상태 등에 대한 정보를 제공한다.
1. 데이터
- REST API 요청에는 POST, PUT 등 기타 HTTP 메서드가 성공적으로 작동하기 위한 데이터가 포함될 수 있다.
2. 파라미터
- REST API 요청에는 수행해야 할 작업에 대한 자세한 정보를 서버에 제공하는 파라미터가 포함될 수 있다. 예를 들어, 경로 파라미터(URL 세부정보 지정), 쿼리 파라미터(리소스에 대한 추가 정보 요청), 쿠키 파라미터(클라이언트를 빠르게 인증함)
CSRF
- **사이트 간 요청 위조**(또는 **크로스 사이트 요청 위조)** 는 [웹사이트](https://ko.wikipedia.org/wiki/%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8) [취약점 공격](https://ko.wikipedia.org/wiki/%EC%B7%A8%EC%95%BD%EC%A0%90_%EA%B3%B5%EA%B2%A9)의 하나로, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)를 특정 웹사이트에 요청하게 하는 공격을 말한다.
- 유명 경매 사이트인 [옥션](https://ko.wikipedia.org/wiki/%EC%98%A5%EC%85%98_(%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8))에서 발생한 개인정보 유출 사건에서 사용된 공격 방식 중 하나다.
- [사이트 간 스크립팅](https://ko.wikipedia.org/wiki/%EC%82%AC%EC%9D%B4%ED%8A%B8_%EA%B0%84_%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8C%85)(XSS)을 이용한 공격이 사용자가 특정 웹사이트를 신용하는 점을 노린 것이라면, 사이트간 요청 위조는 특정 웹사이트가 사용자의 [웹 브라우저](https://ko.wikipedia.org/wiki/%EC%9B%B9_%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80)를 신용하는 상태를 노린 것이다. 일단 사용자가 웹사이트에 [로그인](https://ko.wikipedia.org/wiki/%EB%A1%9C%EA%B7%B8%EC%9D%B8)한 상태에서 사이트간 요청 위조 공격 코드가 삽입된 페이지를 열면, 공격 대상이 되는 웹사이트는 위조된 공격 명령이 믿을 수 있는 사용자로부터 발송된 것으로 판단하게 되어 공격에 노출된다.
CSRF 토큰
- MVC Application에서는 서버에 들어온 요청이 실제 서버에서 허용한 요청이 맞는지 확인하기 위해 Spring에서 모든 View에 대해 토큰을 발행하고, (PATCH, POST, PUT DELETE) 에 해당하는 요청에 대해 토큰도 같이 포함시켜야 한다.

출처

https://blog.naver.com/kon_pig/222459118113

https://blog.naver.com/0yihyeon/222839637465

https://ko.wikipedia.org/wiki/%EC%82%AC%EC%9D%B4%ED%8A%B8_%EA%B0%84_%EC%9A%94%EC%B2%AD_%EC%9C%84%EC%A1%B0

</aside>

## **회고 과정에서 나왔던 질문 (최소 200자 이상)**

서로 피드백한 댓글을 첨부합니다.

### 이소영님 회고록 댓글

  ![11.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/11.png)
  
### 김지훈님 회고록 댓글

  ![22.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/22.png)
  
### 도성구님 회고록 댓글

  ![33.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/33.png)
  
### 김상훈님 회고록 댓글

  ![44.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/44.png)
  
### 서진수님 회고록 댓글

  ![55.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/55.png)
  
## **회고 인증샷 & 팀 자랑**
- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- 이번주는 다같이 회의한 사진으로 대체합니다!!
    
  ![123.png](https://github.com/likelion-backendschool/recipia/blob/develop/Retrospec_Log/image/week5/123.png)
    
- 필수) 자랑 멘트는 **팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지**에 대해 간단하게 작성해 주시면 됩니다 😊
     - 요즘 기능구현 과 학습 진도를 동시에 따라 가기 버거웠던 부분이 많았는데 각자의 회고록을 보면서 진도에대한 복습및 이해를 다시금 할수있는 기회가 되었습니다.
