# 러너비 API 예외 처리 방법 통일안

- Exception Message 를 label 등으로 따로 표시할 수 있는 UI가 있는 경우(ex_메일 발송 UI)에만 
  failure result code 까지 domain 으로 오픈
- 예외적으로 특정 목적을 가지고 사용자에게 처리 상태를 노출해야 하는 경우에도
  failure result code 까지 domain 으로 오픈
- 위와 같은 2가지의 케이스를 제외한 **모든** 케이스는 successful result code 만 domain 으로 오픈하고, 
  failure result code 는 data 에서 throw 처리

# 규칙

- 만약 failure result code 를 오픈했을 경우, 위 2가지의 failure result code 오픈 케이스에 해당하더라도 
왜 오픈 했는지 주석으로 명시 (추후 혼동 방지)

# 오픈의 정의

이 통일안에서 말하는 failure result code 오픈이란, API Call failure state 를 내부에서 throw 하지 않고,
별도의 State Enum Class 로 만들어 domain 레이어로 mapping 시키는 경우임
