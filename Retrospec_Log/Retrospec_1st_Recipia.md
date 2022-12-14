## 팀 구성원

- [팀장] 김지훈, 도성구, 김상훈, 이소영 서진수

## 회고 내용 요약 (최소 500자 이상)

팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂

### **Git**

브랜치는 실제 저장소에 대한 참조이다.

- 100만개를 만들어도 상관이 없다. 무리가 없다.
    - 왜냐하면 똑같은 객체를 가르키기 때문에 메모리를 낭비하지 않는다.

깃에 대한 브랜치를 자바 객체에 비유하니 더 잘와닿습니다.

![자바의 객체 생성에 비유](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ca5ec4d1-c572-43bb-88d3-023396afda78/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-08_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_9.49.58.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220812%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220812T131056Z&X-Amz-Expires=86400&X-Amz-Signature=5deebd254331c6e6036182654800d115e6f5cba3620e12fed8fb4f3d40009fc8&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA%25202022-08-08%2520%25E1%2584%258B%25E1%2585%25A9%25E1%2584%258C%25E1%2585%25A5%25E1%2586%25AB%25209.49.58.png%22&x-id=GetObject)

### **쿠키와 세션**

|  | 쿠키(Cookie) | 세션(Session) |
| --- | --- | --- |
| 저장 위치 | 클라이언트(=접속자 PC) | 웹 서버 |
| 저장 형식 | text | Object |
| 만료 시점 | 쿠키 저장시 설정
(브라우저가 종료되도,만료시점이 지나지않으면 자동 삭제되지 않음) | 브라우저 종료시 삭제
(기간 지정 가능) |
| 사용하는 자원(리소스) | 클라이언트 리소스 | 웹서버 리소스 |
| 용량 제한 | 총300개
하나의 도메인당 20개
하나의 쿠키당 4KB | 서버가 허용하는한 용량제한 X |
| 속도 | 세션보다 빠름 | 쿠키보다느림 |
| 보안 | 세션보다 안좋음 | 쿠키보다 좋음 |
- 다들 쿠키와 세션을 자세히 정리하면서 개념을 정리했습니다. 상훈님의 정리가 제일 깔끔해 회고록에 올렸습니다👍

### HTTP 상태코드

|  HTTP 에러코드 | 에러 메시지  |
| --- | --- |
| 100 |  Continue  |
| 101  |  Switching Protocols |
| 200 |  OK, 에러 없이 전송 성공  |
| 202 | Accepted, 서버가 클라이언트의 명령을 받음 |
| 203 | Non Content, 클라이언트 요구를 처리했으나 전송할 데이터가 없다. |
| 404  | Not Found, 문서를 찾을 수 없음 
이 에러는 클라이언트가 요청한 문서를 찾지 못한 경우에 발생함. URL을 다시 잘 보고 주소가 올바로 입력되었는지를 확인함.  |
| 500 | Internal Server Error, 서버 내부 오류
이 에러는 웹 서버가 요청사항을 수행할 수 없을 경우에 발생함 |
| 501 | Not Implemented, 적용 안 됨
이 에러는 웹 서버가 요청을 수행하는데 필요한 기능을 지원하지 않는 경우 발생 |
| 502  | Bad gateway, 게이트웨이 상태 
이 에러는 게이트웨이 상태가 나쁘거나 서버의 과부하 상태일 때 발생한다. |
- SpringBoot까지 진행해오면서 만났을 수많은 상태코드들을 성구님이 주요 상태코드들만 알짜배기로 표로 정리해주셨습니다.

## 회고 과정에서 나왔던 질문 (최소 200자 이상)

서로 피드백한 댓글을 첨부합니다.

### 이소영님 회고록

![스크린샷 2022-08-12 오후 9.50.40.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/75d521d7-89a5-4b5a-9ca2-4f0c686b51c4/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_9.50.40.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220812%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220812T131200Z&X-Amz-Expires=86400&X-Amz-Signature=67ac25a79a3414b424f9fbfdc12dfe546dbc7abf37e9c166c3e32bfc5bf999ed&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA%25202022-08-12%2520%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE%25209.50.40.png%22&x-id=GetObject)

### 서진수님 회고록

![스크린샷 2022-08-12 오후 9.51.37.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/37730c1b-f055-4c32-92ba-bd0ee68d3ee6/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_9.51.37.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220812%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220812T131228Z&X-Amz-Expires=86400&X-Amz-Signature=1674c765bf4b46a801eb5033d110262736733b6d242e840c08d1e5573f0954fa&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA%25202022-08-12%2520%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE%25209.51.37.png%22&x-id=GetObject)

### 김지훈님 회고록

![스크린샷 2022-08-12 오후 9.52.00.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0c98c9fd-0501-4c27-ad03-312effd63f7f/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_9.52.00.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220812%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220812T131249Z&X-Amz-Expires=86400&X-Amz-Signature=e72ed9b6f1dedc3126e8b4aa8274aed2386d39de54bec8a54898757d0d83e6c3&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA%25202022-08-12%2520%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE%25209.52.00.png%22&x-id=GetObject)

### 김상훈님 회고록

![스크린샷 2022-08-12 오후 9.53.05.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4ff504ea-19e1-4fad-bdb5-fa03a7382914/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_9.53.05.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220812%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220812T131309Z&X-Amz-Expires=86400&X-Amz-Signature=726d5432b3fda09c750dbc74a4fc3fa30403188d34a348ce0e9644ca5f8c602f&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA%25202022-08-12%2520%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE%25209.53.05.png%22&x-id=GetObject)

### 도성구님 회고록

![스크린샷 2022-08-12 오후 9.52.50.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4ef68750-dabf-4e52-b0cc-d2411f29b43f/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_9.52.50.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220812%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220812T131324Z&X-Amz-Expires=86400&X-Amz-Signature=bd65adefa53b4d3a356ed527da1aa14ec61440e64212914dc3a65c1af31a87df&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA%25202022-08-12%2520%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE%25209.52.50.png%22&x-id=GetObject)

## 회고 인증샷 & 팀 자랑
<img width="996" alt="image1" src="https://user-images.githubusercontent.com/40134318/184821788-a2b3bf51-97d7-4966-9509-504b3ec67637.png">

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
    
    
- 필수) 자랑 멘트는 **‘팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지’**에 대해 간단하게 작성해 주시면 됩니다 😊
    
    매일매일 스크럼을 진행하면서 오늘 진행했던 어려운 상황을 나누고 있습니다. 팀간 회고에서도 모르는 부분에 대해서 주 1회이상 꼭 음성채널에서 질문하는 시간을 갖고 있습니다🧐 서로 원활한 피드백으로 수업을 진행하면서 더 좋은 정보들을 공유하고 있습니다ㅎㅎ
