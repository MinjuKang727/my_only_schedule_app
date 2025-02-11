# 🛞 나만의 일정 관리 앱 서버 만들기


### Ver.1 
> Java & JDBC로 개발  
> 개발 기간 : 2024.08.01 ~ 08.08 *(1주일)*

#### API  

  ![ver1_API](https://github.com/MinjuKang727/my_only_schedule_app/blob/main/image/ver1_api.png)  

<br>

#### ERD  

  ![ver1_ERD](https://github.com/MinjuKang727/my_only_schedule_app/blob/main/image/ver1_erd.png)  
  
<br>

---
### Ver.2
> Spring Boot & JPA로 개발   
> 개발 기간 : 2024.08.16 ~ 08.30 *(2주일)*

### 개발 환경
- Spring Boot
- JPA
- MySQL
- IntelliJ
- Postman

### 공통 조건
- 모든 테이블은 고유 식별자(ID)를 가집니다.
- `3 Layer Architecture`에 따라 각 Layer의 목적에 맞게 개발합니다.
- CRUD 필수 기능은 모두 데이터베이스 연결 및 `JPA`를 사용해서 개발합니다.
- `JDBC`와 `Spring Security`는 사용하지 않습니다.
- 인증/인가 절차는 `JWT`를 활용하여 개발합니다.
- JPA 연관관계는 `양방향`으로 구현합니다.

### 필수 구현 단계
*JPA를 활용한 CRUD 심화*


> **0단계**  
> 과제의 첫걸음
#### 1. API  

   ![ver2_API](https://github.com/MinjuKang727/my_only_schedule_app/blob/main/image/ver2_api.png)  

<br>


#### 2. ERD  

   ![ver2_ERD](https://github.com/MinjuKang727/my_only_schedule_app/blob/main/image/ver2_erd.png)

<br>

#### 3. SQL 작성하기
> sql 파일 참고

> **1단계**  
> 기능 : 일정 CRU
> 학습 목표 : JPA 입문
1. 일정을 저장, 단건 조회, 수정할 수 있습니다.
2. 일정은 `작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` 필드를 갖고 있습니다.

> **2단계**
> 기능 : 댓글 CRUD
> 학습 목표 : 연관관계 기본
1. 일정에 댓글을 달 수 있습니다.
    1. 댓글과 일정은 연관 관계를 가집니다.
2. 댓글을 저장, 단건 조회, 전체 조회, 수정, 삭제할 수 있습니다.
3. 댓글은 `댓글 내용`, `작성일`, `수정일`, `작성 유저명` 필드를 갖고 있습니다.

> **3단계**
> 기능 : 일정 페이징 조회
> 학습 목표 : 페이징/정렬
1. 일정을 Spring Data JPA의 `Pageable`과 `Page` 인터페이스를 활용하여 페이지네이션을 구현해 주세요.
    1. `페이지 번호`와 `페이지 크기`를 쿼리 파라미터로 전달하여 요청하는 항목을 나타냅니다.
    2. `할일 제목`, `할일 내용`, `댓글 개수`, `일정 작성일`, `일정 수정일`, `일정 작성 유저명` 필드를 조회합니다.
    3. 디폴트 `페이지 크기`는 10으로 적용합니다.
2. 일정의 `수정일`을 기준으로 내림차순 정렬합니다.

> **4단계**
> 기능 : 일정 삭제
> 학습 목표 : 영속성 전이
1. 일정을 삭제할 때 일정의 댓글도 함께 삭제됩니다.
    1. JPA의 영속성 전이 기능을 활용합니다.

> **5단계**
> 기능 : 유저 CRUD
> 학습 목표 : 연관관계 심화
1. 유저를 저장, 단건 조회, 전체 조회, 삭제할 수 있습니다.
    1. 유저는 `유저명`, `이메일`, `작성일`, `수정일` 필드를 갖고 있습니다.
2. 일정은 이제 `작성 유저명` 필드 대신 `유저 고유 식별자` 필드를 가집니다.
3. 일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있습니다.
    1. 유저와 일정은 N:M 관계입니다.(`@ManyToMany` 사용 금지!)

> **6단계**
> 기능 : 일정 조회 개선
> 학습 목표 : 지연 로딩
1. 일정 단건 조회 시 담당 유저들의 `고유 식별자`, `유저명`, `이메일`이 추가로 포함됩니다.
2. 일정 전체 조회 시 담당 유저 정보가 포함되지 않습니다.
    1. JPA의 지연 로딩 기능을 활용합니다.

---

### 튜터님 피드백

```
김기용 튜터 ・ --
민주님 수고많으셨습니다!
과제하시면서 에러 로그를 남기시면서 작업하신 모습이 참 인상깊습니다. RestFul API 잘 설계해주시고 전반적으로 과제가 훌륭하다고 생각합니다. 아쉬운 부분은 아래 항목별로 정리해보았습니다. 학습에 참고하셔요 :)


[1. Dto 사용]
 Response 객체에 불필요한 속성이 꼭 필요한지 고민해보시고 불필요한 속성은 포함되지 않도록 설계를 재검토하는 것이 좋겠습니다. Dto는 하나로 고정할 필요가 없습니다. API 별 응답에 맞게 별도의 Dto를 만들어 사용하는 방법도 고려해보세요.


[2. 무분별한 Setter 사용 @Setter]
엔티티의 상태 변경은 *생성자나  *메서드를 통해 관리하는 것이 바랍직합니다. 생성자와 메서드로 객체의 속성을 관리하게 되면 객체의 무결성과 유지보수성을 높이는 데 도움이 됩니다.

로봇 버튼 비유 예시:
로봇에 많은 버튼이 있고 각 버튼마다 기능이 정의되어있다고 가정합니사. 왼쪽눈 깜빡, 오른쪽 눈 깜빡, 오른쪽으로 돌기 등 각 버튼이 눈, 머리, 입, 팔, 다리 등 따로 움직인다고 생각해보세요. 많은 버튼을 관리하면서 객체를 제어하는 것은 굉장히 어려운 일입니다. 

자바 객체도 마찬가지입니다. 세터를 통해 마음대로 조작할 수 있지만 프로젝트 규모가 커지면서 오작동할 가능성이 점점 증가하게 됩니다. 메서드로 하나의 기능을 정의해 놓고 객체를 관리하는게 유지보수 관점에서 올바른 설계입니다.


[3. 인텔리제이 기능활용]
초록색 줄은 코드가 잘 작동하더라도 개선이 필요할 수 있는 부분을 알려주는 힌트입니다. 해당 기능을 통해 변수 오류등을 관리할 수 있습니다.
public ManagerResponseDto save(ManagerRequestDto **resquestDto) {


[4. Null 의 위험성]
Null 을 직접 반환하거나 그대로 사용하는 것은 매우 위험합니다. 모던 자바에서는 Optinal 객체를 적극적으로 사용해주시면 좋습니다.

// 기존 코드
public ManagerResponseDto save(ManagerRequestDto resquestDto) {
    ...
    saveManager = this.managerRepository.save(manager);
    if (saveManager != null) {
        return this.managerRepository.getManager(saveManager.getManager_id());
    }
    return null;
}

Optional 객체를 활용한 메서드 선언 방법:
public Optional<ManagerDto> save(Manager manager) { ... }

Optional 객체를 활용한 메서드 호출방법
ManagerDto managerdto = managerService.save(manager) 
   .orElseThrow(() -> new 예외("예외메시지"));


[5. 클래스, 메서드 레벨 주석 처리 필요]
클래스와 메서드 레벨에서는  /** */ 형태로 사용하시는 습관을 들여야합니다. 클래스에 정의되어 있는 메서드가 무엇을 뜻하는지 표시하는것은 현업에서 매우 중요합니다. 필요한 경우 작성자, 연락처 등 회사 규칙에 따라 주석이 추가될 수 있습니다. 아래는 참고할 모범사례 예시입니다.
/**
 * This method calculates the sum of two integers.
 * 
 * @param a the first integer
 * @param b the second integer
 * @return the sum of a and b
 */
public int sum(int a, int b) {
    return a + b;
}


[6. 예외처리]
예외가 발생/처리 부분이 없어 아쉬운 부분이 있습니다. 예외 발생과 처리는 웹 구성에 필수 입니다. 커스텀 예외 객체를 만드는 방법과, 공통적으로 예외를 처리할 수 있는 @ControllerAdvice 메커니즘을 학습해보시면 많은 도움이 될 것입니다.

// 커스텀 예외 선언:
public class 커스텀예외 extends RuntimeException {
    public 커스텀예외(Long id) {
        super("예외메시지: " + id);
    }
}

// 커스텀 예외 사용 예시:
public Schedule findSchedulById(Long id) {
    return scheduleRepository.findById(id)
            .orElseThrow(() -> new 커스텀예외(id));
}

// 공통예외처리
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(커스텀예외.class)
    public ResponseEntity<String> handleException(DateParseException ex) {
        return new ResponseEntity<>("에러메시지: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 다른 예외 처리 메서드를 추가할 수 있습니다.
}
```
