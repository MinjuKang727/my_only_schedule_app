# 🛞 나만의 일정 관리 앱 서버 만들기


### Ver.1 
> Java & JDBC로 개발  
> 개발 기간 : 2024.08.01 ~ 08.08 *(1주일)*

- **API**  
  ![ver1_API](.\image\ver1_api.png)  


- **ERD**  
  ![ver1_ERD](.\image\ver1_erd.png)  
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
1. **API**
   ![ver2_API](.\image\ver2_api.png)  


2. **ERD**
   ![ver2_ERD](.\image\ver2_erd.png)


3. SQL 작성하기

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