## 팀 구성원
- [팀장] 김지훈, 도성구, 김상훈, 이소영 서진수
## 회고 내용 요약 (최소 500자 이상)
팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂

<details>
<summary>이소영 회고록 요약</summary>
<div markdown="1">
# 이소영

# GIT

## Source가 없다.

- 정답코드

```java
$ git push origin :foo

$ git fetch origin :bar
```

## pull인자들

- 정답코드

```java
$ git push origin :foo

$ git fetch origin :bar
```

# 인프라

## 현재 날짜 출력

```java
<?php
date_default_timezone_set('Asia/Seoul');
echo "현재 날짜 : ".date("Y-m-d H:i:s")."<br/>";
?>
```

- 서울 기준으로 날짜를 설정해 주고 원하는 형식으로 받을 수 있도록 한다.

- php 날짜 형식 변경 포맷 표(참고)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/98aca4a6-9cff-4d57-a802-d98413bde889/Untitled.png)

# QueryDSL

- addFollower 함수를 follow 함수로 변경

```java
//기존
public void addFollower(SiteUser follower) {
        followers.add(follower);
}

//변경
public void follow(SiteUser u2) {
        u2.getFollowers().add(this);
}
```

- follower == 구독하는 사람 following == 구독 대상

```java
//기존
public void follow(SiteUser u2) {
        u2.getFollowers().add(this);
}

//변경
public void follow(SiteUser following) {
        following.getFollowers().add(this);
}
```

- 자신이 자신을 구독할 수 없게 함

```java
public void follow(SiteUser following) {
        **if (this == following) return;  //1
        if (following == null) return;  //2
        if (this.getId() == following.getId()) return; //굵은 글씨 부분 추가 //3**

        following.getFollowers().add(this);
    }
```

1: 자신은 자신을 팔로우 할 수 없음

2: null값이 팔로우 할때

3: 이외의 경우 일 때 아이디를 받아 this에 넣는다.

- 팔로잉, 팔로우 목록 출력

```java
public Set<SiteUser> getFollowings() {
        return new HashSet<>();
    }
```

테스트 코드

@Test
    @DisplayName("특정회원의 follower들과 following들을 모두 알 수 있어야 한다.")
    @Rollback(false)
    void t15() {
        SiteUser u1 = userRepository.getQslUser(1L);
        SiteUser u2 = userRepository.getQslUser(2L);

        u1.follow(u2);

        // follower
        // u1의 구독자 : 0
        assertThat(u1.getFollowers().size()).isEqualTo(0);

        // follower
        // u2의 구독자 : 1
        assertThat(u2.getFollowers().size()).isEqualTo(1);

        // following
        // u1이 구독중인 회원 : 1
        assertThat(u1.getFollowings().size()).isEqualTo(1);

        // following
        // u2가 구독중인 회원 : 0
        assertThat(u2.getFollowings().size()).isEqualTo(0);
    }


</div>
</details>


<details>
<summary>서진수 회고록 요약</summary>
<div markdown="1">
# PHP

### 무엇인가?

- PHP는 Php Hypertext Preprocessor의 약자로, 서버 측에서 실행되는 프로그래밍 언어

- 웹 개발에 특화된 언어 C언어 기반이기 때문에 C언어에 익숙한 개발자들이 쉽게 접근 가능

### 장점

- 대부분의 운영체제에서 이용 가능, 빠른 생산성을 자랑, 운영 비용도 저렴

### 단점

- 간단한 사이트 제작에 최적화 → 방대한 웹사이트를 만들 경우, 개발자의 입장에서 체계적이지 않기 때문에 구조 잡기가 매우 힘듦

- 보안 문제 발생 가능

### 극복

- 2014년 페이스북 자사에서 PHP의 한계점을 보완하는 ‘**Hack’** 이라는 언어를 개발→ PHP를 버리지 않고, 단점을 보완하는 방향으로 개발

## **웹 서버**

### **CGI(Common Gateway Interface)**

- 웹 서버에서 어플리케이션을 작동시키기 위한 인터페이스. 정적인 웹서버를 동적으로 기능하게 하기 위해서 등장. 서버 프로그램과 외부 프로그램 간의 인터페이스

