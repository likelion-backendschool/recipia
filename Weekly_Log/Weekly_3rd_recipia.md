# 3주차 팀 위클리

날짜: 2022년 8월 22일

## 팀 구성원, 개인 별 역할

---

**🙋🏻‍♂️김지훈(팀장)**

- 유저관련 Controller,Service,Repository 생성
- 회원가입 페이지 구현

**🙋🏻‍♂️도성구(팀원)**

- 마이페이지 구현

**🙋🏻‍♂️김상훈(팀원)**

- 게시물관련 Controller,Service,Repository 생성
- 게시물 리스트 페이지 구현

**🙋🏻‍♀️이소영(팀원)**

- 게시물 상세 페이지 구현

**🙋🏻‍♂️서진수(팀원)**

- 메인 페이지 구현

## 팀 내부 회의 진행 회차 및 일자

---

12**회차 (22.08.16) 디스코드 음성채널 진행 (전원 참석)**

13**회차 (22.08.17) 디스코드 음성채널 진행 (전원 참석)**

14**회차 (22.08.18) 디스코드 음성채널 진행 (전원 참석)**

15**회차 (22.08.19) 디스코드 음성채널 진행 (전원 참석)**


## 현재까지 개발 과정 요약 (최소 500자 이상)

---

## 초기목표

- 테일윈드를 기반으로 UI 만들어오기
- Controller, Service, Repository 구현
- ERD 기반으로 JPA를 통해 DB 연동

### 실제 어떤 일을 하고 있는지

- 김지훈
    - 회원가입 페이지 UI 구현완료
    - UserController, Service, Repository 구현
    - ERD 기반으로 JPA 연동 구현

- 이소영
    - 프로젝트 에러로 구현하지 못함.

- 도성구
    - 마이페이지 구현
    - MyPageController 까지 구현
    - DB는 아직 하지 못했습니다.

- 김상훈
    - 게시물 Controller,Servcie,Repository 구현
    - 게시물 리스트 페이지 구현
    
- 서진수
    - 첫페이지 메인화면 구현
    - 검색창과 이미지는 만들었지만 수업에서 배우지 않아서 제대로 구현X
    
### 계획과 실제 결과의 차이는 왜 발생되었는가

- **성구**
    - tailwind로 UI설계는 했으나, 설계 방향이 UI를 완벽하게 설계하지 않고 page를 간단하게 로드하고 하는 것이 좋았을 것 같습니다.
    이번주 코드리뷰 회의시간과 다른 사람들의 commit 된 내용을 확인 하여서 방향성을 확립하였습니다.
    따라서 mypage 구현을 제대로 진행할 수 있을 것 같습니다.
- **소영**
    - 파일에서 빌드와 런이 되지 않아 진행하지 못하였습니다.
    - 이번주 회의를 통해 파일을 잘 열어서 진행할 수 있게 되었습니다.
    
### 지속, 개선 혹은 포기

- **상훈**
    - 설계당시에는 이미지를 섬네일 만 생각했었는데 구현을 하면서 게시물에는 
    여러개의 이미지가 필요⇒ 이미지 컬럼을 1:M관계인 이미지 테이블 생성하는것으로 변경
    - 이미지 타입을 blob으로 구현하려했지만  URL을 넣어주고 이미지자료는 AWS S3 서버에 저장하는것이 효율적이고 편리해서 변경하려함
    - like는 예약어이기 때문에 likes로 변경

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

- 코딩하는 방식을 어떻게 해야할지 모르겠다.
    - 초기구축 설계 방법
        1. JPA dependency 추가
        2. application.properties 안에 DB랑 JPA 설정 해주기
        3. Entity +EntityRepository 생성 및 테스트코드 짜보기
        4. Controller 와 연결되는 html 간단한 글자 넣어서 연결 및 생성(MVC테스트)
        5. model 속성에  데이터 담아서 전송해서 thymeleaf 로 출력 하기 
        6.  타임리프 레이아웃 짜기

## 개발 결과물 공유

---
- **회원 가입 페이지**

![회원 가입 페이지](https://github.com/likelion-backendschool/recipia/blob/feature-%2328/Weekly_Log/images/week3_signUpPage.png)

- **게시물 리스트**

![게시물 리스트 페이지](https://github.com/likelion-backendschool/recipia/blob/feature-%2328/Weekly_Log/images/week3_postList.png)

- **마이 페이지**

![회원 가입 페이지](https://github.com/likelion-backendschool/recipia/blob/feature-%2328/Weekly_Log/images/week3_myPage.png)

