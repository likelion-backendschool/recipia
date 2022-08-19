# 2주차 회고록

날짜: 2022년 8월 19일

## **팀 구성원**

- [팀장] 김지훈, 도성구, 김상훈, 이소영 서진수

---

## **회고 내용 요약 (최소 500자 이상)**

팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂

### Thymeleaf

### thymeleaf

<aside>
📢 **th:each
th:each사용시⇒”item:${items}”
th:text⇒${}안의 값을 Value로 설정**

```html

<tr th:each="question : ${questionList}">
  <td th:text="${question.subject}"></td>
  <td th:text="${question.createDate}"></td>
</tr>
```

</aside>

<aside>
📢 **th:href
th:href사용시 ⇒@{} 필수**

```html
<a th:href="@{/question/list}"></a>
```

**th:href+${}사용시⇒| |로 문자열을 연결해줘야한다**

```html
<a th:href="@{|/question/detail/${question.id}|}"></a>
```

</aside>

### **th:replace 가 경로를 못찾는 오류 해결방법(thymeleaf-layout 사용시)**

```
Caused by: org.attoparser.ParseException: Error resolving template [common/header : : headerFrag], template might not exist or might not be accessible by any of the configured Template Resolvers (template: "common/default_layout" - line 4, col 11)
	at org.attoparser.MarkupParser.parseDocument(MarkupParser.java:393)
	at org.attoparser.MarkupParser.parse(MarkupParser.java:257)
	at org.thymeleaf.templateparser.markup.AbstractMarkupTemplateParser.parse(AbstractMarkupTemplateParser.java:230)
	... 48 more
Caused by: org.thymeleaf.exceptions.TemplateInputException: Error resolving template [common/header : : headerFrag], template might not exist or might not be accessible by any of the configured Template Resolvers (template: "common/default_layout" - line 4, col 11)
```

```java
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
//나는 아래 ::사이의 공백때문에 경로를 못찾았음 =>오류 찾기 너무빡셈
<th:block th:replace= "common/header : : headerFrag"></th:block>
//<th:block th:replace= "common/header :: headerFrag"></th:block> 같이 ::사이공백을 없애야함
<th:block layout:fragment="content"></th:block>
<th:block th:replace= "common/footer::footerFrag"></th:block>
</html>
```

### navbar설정을 위한 CSS

```html
<!--범위를 늘려주기위한 section과 header,main,footer -->
<section>
  <header>
    헤더
  </header>

  <main>
    Lorem ipsum dolor sit amet consectetur adipisicing elit. Magnam id nobis ducimus? Necessitatibus doloremque tempora aspernatur asperiores, provident eaque eos, commodi voluptatum ipsum distinctio enim rerum ipsa, rem laudantium maxime.
    Corporis, porro! Odio quos accusantium, dolorum perferendis architecto sint nam ad quas non fuga possimus officia sequi doloribus, voluptates impedit suscipit eaque dolor! Dignissimos dicta adipisci deserunt aut sed tempora?
    Quidem sed, vero nemo vel voluptate illo eius perspiciatis velit ipsum. Odit, cumque. Ipsam magni earum repellendus dolore, rerum fuga. Dignissimos magni voluptatum veniam earum officia vitae quibusdam quisquam sint.
    Temporibus ab debitis, cumque odit nostrum perspiciatis atque nobis quibusdam explicabo saepe maxime dolor omnis. Magnam, culpa veritatis. Inventore rerum reiciendis eum. Facere doloremque distinctio officiis quis quo beatae odio.
    Omnis autem suscipit iure ea, perferendis quam architecto nam voluptatum, nostrum officia atque facere exercitationem aut reiciendis, facilis impedit debitis! Iure, velit! Odit aliquid debitis dolores sit. Provident, maxime eveniet.
    Quo, ipsa veritatis necessitatibus dolorum expedita est molestias similique aperiam culpa doloremque perspiciatis excepturi ad quod ex dolor quam distinctio eligendi consequatur eveniet consequuntur iure minus. Amet repellat praesentium tenetur.
    Illo architecto recusandae, error, expedita nihil corporis perspiciatis dignissimos ad praesentium alias explicabo non, tempora laudantium consequuntur maxime maiores sed iusto incidunt quibusdam esse repellat distinctio veritatis. Sint, qui dolores?
    Culpa repudiandae aliquam veniam, obcaecati in optio soluta nam odio, non modi magni consequatur quia accusantium numquam, deserunt et ex voluptatum consectetur hic qui. Architecto blanditiis voluptas sit esse veritatis?
    Voluptatem illo vitae velit? Eum unde, illum delectus fugiat culpa corrupti eius, quasi voluptates sapiente animi eveniet maiores? Iste sunt vero perferendis nam, mollitia eveniet corrupti quod et repellendus adipisci.
    Vel repellat nostrum debitis sapiente quidem, ipsam culpa ipsa amet autem consequuntur itaque voluptatum! At ea mollitia voluptatem enim. Ipsum adipisci iusto explicabo quo id quos ipsam reiciendis totam ullam.
  </main>

  <footer>
    푸터
  </footer>
</section>
```