- 방식은 요청 → 웹서버(아파치 등) → (웹서버가 직접실행) 프로그램

### **WAS(Web Application Server)**

- 웹서버가 동적으로 기능하면 WAS. 즉, Web Server + CGI가 WAS. 우리가 하는 소규모 프로젝트에서는 일반적으로 웹서버와 WAS를 굳이 구분할 필요가 없음

- 접속자가 많은 서비스의 경우 CGI 방식보다 어플리케이션 서버 방식의 Throughput(처리량)이 더 좋음

### **WSGI(Web Server Gateway Interface)**

- 파이썬에서 어플리케이션, 즉 파이썬 스크립트(웹 어플리케이션)가 웹 서버와 통신하기 위한 인터페이스

- 프로토콜 개념으로 이해 가능. WSGI는 서버와 앱 양단으로 나뉘어져 있음. WSGI 리퀘스트를 처리하려면 서버에서 환경정보와 콜백함수를 앱에 제공해야. 앱은 그 요청을 처리하고 콜백함수를 통해 서버에 응답.

### **WSGI, CGI 방식의 차이**

```
아파치는 80번 포트를 듣고 있다가, 리퀘스트가 들어오면 이를 해석해서 응답합니다. 응답의 방법은
다양한데, 그중 하나는 CGI를 통해 스크립트를 실행시키는 것이고 다른 하나는 단순히 파일을 제공하는 
것입니다. CGI의 경우, 아파치는 환경을 준비하고 CGI 프로토콜에 따라 스크립트를 실행시킵니다. CGI
서브프로세스는 소켓과 stdout을 포함하여 OS 환경을 상속합니다. CGI 서브프로세스가 response를
작성하고, 이를 아파치로 보내면, 아파치는 이 response를 브라우저로 보냅니다. CGI는 원시적이라고 
볼 수 있습니다. 대부분 CGI는 모든 리퀘스트마다 서브프로세스를 fork합니다.
```

```
반면 WSGI는 CGI 디자인 패턴에 기반한 인터페이스입니다. 하지만 WSGI가 항상 CGI는 아닙니다. 
WSGI는 모든 리퀘스트에 대해 서브프로세스를 다 fork하지 않습니다. WSGI는 CGI가 될수도 있고
아닐수도 있습니다. WSGI는 CGI 디자인 패턴을 몇가지 중요한 방식으로 더했습니다. HTTP Request
Header를 파싱하고 이를 환경에 추가합니다. 이것은 환경에서 file-like object로써 POST-oriented
input을 제공합니다. 또한 사용자에게 수 많은 format 디테일로부터 해방시키고 response를 만들 수
있는 기능을 제공합니다
```

- WSGI가 CGI 디자인 패턴을 가져다 업그레이드 한 모델입니다. CGI는 모든 리퀘스트마다 fork를 통해 서브프로세스를 띄워 요청을 처리하므로 느리지만 WSGI는 그렇지 않으므로 좋습니다.

### Fast CGI

- 성능 향상을 위해 CGI 확장

- 요청이 들어오면 떠서 실행후 종료되는 CGI 방식과는 다르게 응용 프로그램 서버용으로 설계

출처

