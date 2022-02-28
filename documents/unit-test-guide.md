# Unit Test 방법 기록
 
1. 서버랑 연결을 아예 끊고 (retrofit 참조 XXX), **모든** response 값을 아래와 같이 각각 리턴하는 함수를 만듦

```kotlin
fun returnSuccess() = """
{
    ... TODO: success response
}
"""

fun returnFailure() = """
{
    ... TODO: failure response
}
"""
```

2. 위에서 만든 모든 응답 값에 대해 domain 으로 바꾸는 작업을 할 때 (mapping) 에러 없이 모두 예상한 대로 작동하면 테스트 성공