```css
/*글자가 밖으로 나가는 것을 방지*/
* {
  box-sizing: border-box;
}
/*body margin 속성을 제거*/
body {
  margin: 0;
}

/*글자를 가운데 정렬하고 글자가 잘보이게 여러설정*/
header,
footer {
  padding: 10px;
  background-color: black;
  color: white;
  text-align: center;
  font-weight: bold;
}

footer {
}
/*min-height 를 100vh 로 주어서 화면전체를 사용 and 글자가 화면 밖으로 나가는것을 방지효과 */
/*여기서 flex 를 쓴이유는 main에 flex-grow를 줘서 빈공간을 채우기 위해서!*/
section {
  border: 10px solid green;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
/*빈공간 채우기*/
main {
  flex-grow: 1;
}
```

### 자주사용하는 thymeleaf

> **1) 분기문 속성**

분기문 속성은 다음과 같이 사용한다.
> 

```java
th:if="${question != null}"
// question 객체가 null이 아닌 경우 엘리먼트 표시
```

> **2) 반복문 속성**

반복문은 반복횟수만큼 해당 엘리먼트를 반복하여 표시한다. 
반복문 속성은 **자바의 for each 문과 유사하다.**
> 

```java
th:each="question : ${questionList}"

th:each="question, loop : ${questionList}"
//둘다 사용가능 
```

<aside>
✅ **loop 객체를 이용하여 루프내 다음과 같은 속성을 사용 할 수 있음**

- loop.index - 반복 순서, 0부터 1씩 증가
- loop.size - 반복 객체의 요소 갯수 (예: questionList의 요소 갯수)
- loop.first - 루프의 첫번째 순서인 경우 true
- loop.last - 루프의 마지막 순서인 경우 true
- loop.odd - 루프의 홀수번째 순서인 경우 true
- loop.even - 루프의 짝수번째 순서인 경우 true
- loop.current - 현재 대입된 객체 (예: 위의 경우 question과 동일)
- loop.count - 반복 순서, 1부터 1씩 증가
</aside>

> 3**) 텍스트 속성**
`**th:text=값**` 속성은 해당 엘리먼트의 텍스트로 "값"을 출력한다.
> 

```java
th:text="${question.subject}"
```

텍스트는 `**th:text**`속성 대신에 다음처럼 대괄호를 사용하여 값을 직접 출력할수 있다.

```java
<tr th:each="question : ${questionList}">
    <td>[[${question.subject}]]</td>
    <td>[[${question.createDate}]]</td>
</tr>
```

- thymeleaf 관련 설명을 상훈님과 성구님의 회고에서 발췌해보았습니다.

### Validation

폼으로 값을 받아오는 경우가 웹사이트에선 거의 필수적으로 존재하는데 이럴 때마다 해당 값이 유효한지를 검사해줘야한다. 아래의 직접👀 그린 그림을 그런 수많은 경우 중에 글을 작성할 때 동작 과정을 간단하게 구조도처럼 그려봤다.

### Validation 동작 구조와 유용한 어노테이션

![스크린샷 2022-08-17 오후 3.56.26.png](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2022-08-17_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_3.56.26.png)

1. 사용자는 Get 메서드를 통해 글 작성 폼의 페이지를 요청한다.
    1. 서버는 글 작성 폼을 사용자에게 보여주며 응답한다.
2. 글 작성 폼을 받은 사용자는 변경될 내용을 폼에게 Post 메서드로 요청한다.
    1. **이때 사용자가 유효한 값(올바른 값)을 입력했는지 검증한다.**
3. 만일 검증시 에러가 발생하게 되면 Redirect로 폼으로 돌아가게 반환해준다.

이때 검증시 때 유용하게 사용할 수 있는게 **BindingResult** 클래스이다.

### BindingResult

BindingResult를 만일 쓰지 않았다면 아래와 같은 코드를 작성해야한다. Question 작성 폼에서 Subject와 Content를 받아오면 둘 다 null값은 아닌지, 해당 문자 길이가 얼마인지를 일일이 if문을 덕지덕지 써가며 검사를 해야할 것이다. 이는 펀하고 쿨하고 섹시하게 코딩하는 방식이 아니다.(개인적인 사견)

```java
		**@PostMapping("/create")
    public String questionCreate(Model model, QuestionFrom questionFrom) {
        if (questionFrom.getSubject() == null || questionFrom.getSubject().trim().length() == 0) {
            model.addAttribute("errorMsg", "제목 좀...");
            return "question_form";
        }

        if (questionFrom.getContent() == null || questionFrom.getContent().trim().length() == 0) {
            model.addAttribute("errorMsg", "내용 좀...");
            return "question_form";
        }

        questionService.create(questionFrom.getSubject(), questionFrom.getContent());
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동**
```

BindingResult는 해당 에러들을 다 담은 바구니라고 생각하면 된다. 아래의 어노테이션들을 달아주게 되면 BindingResult에서 에러들을 판단하고 우리는 BindingResult에 에러가 있는지만 확인해주면 검증을 할 수 있다. 