[https://blog.wishket.com/php-간단-개념과-장단점에-대해서-3/](https://blog.wishket.com/php-%EA%B0%84%EB%8B%A8-%EA%B0%9C%EB%85%90%EA%B3%BC-%EC%9E%A5%EB%8B%A8%EC%A0%90%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-3/)

[https://brownbears.tistory.com/350](https://brownbears.tistory.com/350)

[https://blog.naver.com/kh2un/222556042455](https://blog.naver.com/kh2un/222556042455)


</div>
</details>



<details>
<summary>김지훈 회고록 요약</summary>
<div markdown="1">
# 인프라

---

## php

### php란?

- 서버측 스크립트 언어다.
- 주로 html을 프로그래밍적으로 작성하는데 사용되는 언어다.
- 정적 페이지를 동적 페이지로 구현하는데 쓰인다.
- html처럼 소스를 외부로 내놓지 않기 때문에 보안에 유리하다.

### php의 기본 문법

1. PHP 권장 스타일 : **<?php ... ?>**
2. HTML 스크립트 스타일 : **<script language = "php"> ... </script>**
3. SGML 스타일 : **<? ... ?>**
4. ASP 스타일 : **<% ... %>**

### Nginx에서 php 설치 방법

내가 참고한 페이지, 다른 방법으론 잘 설치가 안돼 nginx로 설치하는 방법을 택했다.

[How to Install PHP 8 on CentOS/RHEL 8/7 Linux](https://www.tecmint.com/install-php-8-on-centos/)

### php - fpm

[NGINX + PHP-FPM 연동](https://yumserv.tistory.com/146)

## 👨🏻‍💻 리눅스 프로그래밍 실습

---

- Hi There을 출력하는 Php
    
    ```bash
    **<!DOCTYPE html>
    <html>
    <meta charset="UTF-8">
    <body>
    <?php
    echo "Hi, There";
    ?>
    </body>
    </html>**
    ```
    
- php for 문을 이용한 인자 받아와서 출력
    
    ```bash
    **<meta charset="UTF-8">
    <?php
    echo "$argv[1] 단\n";
    
    $i = 1;
    $result = 0;
    
            while($i<=10) //i가 10보다 작거나 같을 때 반복합니다
            {
                    echo "$argv[1] X $i = ";
                    echo $argv[1]*$i;
                    echo "\n";      //i를 출력합니다.
                    $i++;
            }
    ?>**
    ```
    
    - php의 인자값을 받아오기엔 argv 배열의 값에 참조해 값을 들고 와야한다.
    - argv의 0번째 인덱스는 해당 프로그램의 파일명이 들어가있다.
- batch 파일, 쉘스크립트를 이용한 자동
    
    ```bash
    **<?php
    for ( $dan = 1; $dan <= 100; $dan++ ) {
            $cmd = "php dan.php {$dan} > {$dan}dan.html";
            shell_exec($cmd);
    }
    ?>**
    ```
    
    - shell_exec는 아래의 링크에 정의되어있다.
        
        [shell_exec](https://www.php.net/manual/en/function.shell-exec.php)
        
        **shell_exec — Execute command via shell and return the complete output as a string**
        
        즉 shell을 통해 실행된 명령들과 그 성공한 리턴값을 문자열로 나타내주는 명령어다.
        
        **결과값(100개 생성)**
        
        ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%A7%80%ED%9B%88%EB%8B%981.png)
        
- batch 파일 개선
    
    ```bash
    **<?php
    $limit = intval($argv[1]);
    
    for ( $dan = 1; $dan <= $limit; $dan++ ) {
            $cmd = "php dan.php {$dan} > {$dan}dan.html";
            shell_exec($cmd);
    }
    ?>**
    ```
    
    - php파일의 인자값, 실행할 건수를 인자로 받아온다.
    - intval은 argv의 값을 정수화, 하지만 똑똑한 php는 자동으로 해줘서 상관 없다.
        
        **결과값(150생성)**
        
        ```bash
        **php batch_dan.php 150**
        ```
        
       ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%A7%80%ED%9B%88%EB%8B%982.png)
    

# 스프링부트

## Paging

---

### Pagable

JPA 안에 내장되어있는 라이브러리이다.

### Page 객체의 내부 메서드

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%A7%80%ED%9B%88%EB%8B%983.png)

## JPA

---

### InterestedKeyword 문제

목표

- 테스트케이스 t10 이 실행되면
- interest_keyword 테이블에 row 3개 생성
- 회원과 키워드는 서로 M:N 관계이므로
- 중간테이블이 필요하다.

제약사항

- InterestKeyword 클래스에 필드를 추가할 수 없다.

### ManytoMany
https://ict-nroo.tistory.com/127

</div>
</details>


<details>
<summary>김상훈 회고록 요약</summary>
<div markdown="1">
# linux-날짜,Cookies

## 현재날짜출력

- **PHP파일에서 나라 설정**
    
    ```php
    <?php
    date_default_timezone_set('Asia/Seoul');
    ?>
    <?=date("Y-m-d H:i:s")?>
    ```
    
- **/etc/php.ini 에서 나라 설정**
    - ESC→/timezone입력⇒단어검색
    - date.timezone=Asia/Seoul로 수정
    
    ![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%83%81%ED%9B%88%EB%8B%981.png)
    

## 클라이언트 기술(script)로 쿠키값변경

- **Script로 쿠키값 변경 메소드 모음**
    
    ```jsx
    <script>
    // 주어진 이름의 쿠키를 반환하는데,
    // 조건에 맞는 쿠키가 없다면 undefined를 반환합니다.
    function getCookie(name) {
      let matches = document.cookie.match(
        new RegExp(
          "(?:^|; )" +
            name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, "\\$1") +
            "=([^;]*)"
        )
      );
      return matches ? decodeURIComponent(matches[1]) : undefined;
    }
    
    function setCookie(name, value, options = {}) {
      options = {
        path: "/", // 경로 지정
        ...options // 아규먼트로 옵션을 넘겨줬을경우 전개연산자로 추가 갱신
      };
    
      if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString(); // 생 Date 객체라면 형식에 맞게 인코딩
      }
    
      let updatedCookie =
        encodeURIComponent(name) + "=" + encodeURIComponent(value);
    
      for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
          // 밸류가 없다면
          updatedCookie += "=" + optionValue;
        }
      }
    
      document.cookie = updatedCookie; // 새로 갱신
    }
    
    function deleteCookie(name) {
      // 해당 쿠키 요소만 삭제
      setCookie(name, "", {
        "max-age": -1
      });
    }
    
    function deleteAllCookies() {
      var cookies = document.cookie.split(";");
      for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        deleteCookie(name.trim());
      }
      console.log("all cookies deleted !");
    }
    </script>
    ```
    
- **Script로 쿠키값 한개씩증가**
    
    ```jsx
    //메소드 추가후
    ...
    //쿠키값이 없으면
    if (getCookie("no") === undefined) {
    	//쿠키생성(key,value)
      setCookie("no", 0);
    }
    //숫자로 변경해서 쿠키값받아온후
    const noFromCookie = parseInt(getCookie("no"));
    //증가
    const newNo = noFromCookie + 1;
    //쿠키에 값저장
    setCookie("no", newNo);
    
    // 화면에 표시
    document.querySelector("#no").innerHTML = newNo;
    ```
    

## 서버 기술(PHP)로 쿠키값변경

- **서버로 어떤정보가 왔는지 확인**
    
    ```php
    <?php// request.php
    print_r($_SERVER);
    ?>
    ```
    
- **php로 쿠키생성방법**
    
    ```php
    #set
    setcookie("쿠키이름", "값", 만료시간 / 초 단위, "경로");
    
    #쿠키출력
    echo $_COOKIE["쿠키이름"];
    
    #쿠키삭제
    setcookie("쿠키이름", "", 0, "경로");
    ```
    
- **PHP로 쿠키값 1씩증가**
    
    ```php
    // cookie_no_up_by_server.php
    //isset=>있는지 없는지 확인
    <?php
    if ( isset($_COOKIE['no']) === false ) {
            $_COOKIE['no'] = 0;
    }
    
    $newNo = $_COOKIE['no'] + 1;
    
    setcookie("no", $newNo);
    //==$_COOKIE['no'] = $newNo;
    ?>
    
    <h1>서버에서 렌더링한 쿠키값</h1>
    <div><?=$_COOKIE['no']?></div>
    ```
    
- **Script와PHP를 이용한 값 1씩 각각 증가시키기**
    
    <aside>
    💡 **PHP코드를 어디다 두든 먼저 수행하게 된다!**
    
    </aside>
    
    ```php
    <?php
    // cookie_no_up.php
    if ( isset($_COOKIE['no']) === false ) {
            $_COOKIE['no'] = 0;
    }
    
    $newNo = $_COOKIE['no'] + 1;
    
    setcookie("no", $newNo);
    $_COOKIE['no'] = $newNo;
    ?>
    //--------------------------------------------------------------------
    <h1>서버에 받은 쿠키 no의 값</h1>
    <div><?=$_COOKIE['no']?></div>
    <hr>
    <h1>클라에서 변형시킨 쿠키 no의 값</h1>
    <div id="no"></div>
    //--------------------------------------------------------------------
    <style>
    div {
      font-size: 10rem;
    }
    </style>
    //--------------------------------------------------------------------
    <script>
    // 주어진 이름의 쿠키를 반환하는데,
    // 조건에 맞는 쿠키가 없다면 undefined를 반환합니다.
    function getCookie(name) {
      let matches = document.cookie.match(
        new RegExp(
          "(?:^|; )" +
            name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, "\\$1") +
            "=([^;]*)"
        )
      );
      return matches ? decodeURIComponent(matches[1]) : undefined;
    }
    
    function setCookie(name, value, options = {}) {
      options = {
        path: "/", // 경로 지정
        ...options // 아규먼트로 옵션을 넘겨줬을경우 전개연산자로 추가 갱신
      };
    
      if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString(); // 생 Date 객체라면 형식에 맞게 인코딩
      }
    
      let updatedCookie =
        encodeURIComponent(name) + "=" + encodeURIComponent(value);
    
      for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
          // 밸류가 없다면
          updatedCookie += "=" + optionValue;
        }
      }
    
      document.cookie = updatedCookie; // 새로 갱신
    }
    
    if (getCookie("no") === undefined) {
      setCookie("no", 0);
    }
    const noFromCookie = parseInt(getCookie("no"));
    const newNo = noFromCookie + 1;
    setCookie("no", newNo);
    
    // 화면에 표시
    document.querySelector("#no").innerHTML = newNo;
    </script>
    ```
    

## 쿠키만으로 로그인 구현

- **로그인 폼페이지**
    - **로그인 되어있으면 로그인이름 출력**
    - **로그인 안되어있으면 로그인폼출력**
    
    ```php
    // login_form.php
    <?php
    $loginedUsername = isset($_COOKIE['loginedUsername']) ? $_COOKIE['loginedUsername'] : null;
    $isLogined = $loginedUsername !== null;
    ?>
    
    <meta charset="UTF-8">
    
    <?php if ( $isLogined == false ) { ?>
    <h1>로그인 해주세요.</h1>
    
    <form method="POST" action="login_check.php" target="_blank">
        <input type="text" name="username" placeholder="username" />
        <input type="password" name="password" placeholder="password" />
        <input type="submit" value="로그인" />
    </form>
    <?php } ?>
    
    <?php if ( $isLogined ) { ?>
    <h1><?=$loginedUsername?>님 환영입니다.</h1>
    <?php } ?>
    ```
    
- 로그인 체크페이지
    
    ```php
    <?php
    // login_check.php
    //전송된 데이터가 일치하지않으면
    if ( $_POST['username'] != 'user1' || $_POST['password'] != '1234' ) {
            echo '로그인 실패';
            exit;
    }
    //일치하면
    //username에 값넣어주기
    setcookie('loginedUsername', $_POST['username']);
    echo '로그인 성공';
    ?>
    ```
    

# SpringBoot-QueryDsl

- **addFollower  대신 follow함수로변경**
    
    ```java
    //SiteUser.class
    public void follow(SiteUser u2) {
    			//대상의 followers에 추가
           u2.getFollowers().add(this);
        }
    ```
    
- **본인을 follow 할수없도록 설정**
    
    ```java
    //SiteUser.class
    public void follow(SiteUser following) {
            if (this == following) return;
            if (following == null) return;
            if (this.getId() == following.getId()) return;
            following.getFollowers().add(this);
        }
    ```
    
- **@Transactional**
    - **SQL에 있는 Transaction과 같은 기능으로 여러 처리를 하나의 단위로 처리하고 싶을 때 사용한다.**
    - ex)
    
    ```java
    @Transactional
    //start transaction
    public void save(){
    	//user를 삭제하고
    	userRepository.deleteAll();
    	//팀을 삭제 하는 transaction
    	teamRepository.delete()
    }
    //rollback
    //commit
    ```
    
    - **transcational은 쓰기지연과 아무상관없다!**


</div>
</details>


<details>
<summary>도성구 회고록 요약</summary>
<div markdown="1">
## **디렉토리 권한에 관한 권한 소유주 수정**

### 1) 문제 발생과 해결과정

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%981.png)

php batch_dan.php 을 돌리려는데 허가 거부가 나는 것을 확인했다. 오류라고 보기는 어렵고 권한 문제일 것 같았다.

찾아보니까 `**chmod +x filename`** 으로  스크립트에 대한 실행권한을 부여하는 것이 있었는데. 이때는 

`chmod +x **batch_dan.php`** 으로 실행권한을 주어서 

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%982.png)

