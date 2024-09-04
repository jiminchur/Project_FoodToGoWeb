# 🍱 Project_FoodToGoWeb 🍱
주문 배달 플랫폼을 스프링 부트 기반의 API Gateway, Auth, Monolitic 3개의 서버로 개발한 팀프로젝트 입니다.
![스크린샷](/IMG/FoodToGoWeb.png)

## 🗓️ 개발 기간
* 2024.08.22 ~ 2024.09.03

## 📚 목차
[1. 프로젝트 개요](#1-프로젝트-개요)

[2. 팀원 역할 분담](#2-팀원-역할-분담)

[3. 요구사항 명세서](#3-요구사항-명세서)

[4. API 명세서](#4-api-명세서)

[5. 테이블 설계서](#5-테이블-설계서)

[6. ERD](#6-erd)

[7. 기술 스택](#7-기술-스택)

[8. 인프라 설계도](#8-인프라-설계도)

[9. CICD 파이프라인](#9-cicd-파이프라인)

[10. Git Branch 및 Git Commit 전략](#10-git-branch-및-git-commit-전략)

## ✅ 1. 프로젝트 개요
* **주제:** 배달 및 포장 음식 주문 관리 플랫폼 개발
* **목표:** 광화문 근처에서 운영될 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼 개발

## 👬🏻 2. 팀원 역할 분담

### 🙌🏻 회의 기록

* [🗓️ [회의] 일자별로 회의 내용를 기록한 내용입니다. #1](https://github.com/jiminchur/Project_FoodToGoWeb/issues/1)
* [📘 Wiki - OurRules](https://github.com/jiminchur/Project_FoodToGoWeb/wiki/OurRules)

### 🫡 팀원 소개
|팀장(백엔드)|팀원(백엔드)|팀원(백엔드)|
|-----|-----|-----|
|<img src="https://avatars.githubusercontent.com/u/145955453?v=4" alt="지민철" width="100">|<img src="https://avatars.githubusercontent.com/u/81797927?v=4" alt="박현도" width="100">|<img src="https://avatars.githubusercontent.com/u/161826579?v=4" alt="한정원" width="100">|
|[지민철](https://github.com/jiminchur)|[박현도](https://github.com/atto08)|[한정원](https://github.com/dev-wonny)|

### 👊🏻 역할 분담
<details>
    <summary><h2>🙎🏻‍♂️ 지민철</h2></summary>

<details>
    <summary>🏠 가게</summary>

* [🏠 [가게 앱] 성능 개선 #115](https://github.com/jiminchur/Project_FoodToGoWeb/issues/115)

* [🏠 [가게 앱] 가게 운영 상태 변경 #12](https://github.com/jiminchur/Project_FoodToGoWeb/issues/12)

* [🏠 [가게 앱] 가게 검색 #11](https://github.com/jiminchur/Project_FoodToGoWeb/issues/11)

* [🏠 [가게 앱] 가게 삭제 #10](https://github.com/jiminchur/Project_FoodToGoWeb/issues/10)

* [🏠 [가게 앱] 가게 정보 전체 수정 #9](https://github.com/jiminchur/Project_FoodToGoWeb/issues/9)

* [🏠 [가게 앱] 가게 전체 조회 #8](https://github.com/jiminchur/Project_FoodToGoWeb/issues/8)

* [🏠 [가게 앱] 가게 단건 조회 #7](https://github.com/jiminchur/Project_FoodToGoWeb/issues/7)

* [🏠 [가게 앱] 가게 등록 #6](https://github.com/jiminchur/Project_FoodToGoWeb/issues/6)

</details>    
    
<details>
    <summary>📚 가게 - 카테고리</summary>

* [📚 [가게 - 카테고리] 성능 개선 #122](https://github.com/jiminchur/Project_FoodToGoWeb/issues/122)

* [📚 [가게 - 카테고리] 카테고리 삭제 #56](https://github.com/jiminchur/Project_FoodToGoWeb/issues/56)

* [📚 [가게 - 카테고리] 카테고리 수정 #55](https://github.com/jiminchur/Project_FoodToGoWeb/issues/55)

* [📚 [가게 - 카테고리] 카테고리 목록 조회 #54](https://github.com/jiminchur/Project_FoodToGoWeb/issues/54)

* [📚 [가게 - 카테고리] 카테고리 등록 #53](https://github.com/jiminchur/Project_FoodToGoWeb/issues/53)

</details>

<details>
    <summary>🍱 음식</summary>

* [🍱 [음식 앱] 음식 품절 처리 #91](https://github.com/jiminchur/Project_FoodToGoWeb/issues/91)

* [🍱 [음식 앱] 음식 삭제 #77](https://github.com/jiminchur/Project_FoodToGoWeb/issues/77)

* [🍱 [음식 앱] 음식 숨김 처리 #76](https://github.com/jiminchur/Project_FoodToGoWeb/issues/76)

* [🍱 [음식 앱] 음식 상세 정보 수정 #75](https://github.com/jiminchur/Project_FoodToGoWeb/issues/75)

* [🍱 [음식 앱] 음식 단건 조회 (운영진) #74](https://github.com/jiminchur/Project_FoodToGoWeb/issues/74)

* [🍱 [음식 앱] 음식 전체 조회 (운영진) #73](https://github.com/jiminchur/Project_FoodToGoWeb/issues/73)

* [🍱 [음식 앱] 가게에 속한 음식 조건 검색 #72](https://github.com/jiminchur/Project_FoodToGoWeb/issues/72)

* [🍱 [음식 앱] 가게에 속한 음식 전체 조회 #70](https://github.com/jiminchur/Project_FoodToGoWeb/issues/70)

* [🍱 [음식 앱] 가게에 속한 음식 등록 #69](https://github.com/jiminchur/Project_FoodToGoWeb/issues/69)

</details>

<details>
    <summary>🤖 AI</summary>

* [[🤖 AI] ai 기록조회 #148](https://github.com/jiminchur/Project_FoodToGoWeb/issues/148)

* [[🤖 AI] ai 요청 및 데이터베이스에 저장 #132](https://github.com/jiminchur/Project_FoodToGoWeb/issues/132)

</details>

<details>
    <summary>🛠️ CICD</summary>

* [🛠️ [CICD] Docker hub을 이용한 CICD 구축 #167](https://github.com/jiminchur/Project_FoodToGoWeb/issues/167)

* [🛠️ [CICD] Docker hub을 이용한 CICD 구축시 Redis와의 연결 오류 #168](https://github.com/jiminchur/Project_FoodToGoWeb/issues/168)

* [🛠️ [CICD] SCP를 이용한 CICD 구축 #169](https://github.com/jiminchur/Project_FoodToGoWeb/issues/169)

</details>

</details>

<details>
    <summary><h2>🧑🏻‍💻 박현도</h2></summary>

<details>
    <summary>결제</summary>

* [[Payment] 초기 구현 #146](https://github.com/jiminchur/Project_FoodToGoWeb/issues/146)

</details>

<details>
    <summary>배송지</summary>

* [[Address] 회원 배송지 삭제 #62](https://github.com/jiminchur/Project_FoodToGoWeb/issues/62)

* [[Address] 회원 배송지 수정 #61](https://github.com/jiminchur/Project_FoodToGoWeb/issues/61)

* [[Address] 회원 배송지 목록 조회 #60](https://github.com/jiminchur/Project_FoodToGoWeb/issues/60)

* [[Address] 회원 배송지 상세 조회 #59](https://github.com/jiminchur/Project_FoodToGoWeb/issues/59)

* [[Address] 회원 배송지 등록 #58](https://github.com/jiminchur/Project_FoodToGoWeb/issues/58)

</details>

<details>
    <summary>주문</summary>

* [[Order] totalOrderPrice @Enumerated 설정 제거 [Payment] PaymentRequestDto amount 제거 #155](https://github.com/jiminchur/Project_FoodToGoWeb/issues/155)

* [[Order] 성능 개선 #126](https://github.com/jiminchur/Project_FoodToGoWeb/issues/126)

* [[Order] 주문 내역 삭제 #99](https://github.com/jiminchur/Project_FoodToGoWeb/issues/99)

* [[Order] 주문 내역 목록 조회 - [고객, 가게, 운영진] #96](https://github.com/jiminchur/Project_FoodToGoWeb/issues/96)

* [[Order] 주문 단건 조회 #95](https://github.com/jiminchur/Project_FoodToGoWeb/issues/95)

* [[Order] 주문 취소 요청 #94](https://github.com/jiminchur/Project_FoodToGoWeb/issues/94)

* [[Order] 주문 상태 변경 #93](https://github.com/jiminchur/Project_FoodToGoWeb/issues/93)

* [[Order] 주문 등록 #92](https://github.com/jiminchur/Project_FoodToGoWeb/issues/92)


</details>

<details>
    <summary>유저</summary>

* [[Users] 성능 개선 #125](https://github.com/jiminchur/Project_FoodToGoWeb/issues/125)

* [[Users] 회원 전체 조회 - 관리자 #18](https://github.com/jiminchur/Project_FoodToGoWeb/issues/18)

* [[Users] 회원 검색 - 관리자 #17](https://github.com/jiminchur/Project_FoodToGoWeb/issues/17)

* [[Users] 회원 삭제 #16](https://github.com/jiminchur/Project_FoodToGoWeb/issues/16)

* [[Users] 회원 정보 수정 #15](https://github.com/jiminchur/Project_FoodToGoWeb/issues/15)

* [[Users] 회원 정보 조회 #14](https://github.com/jiminchur/Project_FoodToGoWeb/issues/14)

* [[Users] 회원 전체 조회 #13](https://github.com/jiminchur/Project_FoodToGoWeb/issues/13)

</details>

</details>


<details>
    <summary><h2>👩🏻‍💻 한정원</h2></summary>

<details>
    <summary>Gateway</summary>

* [[gateway] 초기 세팅 #19](https://github.com/jiminchur/Project_FoodToGoWeb/issues/19)

* [[gateway] 필터 개선 #131](https://github.com/jiminchur/Project_FoodToGoWeb/issues/131)

* [[gateway] 인증, 모노리티 서버 호출 연동 #145](https://github.com/jiminchur/Project_FoodToGoWeb/issues/145)


</details>

<details>
    <summary>Auth</summary>

* [[인증 서버] 초기 세팅 #20](https://github.com/jiminchur/Project_FoodToGoWeb/issues/20)

* [[인증 서버] jwt - 발급 #21](https://github.com/jiminchur/Project_FoodToGoWeb/issues/21)

* [[인증 서버] jwt - 토큰 검증 #22](https://github.com/jiminchur/Project_FoodToGoWeb/issues/22)

* [[인증 서버] jwt - 재발급 #23](https://github.com/jiminchur/Project_FoodToGoWeb/issues/23)

* [[인증 서버] user - 등록 #24](https://github.com/jiminchur/Project_FoodToGoWeb/issues/24)

* [[인증 서버] user - 수정 #25](https://github.com/jiminchur/Project_FoodToGoWeb/issues/25)

* [[인증 서버] user - 조회 #26](https://github.com/jiminchur/Project_FoodToGoWeb/issues/26)

* [[인증 서버] user - 삭제 #27](https://github.com/jiminchur/Project_FoodToGoWeb/issues/27)

* [[인증 서버] user - 로그인 #28](https://github.com/jiminchur/Project_FoodToGoWeb/issues/28)

* [[인증 서버] user - 로그아웃 #29](https://github.com/jiminchur/Project_FoodToGoWeb/issues/29)

* [[인증 서버] redis - 캐시 조회, 갱신 #30](https://github.com/jiminchur/Project_FoodToGoWeb/issues/30)

* [[인증 서버] redis - 캐시 업데이트 #31](https://github.com/jiminchur/Project_FoodToGoWeb/issues/31)

* [[인증 서버] redis - 캐시 삭제 #32](https://github.com/jiminchur/Project_FoodToGoWeb/issues/32)

* [[인증 서버] 계정 - 잠금, 해제 #33](https://github.com/jiminchur/Project_FoodToGoWeb/issues/33)

* [[인증 서버] 비밀번호 - 변경 #34](https://github.com/jiminchur/Project_FoodToGoWeb/issues/34)

* [[인증 서버] 비밀번호 - 재설정 요청 #35](https://github.com/jiminchur/Project_FoodToGoWeb/issues/35)

* [[인증 서버] 비밀번호 - 재설정 요청 #35](https://github.com/jiminchur/Project_FoodToGoWeb/issues/35)

* [[인증 서버] gateway서버에서 인증서버 기능 호출 시 맞지 않는 부분 수정 #128](https://github.com/jiminchur/Project_FoodToGoWeb/issues/128)

</details>

<details>
    <summary>유저</summary>

* [[유저 앱] 레디스 권한 체크 추가 #159](https://github.com/jiminchur/Project_FoodToGoWeb/issues/159)

</details>

</details>

## 📕 3. 요구사항 명세서
* Wiki에 따로 요구사항 명세서를 기록함
* [📘 Wiki - 요구사항 명세서 링크](https://github.com/jiminchur/Project_FoodToGoWeb/wiki/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EB%AA%85%EC%84%B8%EC%84%9C)

## 📙 4. API 명세서
* API 명세서에 설계에 따라 개발을 하였고, 첫 설계와 다른점이 있다고 생각하면 [🛠️ [수정사항] API 명세서 수정사항 #90](https://github.com/jiminchur/Project_FoodToGoWeb/issues/90)에 기록함
* 아래 Notion 링크를 통해서 확인 가능함
* [Ohteam - API 명세서 - Notion](https://mire-plastic-701.notion.site/333fba57922143e39638d69ab7b9890b?v=19dba6a601034c9fa0da7acbf82b059e&pvs=4)

## 📄 5. 테이블 설계서
* 요구사항 명세서의 **데이터 베이스 설계**의 규칙에 따라 작성함
* 아래 Notion 링크를 통해서 확인 가능함
* [Ohteam - 테이블 설계서 - Notion](https://mire-plastic-701.notion.site/7f5926ee36a84122ab90c34951d68f39?pvs=4)

## 📋 6. ERD
![ERD](./IMG/erd.png)
* 테이블 설계서에 따라 만든 ERD

## 🛠️ 7. 기술 스택
* Backend
    * Spring Boot 3.3.3
    * Spring Cloud Gateway
    * Gradle
* API Test
    * PostMan
* Database
    * Postgresql
    * Redis
* Infra
    * AWS EC2 t2.medium
    * Docker
* CI/CD
    * GitAction
* Communication
    * Slack
* Version
    * Git
    * Github

## 🛠️ 8. 인프라 설계도
![인프라설계도](./IMG/sys-arch.png)

## 🔨 9. CICD 파이프라인
![cicd](./IMG/cicd.png)

* [🛠️ [CICD] Docker hub을 이용한 CICD 구축 #167](https://github.com/jiminchur/Project_FoodToGoWeb/issues/167)

* [🛠️ [CICD] Docker hub을 이용한 CICD 구축시 Redis와의 연결 오류 #168](https://github.com/jiminchur/Project_FoodToGoWeb/issues/168)

* [🛠️ [CICD] SCP를 이용한 CICD 구축 #169](https://github.com/jiminchur/Project_FoodToGoWeb/issues/169)

## 📀 10. Git Branch 및 Git Commit 전략
* Git Branch 전략
    * 자세한 내용은 [📘 Wiki - GitBranch 전략 링크](https://github.com/jiminchur/Project_FoodToGoWeb/wiki/Git-Branch-%EC%A0%84%EB%9E%B5) 에서 확인 가능함
![gitbranch](./IMG/gitcommit.png)
* Git Commit 전략
    * 자세한 내용은 [📘 Wiki - GitCommit 전략 링크](https://github.com/jiminchur/Project_FoodToGoWeb/wiki/Commit-%EC%A0%84%EB%9E%B5) 에서 확인 가능함

|머리말|내용|
|-----|-----|
|Init|시작|
|Fix|버그 수정|
|Add|새로운 기능 추가|
|Update|기존 기능 업데이트|
|Remove|불필요한 코드 제거|
|Refactor|코드 리팩토링|
|Improve|성능 개선|
|Document|문서화|
|Style|스타일 변경 (예: 코드 포맷팅)|
|Test|테스트 추가 또는 수정|
