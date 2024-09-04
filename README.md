# 🍱 Project_FoodToGoWeb 🍱
![스크린샷](/IMG/FoodToGoWeb.png)
* 주문 배달 플랫폼을 스프링 부트 기반의 API Gateway, Auth, Monolitic 3개의 서버로 개발한 팀프로젝트 입니다.

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

### 🫡 팀원 소개
|팀장(백엔드)|팀원(백엔드)|팀원(백엔드)|
|-----|-----|-----|
|<img src="https://avatars.githubusercontent.com/u/145955453?v=4" alt="지민철" width="100">|<img src="https://avatars.githubusercontent.com/u/81797927?v=4" alt="박현도" width="100">|<img src="https://avatars.githubusercontent.com/u/161826579?v=4" alt="한정원" width="100">|
|[지민철](https://github.com/jiminchur)|[박현도](https://github.com/atto08)|[한정원](https://github.com/dev-wonny)|

### 👊🏻 역할 분담
<details>
    <summary>🙎🏻‍♂️ 지민철</summary>

<details>
    <summary>🏠 가게</summary>

[🏠 [가게 앱] 성능 개선 #115](https://github.com/jiminchur/Project_FoodToGoWeb/issues/115)
[🏠 [가게 앱] 가게 운영 상태 변경 #12](https://github.com/jiminchur/Project_FoodToGoWeb/issues/12)
[🏠 [가게 앱] 가게 검색 #11](https://github.com/jiminchur/Project_FoodToGoWeb/issues/11)
[🏠 [가게 앱] 가게 삭제 #10](https://github.com/jiminchur/Project_FoodToGoWeb/issues/10)
[🏠 [가게 앱] 가게 정보 전체 수정 #9](https://github.com/jiminchur/Project_FoodToGoWeb/issues/9)
[🏠 [가게 앱] 가게 전체 조회 #8](https://github.com/jiminchur/Project_FoodToGoWeb/issues/8)
[🏠 [가게 앱] 가게 단건 조회 #7](https://github.com/jiminchur/Project_FoodToGoWeb/issues/7)
[🏠 [가게 앱] 가게 등록 #6](https://github.com/jiminchur/Project_FoodToGoWeb/issues/6)

</details>    
    
<details>
    <summary>📚 가게 - 카테고리</summary>

[📚 [가게 - 카테고리] 성능 개선 #122](https://github.com/jiminchur/Project_FoodToGoWeb/issues/122)
[📚 [가게 - 카테고리] 카테고리 삭제 #56](https://github.com/jiminchur/Project_FoodToGoWeb/issues/56)
[📚 [가게 - 카테고리] 카테고리 수정 #55](https://github.com/jiminchur/Project_FoodToGoWeb/issues/55)
[📚 [가게 - 카테고리] 카테고리 목록 조회 #54](https://github.com/jiminchur/Project_FoodToGoWeb/issues/54)
[📚 [가게 - 카테고리] 카테고리 등록 #53](https://github.com/jiminchur/Project_FoodToGoWeb/issues/53)

</details>

<details>
    <summary>🍱 음식</summary>

[🍱 [음식 앱] 음식 품절 처리 #91](https://github.com/jiminchur/Project_FoodToGoWeb/issues/91)
[🍱 [음식 앱] 음식 삭제 #77](https://github.com/jiminchur/Project_FoodToGoWeb/issues/77)
[🍱 [음식 앱] 음식 숨김 처리 #76](https://github.com/jiminchur/Project_FoodToGoWeb/issues/76)
[🍱 [음식 앱] 음식 상세 정보 수정 #75](https://github.com/jiminchur/Project_FoodToGoWeb/issues/75)
[🍱 [음식 앱] 음식 단건 조회 (운영진) #74](https://github.com/jiminchur/Project_FoodToGoWeb/issues/74)
[🍱 [음식 앱] 음식 전체 조회 (운영진) #73](https://github.com/jiminchur/Project_FoodToGoWeb/issues/73)
[🍱 [음식 앱] 가게에 속한 음식 조건 검색 #72](https://github.com/jiminchur/Project_FoodToGoWeb/issues/72)
[🍱 [음식 앱] 가게에 속한 음식 전체 조회 #70](https://github.com/jiminchur/Project_FoodToGoWeb/issues/70)
[🍱 [음식 앱] 가게에 속한 음식 등록 #69](https://github.com/jiminchur/Project_FoodToGoWeb/issues/69)

</details>

<details>
    <summary>🤖 AI</summary>

[[🤖 AI] ai 기록조회 #148](https://github.com/jiminchur/Project_FoodToGoWeb/issues/148)
[[🤖 AI] ai 요청 및 데이터베이스에 저장 #132](https://github.com/jiminchur/Project_FoodToGoWeb/issues/132)

</details>

<details>
    <summary>🛠️ CICD</summary>

</details>

</details>

<details>
    <summary>🧑🏻‍💻 박현도</summary>
    
* 주문
* 결제
* 배송지
* 유저

</details>
<details>
    <summary>👩🏻‍💻 한정원</summary>

* gateway
* auth
* 유저
* redis

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