다음과 같은 상태를 만들었는데. 

**허가 거부가 계속 발생하였다.**

어떤 문제가 있어서 계속 그러는지 강사님께 여쭈어 보았다.

`ls -alh` 로 현재 디렉토리와 상위디렉토리 그리고 현재 디렉토리의 파일들의 `권한`과 `소유주`를 확인하였다.

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%983.png)

소유주를 보니 `root`인 것을 확인 할 수 있었는데 ~~(왜 못 본 것인가..)~~

> **현재 접속중인 계정은 `lldj`이므로 소유주가 `root`인 디렉토리에 대한 권한이 없어서 발생한 문제였던 것이다.**
> 

**해결** (강사님께서 말씀해주신 코드) : 

`**$sudo chown lldj:lldj /web -R**` 

위 코드는 슈퍼권한으로 웹 아래의 하위 디렉토리를 다 lldj의 소유로 바꾸는 명령이다.

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%984.png)

하여서 위와 같이 lldj가 소유주가 된 것을 확인했고,

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%985.png)

정상적으로 `dan.html` 파일들이 생성된 것을 볼 수 있다.

---

### 2) chmod와 chown

*정의 -*

**chown: 파일이나 디렉토리의 소유주를 바꾸는 명령어.**

`**chown user:group /file/path**` 형식으로 명령을 실행하면 된다.

