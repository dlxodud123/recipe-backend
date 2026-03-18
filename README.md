# 🍽️ 한끼식사 (MealHub)

## 📖 프로젝트 소개
사용자가 간편하게 레시피를 생성하고 참고할 수 있도록 만든 웹 서비스입니다.  
레시피 조회, 추천, 관리 기능을 제공하여 사용자 편의성을 높였습니다.

---

## 🚀 배포 링크
- 서비스 URL: https://mealhub.site/
- PPT 발표자료: https://docs.google.com/presentation/d/1D3yxSzIk0LFxntSI0_tRor5ApjAuxsKl48olgPKjJtc/edit

---

## 🛠️ 기술 스택

### Backend
- Spring Boot
- JPA / Spring Data JPA
- Querydsl

### Frontend
- React

### 기타
- GitHub
- AWS (Lightsail)

---

## ✨ 주요 기능

- 🍱 레시피 조회 및 검색 기능
- ⭐ 사용자 맞춤 메뉴 추천
- 📝 레시피 기록 관리
- 🔐 로그인 및 인증

---

## 🏗️ 시스템 구조
<img width="1499" height="686" alt="image" src="https://github.com/user-attachments/assets/697073de-56b5-4351-8126-042dc489eca2" />

---

## ⚙️ 핵심 구현 내용

### 1. Querydsl을 활용한 동적 쿼리
- 다양한 조건 검색 기능 구현
- 성능 개선을 위한 쿼리 최적화 적용

### 2. REST API 설계
- 계층형 구조 (Controller-Service-Repository) 적용
- 유지보수성과 확장성을 고려한 설계

### 3. 상태 관리 및 API 연동 (Frontend)
- React 기반 컴포넌트 설계
- 백엔드 API와 비동기 통신 구현

---

## 🔥 트러블 슈팅

### 문제: N+1 문제 발생
- 원인: 지연 로딩으로 인한 반복 쿼리 발생
- 해결: fetch join 적용으로 쿼리 최적화

---

## 📌 느낀 점

- 단순 CRUD를 넘어 성능과 구조를 고려하는 경험을 얻음
- 프론트와 백엔드를 연결하는 전체 흐름을 이해하게 됨