```java
		**@PostMapping("/question/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        // 만약 폼에서 받아온 데이터가 오류가 있다면?
        if (bindingResult.hasErrors()){
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }**
```

<aside>
💡 Tip‼️
BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 2개의 매개변수의 위치가 정확하지 않다면 @Valid만 적용이 되어 입력값 검증 실패 시 400 오류가 발생한다.

</aside>

### Validation의 유용한 어노테이션

![출처: [https://wikidocs.net/161873](https://wikidocs.net/161873)](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2022-08-17_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%258C%25E1%2585%25A5%25E1%2586%25AB_10.29.47.png)

출처: [https://wikidocs.net/161873](https://wikidocs.net/161873)

- Validation 정리를 지훈님이 정성스럽게 해주셔서 가져와 보았습니다.

### 스프링 시큐리티

- Spring 기반의 **애플리케이션의 보안을 담당**하는 스프링 하위 프레임 워크
- 인증과 권한에 대한 부분을 **Filter 흐름에 따라 처리함.**
- 보안과 관련해서 체계적으로 많은 옵션을 제공해 주기 때문에 개발자 입장에서는 **일일이 보안관련 로직을 작성하지 않아도 되기 때문에 편리하다.**

### 인증&인가&권한 차이점

1. **인증(Authentication)** : 보호된 리소스에 접근한 대상에 대해 이 유저가 누구인지, 애플리케이션의 작업을 수행해도 되는 주체인지 확인하는 과정(ex. Form 기반 Login)
2. **인가(Authorize)** : 해당 리소스에 대해 접근 가능한 권한을 가지고 있는지 확인하는 과정 (After Authentication, 인증 이후)
3. **권한** : 어떠한 리소스에 대한 접근 제한, 모든 리소스는 접근 제어 권한이 걸려있다. 즉, 인가 과정에서해당 리소스에 대한 제한된 최소한의 권한을 가졌는지 확인한다.

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled.png)

여기 가족이 휴가를 떠나 집에 홀로 남겨진 반려 동물을 보살피기 위해 누군가가 잠긴 문으로 다가가고 있습니다. 이 사람에게 필요한 것은 다음과 같습니다.

- 열쇠 형태의 **인증**이 필요합니다. 자격 증명을 정확하게 입력하는 사용자에 한해서 액세스가 허용되는 것처럼 현관 자물쇠에 맞는 열쇠를 가진 사람에게만 접근이 허용됩니다.

- 출입 허가에 해당하는 **인가 및 권한 부여가** 필요합니다. 일단 집 안으로 들어가면 주방에 가서 반려 동물 사료가 보관된 찻장을 열 수 있는 권한 인증을 받게 됩니다. 하지만 침실에 들어가서 낮잠을 잘 수 있는 권한은 없습니다.

위의 예에서 인증과 권한 인증은 함께 작동합니다. 반려 동물 관리인은 집에 들어갈 수 있는 권한(인증)이 있으며, 일단 내부로 입장하면 특정 영역에 접근할 수 있습니다(권한 인증).

**인증관련 아키텍쳐**

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%201.png)

Spring Security는 **세션-쿠키 방식으로 인증한다.**

1. 유저가 로그인을 시도함(http request)
2. AuthenticationFilter부터 userDB까지 들어감
3. DB에 있는 유저라면 UserDetails로 꺼내서 유저의 session생성
4. spring securigy의 인메모리 세션저장소인 SecurityContextHolder에 저장
5. 유저에게 session ID와 함께 응답을 줌
6. 이후 요청에서는 요청 쿠키에서 JSESSIONID를 확인해 유효하면 권한을 줌

- 스프링 시큐리티에 관한 내용은 진수님과 소영님께서 잘 정리해주셔서 가져와 보았습니다.

---

## **회고 과정에서 나왔던 질문 (최소 200자 이상)**

서로 피드백한 댓글을 첨부합니다.

### **이소영님 회고록**

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%202.png)

### **서진수님 회고록**

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%203.png)

### **김지훈님 회고록**

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%204.png)

### **김상훈님 회고록**

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%205.png)

### **도성구님 회고록**

![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%206.png)

## **회고 인증샷 & 팀 자랑**

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- 이번주는 다같이 회의한 사진으로 대체합니다!!
    
    ![Untitled](2%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%20%E1%84%92%E1%85%AC%E1%84%80%E1%85%A9%E1%84%85%E1%85%A9%E1%86%A8%2097e89aad7cba4702ae55324385700b37/Untitled%207.png)
    
- 필수) 자랑 멘트는 **‘팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지’**에 대해 간단하게 작성해 주시면 됩니다 😊
    
    매일매일 스크럼을 진행하면서 오늘 진행했던 어려운 상황을 나누고 있습니다. 팀간 회고에서도 모르는 부분에 대해서 주 1회이상 꼭 음성채널에서 질문하는 시간을 갖고 있습니다🧐 서로 원활한 피드백으로 수업을 진행하면서 더 좋은 정보들을 공유하고 있습니다ㅎㅎ
    
    또한, 회고록에 변경점이나 개선사항을 댓글로 남겨서 진행하고 있는데 이로 인해 회고의 질을 높일 수 있어서 좋았습니다.