만약, 소유주를 root 그룹의 root 로 바꾸려면, sudo chown root:root dir1(파일명) 이렇게 사용하면 됨.

**chmod:** **파일이나 디렉토리의 권한을 바꾸는 명령어이다.**

*chomd-*

 **첫번째, 일단 `ls -alh`에 대해 알아보자면**

`ls` : 현재 디렉토리에 있는 file들 조회 

`-al` : 숨김파일을 포함하여 목록 자세히 출력

`h` : 파일크기를 해석하기 편하게 출력함 K, M, G ~단위로 

예시 `**-alh` h : 파일 크기를 1024씩 나누어서 K로 표기한 것을 볼 수 있다.**

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%986.png)

**두번째, ls -alh 한 파일, 디렉토리들의 권한을 보는 법** ⭐

예시 - `drwxrwxr-x  2 user user_group 4096 Sep 17 06:39 dir1`

첫번째 블럭이 **권한 문자열(Permission string)**,

두번째 블럭이 해당 디렉토리 내부의 파일과 디렉토리 갯수,

세번째 블럭이 소유주,

네번째가 소유주가 속한 그룹,

다섯번째가 크기,

6,7,8번째는 마지막으로 파일/디렉토리에 접근한 시각,

아홉번째가 파일/디렉토리의 이름을 의미한다.

