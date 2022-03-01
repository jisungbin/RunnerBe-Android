# 코드 컨벤션

## API Naming

- 정보를 조회하는건 get 접두사 대신 load 접두사 사용
  (ex_러닝 아이템 리스트 조회, 마이페이지 정보 조회)
- 바로 확정되는 작업이 아닌 승인이 필요한 작업을 요청할 경우 request 접두사 사용
  (ex_회원가입 요청, 러닝 참여 요청)

## 코드 스타일

- 코틀린 스타일 가이드는 [ktlint](https://ktlint.github.io/)를 적용하며, 코틀린 공식 스타일 가이드를 따름
- 인자가 2개 이상인 경우, 각 인자마다 새로운 라인에 배치
- 함수 괄호가 열린 경우, 새로운 라인에 코드 작성
  단, filter function 과 같이 단순 boolean expression function 일 경우 예외를 가짐

## KDoc

- 모든 API repository 에 최소한의 KDoc 은 기록
- 헷갈릴 수 있는 return 이나 param 은 KDoc 을 기록 하되, param 의 경우 하나라도 붙으면 모든 param 의 정보를 기록