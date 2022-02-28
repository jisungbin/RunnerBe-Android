# 코드 컨벤션

## API Naming

- 정보를 조회하는건 get 접두사 대신 load 접두사 사용
  (ex_러닝 아이템 리스트 조회, 마이페이지 정보 조회)
- 바로 확정되는 작업이 아닌 승인이 필요한 작업을 요청할 경우 request 접두사 사용
  (ex_회원가입 요청, 러닝 참여 요청)
  
## KDoc

- 모든 API repository 에 최소한의 KDoc 기록
- 헷갈릴 수 있는 return 이나 param 은 KDoc 을 기록 하되, param 의 경우 하나라도 붙으면
  모든 param 의 정보를 기록