**세번째, 권한 해석**

그럼 해석을 해보자면 

`drwxrwxr-x` 는 dir1에 대한 Permission이다.

여기서 d는 **디렉토리(d), 일반파일(-), 특수파일은(s)**를 뜻한다.

따라서 d가 붙어있으므로 디렉토리를 의미한다.

그 뒤에 3글자 dir1의 경우 **rwx** 3글자는 각각 아래처럼 의미한다.

`**r->read  /  w->write  /  x->execute`** 

**해당 소유주의 읽기 쓰기 실행 권한을 의미한다.**

Tip . `**-**`로 표기 된 것은 그 권한이 없다는 것을 의미함 

그 다음의 **3글자는 소유주그룹의 권한**

마지막 **3글자는 위 두 그룹에 속하지 않은 other** 을 의미한다.

다음 아래는 rwx의 8진수 형식과 2진법 파일모드에 대해 매칭한 표이다.

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%987.png)

**네번째, chmod로 적용하기**

일단, chmod의 문법은 

**`$chmod [user / group / other 권한] [filename]`**

대체로 숫자로 쓰지만 문자로도 가능하다고 한다.

<aside>
✅ 그럼 dir1 = `drwxrwxr-x` 
**`sudo chmod 775 dir1`** 명령으로 권한을 부여받은 것을 알 수 있고 이는 
나(소유주)는 **모든 권한** 
소유주그룹도 **모든 권한**
other는 **읽기와 실행 권한**만 받은 것으로 해석할 수 있다.

