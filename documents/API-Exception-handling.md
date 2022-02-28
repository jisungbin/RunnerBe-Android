# API 예외 처리 방법 통일안

- Exception Message 를 label 등으로 따로 표시할 수 있는 UI가 있는 경우(ex_메일 발송 UI)에만 
  failure result code 까지 domain 으로 오픈
- 위와 같은 케이스를 제외한 **모든** 케이스는 successful result code 만 domain 으로 오픈하고, 
  failure result code 는 data 에서 throw 처리
