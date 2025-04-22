# Capstone-Project

---

## 📂 컴포넌트 파일

### 1. **`Header`**
- **경로**: `resources/static/templates/component/Header.html`
- **설명**: 모든 페이지 상단에 표시되는 헤더입니다.
- **주요 기능**:
  - 로고 및 네비게이션 메뉴(스포츠, 배달, 공구, 게임, OTT) 제공.
- **연결 방법**:
  ```html
  <div th:replace="~{component/Header :: header}"></div>

### 2. **`My-menu`**
- **경로**: `resources/static/templates/component/My-menu.html`
- **설명**: 마이페이지에서 사용되는 사이드바 메뉴입니다.
- **주요 기능**:
  - 마이페이지 관련 메뉴(참여중인 파티, 마이페이지, 로그아웃) 제공.
- **연결 방법**:
  ```html
  <div th:replace="~{component/My-menu :: my-menu}"></div>

### 3. **`Footer`**
- **경로**: `resources/static/templates/component/Footer.html`
- **설명**: 모든 페이지 하단에 표시되는 푸터입니다.
- **주요 기능**:
  - 저작권 정보 및 추가 링크 제공.
- **연결 방법**:
  ```html
  <div th:replace="~{component/Footer :: footer}"></div>

### 4. **`Party-Create-Card`**
- **경로**: `resources/static/templates/component/Party-Create-Card.html`
- **설명**: 새로운 파티를 생성할 때 사용되는 카드 컴포넌트입니다.
- **주요 기능**:
  - 파티 생성 완료 시 관련 제공.
  - 파티 관련 (파티명, 모집인원, 모집 기간, 메뉴, 참여 비용, 오픈채팅방 링크) 제공.
- **연결 방법**:
  ```html
  <div th:replace="~{component/Party-Create-Card :: party-create-card}"></div>
- **데이터 전달 시 주의**:
  - 동적으로 렌더링되는 변수:
    partyName, recruitmentCount, recruitmentPeriod, menu, price, openChatLink.
  - 동적으로 설정된 경로:
    th:href="${openChatLink}"는 오픈 채팅 링크로 이동.


### 5. **`Party-Detail-Cotent`**
- **경로**: `resources/static/templates/component/Party-Detail-Content.html`
- **설명**: 특정 파티의 상세 정보를 표시하는 컴포넌트입니다.
- **주요 기능**:
  - 파티 이름,서비스명, 참여 비용, 날짜 등의 상세 정보 표시.
  - 참여 및 나가기 버튼 제공.
- **연결 방법**:
  ```html
  <div th:replace="~{component/Party-Detail-Content :: party-detail-content}"></div>
- **데이터 전달 시 주의**:
  - 동적으로 렌더링되는 변수
    partyTitle, partySubtitle, serviceName, usagePeriod, cost, endDate, currentMembers, maxMembers, description
  - 동적으로 설정된 경로:
    /Join-Question (참여하기 버튼-107번 라인)
    /index (뒤로가기 버튼-98번 라인)


### 6. **`Party-Join-Card`**
- **경로**: `resources/static/templates/component/Party-Join-Card.html`
- **설명**: 사용자가 참여할 수 있는 파티를 표시하는 카드 컴포넌트입니다.
- **주요 기능**:
  - 파티 이름, 참여 인원, 참여 버튼 제공.
- **연결 방법**:
  ```html
  <div th:replace="~{component/Party-Join-Card :: party-join-card}"></div>
- **데이터 전달 시 주의**:
  - 동적으로 렌더링되는 변수:
    serviceName, period, openChatLink, userList.
  - 동적으로 설정된 경로:
    th:href="${openChatLink}": 오픈 채팅 링크로 이동.

---