</aside>

---

 *chown-*

명령어를 사용하기 위해서는 슈퍼 유저 권한이 필요(보안 특성상 슈퍼유저만 사용)  : `**sudo**` 

일단, chown의 문법은 
**`$ chown [owner name]:[group name] [filename or directory] -R`**

**첫번째, 예시**

chown a test : test 파일에 대한 소유자를 a로 바꾼다.

chown :a test : test 파일에 대한 그룹명을 a로 바꾼다.

chown a: test : test 파일에 대한 소유자 및 그룹명을 a로 바꾼다.

chown a:b test : test 파일에 소유자는 a, 그룹명은 b로 바꾼다.

**두번째, 옵션**

![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%988.png)

**세번째, chown으로 소유주 변경하기.**

<aside>
✅ **`sudo chown lldj:lldj /web -R`

슈퍼유저로 `/web` 하위 디렉토리 모든 파일과 모든 디렉토리를 
소유자 lldj, 그룹명 lldj로 변경했다.**

</aside>

---

참고 자료 : 

1) `chmod +x filename` 명령으로 가능해지는 것 :

`**./batch_dan.php` << 가능해진다.**

참고 2번 가능 -

[php소스코드만 따로 콘솔창에서 실행](https://action713.tistory.com/entry/php%EC%86%8C%EC%8A%A4%EC%BD%94%EB%93%9C%EB%A7%8C-%EB%94%B0%EB%A1%9C-%EC%BD%98%EC%86%94%EC%B0%BD%EC%97%90%EC%84%9C-%EC%8B%A4%ED%96%89)

2) 이외의 ls 옵션들

[[Linux] 리눅스 ls 명령어 사용법 & 옵션 정리 (디렉토리 목록 확인)](https://coding-factory.tistory.com/748)

3) chmod chown
(https://har1bo-tk.tistory.com/5)
(https://didimdol20.tistory.com/42)
(https://www.notion.so/0c7490e90ef64b439fda6fad67bb70b5#a45abf4765b445db9d350705e9bff934)
</div>
</details>

## 회고 과정에서 나왔던 질문 (최소 200자 이상)
서로 피드백한 댓글을 첨부합니다.
### 이소영님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%86%8C%EC%98%81%EB%8B%98%ED%9A%8C%EA%B3%A0%EB%8C%93%EA%B8%80.PNG)

### 서진수님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%A7%84%EC%88%98%EB%8B%98%ED%9A%8C%EA%B3%A0%EB%8C%93%EA%B8%80.PNG)

### 김지훈님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%A7%80%ED%9B%88%EB%8B%98%ED%9A%8C%EA%B3%A0%EB%8C%93%EA%B8%80.PNG)

### 김상훈님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%83%81%ED%9B%88%EB%8B%98%ED%9A%8C%EA%B3%A0%EB%8C%93%EA%B8%80.PNG)

### 도성구님 회고록 댓글
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%EC%84%B1%EA%B5%AC%EB%8B%98%ED%9A%8C%EA%B3%A0%EB%8C%93%EA%B8%80.PNG)


## 회고 인증샷 & 팀 자랑

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
![image](https://github.com/likelion-backendschool/recipia/blob/feature-%2355/Retrospec_Log/image/week4/%ED%8C%80%EC%9E%90%EB%9E%91%20%ED%9A%8C%EA%B3%A0.png)
   

- 필수) 자랑 멘트는 **‘팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지’**에 대해 간단하게 작성해 주시면 됩니다 😊
팀 프로젝트를 진행하면서 서로의 에러에 대해 활발히 질문하고 답했습니다. 이를 진행하면서 자칫 수업에서 놓치는 부분이 있을 때도 있는데
서로의 회고록을 보며 복기하기 편했습니다^^
   
