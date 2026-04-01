# 한끼식사 (MealHub)

## 프로젝트 소개
사용자가 간편하게 레시피를 생성하고 참고할 수 있도록 만든 웹 서비스입니다.  
레시피 조회, 추천, 관리 기능을 제공하여 사용자 편의성을 높였습니다.

---

## 배포 링크
- 서비스 URL: https://mealhub.site/
- PPT 발표자료: https://docs.google.com/presentation/d/1D3yxSzIk0LFxntSI0_tRor5ApjAuxsKl48olgPKjJtc/edit

---

## 기술 스택

### Backend
- Java
- Spring Boot
- JPA / Spring Data JPA
- Querydsl
- MySQL

### Frontend
- React
- JavaScript / HTML / CSS

### 기타
- GitHub
- AWS (Lightsail)

---

## 주요 기능

- 레시피 조회 및 검색 기능
- 사용자 맞춤 레시피 추천
- 레시피 기록 관리
- 로그인 및 인증

---

## 시스템 구조
<img width="1499" height="686" alt="image" src="https://github.com/user-attachments/assets/697073de-56b5-4351-8126-042dc489eca2" />

---

## 핵심 구현 내용

### 1. REST API 설계 (Backend)
- 계층형 구조 (Controller-Service-Repository) 적용
- 유지보수성과 확장성을 고려한 설계

### 2. 데이터 조회 최적화 (Backend)
- Lazy 로딩으로 발생하는 N+1 문제 해결 (fetch join 적용)
- 다중 컬렉션 조회 곱집합 문제 해결 (DTO projection + count(distinct))
- Hibernate batch fetch 전략으로 N+1 완화 및 DB 호출 최적화

### 3. 트랜잭션 처리 및 데이터 안정성 (Backend)
- 여러 엔티티 변경 작업을 하나의 단위로 묶고, 문제 발생 시 rollback, 정상 시 commit
- 영속성 컨텍스트를 통해 엔티티 상태 변화를 자동 반영
- 단순 조회에는 readOnly 옵션 적용으로 성능 최적화

### 4. Querydsl을 활용한 동적 쿼리 (Backend)
- 다양한 조건 검색 기능 구현
- 성능 개선을 위한 쿼리 최적화 적용

### 5. 상태 관리 및 API 연동 (Frontend)
- React 기반 컴포넌트 설계
- 백엔드 API와 비동기 통신 구현

---

## 트러블 슈팅

### 문제: N+1 문제 발생
- 원인: Lazy 로딩으로 인해 반복 쿼리 발생
- 해결: fetch join, DTO projection, batch fetch 적용

### 문제: AWS EC2/RDS 연결 문제
- 원인: RDS 보안 그룹에서 EC2 접근 차단
- 해결: 보안 그룹에 EC2 SG 추가 후 3306 포트 허용

---

## 📌 느낀 점

- 단순 CRUD를 넘어, 성능과 데이터 구조를 고려하는 백엔드 설계 경험 획득
- 프론트엔드와 백엔드를 함께 구현하면서 데이터 흐름과 연동 구조를 깊게 이해
- 문제 발생 시 끝까지 원인을 추적하고 해결하는 책임감과 협업 능력 강화
