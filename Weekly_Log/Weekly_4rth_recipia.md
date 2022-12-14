# 4주차 팀 위클리

날짜: 2022년 8월 29일

## 팀 구성원, 개인 별 역할

---

**🙋🏻‍♂️김지훈(팀장)**

- 로그인 로그아웃 페이지 및 기능 구현

**🙋🏻‍♂️도성구(팀원)**

- 마이페이지 하위 페이지 UI 설계
- 마이페이지 구현 DB연결

**🙋🏻‍♂️김상훈(팀원)**

- 게시물 상세 페이지

**🙋🏻‍♀️이소영(팀원)**

- 해시태그 페이지 구현
- 해시태그 crud 구현

**🙋🏻‍♂️서진수(팀원)**

- 검색창 구현

## 팀 내부 회의 진행 회차 및 일자

---

16**회차 (22.08.23) 디스코드 음성채널 진행 (전원 참석)**

17**회차 (22.08.24) 디스코드 음성채널 진행 (전원 참석)**

18**회차 (22.08.25) 디스코드 음성채널 진행 (전원 참석)**

19**회차 (22.08.26) 디스코드 음성채널 진행 (전원 참석)**


## 현재까지 개발 과정 요약 (최소 500자 이상)

---

## 초기목표

1. 김지훈: 로그인 로그아웃 페이지 및 기능 구현
2. 김상훈: 게시물 상세 페이지
3. 도성구 : 마이페이지 하위 페이지 UI 설계, 마이페이지 구현 DB연결
4. 이소영 : 해시태그 페이지 구현 및 해시태그 crud 구현
5. 서진수: 검색창 구현

### 실제 어떤 일을 하고 있는지

- 김지훈
    - 로그인 기능 구현중 패스워드 인식 오류 개선

- 이소영
    - 해시태그 페이지 구현 완료
    - 해시태그 crud 구현 중

- 도성구
    - 마이페이지에서 필요한 값을 찾는 메서드 구현 중

- 김상훈
    - 게시물 작성 페이지 구현 완료
    - 게시물 작성 폼과 작성기능 완료
    - 상세페이지 구현 중
    
- 서진수
    - 검색창 구현 중
    
### 계획과 실제 결과의 차이는 왜 발생되었는가

- **성구**
    - 이사… 죄송합니다! 인터넷도 고쳤으니까 열심히 달려보겠습니다.
   
- **소영**
    - 해시태그 관련 소스를 찾고 구현하는데 시간이 걸려 구현하지 못했습니다.

- **지훈**
    - 주말에 일정이 있어서 많이 못했습니다....ㅎ

- **진수**
    - pull 관련 이슈로 프로젝트가 잘 뜨지 않아 못했습니다.

- **상훈**
    - 작성과 리스트를 먼저 개발하면 상세페이지 작성이 더쉬울것같아서 작성페이지를 먼저 개발하게되었습니다.
    
### 지속, 개선 혹은 포기

- 개선: csrf 처리를 Header에다가 직접 값을 넣어줘서 전송해주자.
- 회고글은 목요일까지 작성하고 금요일날까지 피드백 시간을 가지기로 함

- 김상훈:작성폼디자인을 수정,로그인시 작성페이지 기능변경, 나중에는 이미지를 CLOUD 환경에 저장해야 할것같습니다

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---
1. 파일이 잘 열리지 않습니다.
  - 해결법: 파일을 클론한 뒤 Recipia로 열지 말고 그 안의 RecipiaProject를 연 뒤 필요시 sdk 설정을 해주면 됩니다.
2. 깃 브랜치에 이상이 있습니다.
  - 해결법: checkout을 통한 브랜치 이동이 안될 때는 clone을 다시 한 뒤 브랜치를 옮기고 작업한 파일을 옮깁니다.


## 개발 결과물 공유

--

- **마이 페이지**

![마이 페이지](https://github.com/likelion-backendschool/recipia/blob/feature-%2346/Weekly_Log/images/4%EC%A3%BC%EC%B0%A8%EB%8F%84%EC%84%B1%EA%B5%AC%EB%8B%98%ED%8E%98%EC%9D%B4%EC%A7%80.png)

