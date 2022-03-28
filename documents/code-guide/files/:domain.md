# :domain

모든 API 들의 최소한의 모양만 작성해 두었습니다. 순수 **코틀린만** 의존성을 가지고 있습니다.

## UseCase

- API exception catch 를 위해 API Call 에서 exception 발생시 throw 시키고, UseCase 에서 runCatch 를 통해 Result 로
  결과를 받아옵니다. 따라서 UseCase 를 통해 보다 쉽게 API result handling 을 할 수 있습니다.
- Repository 까지는 추후 코드 파악을 위해 서버에서 사용한 네이밍을 그대로 사용합니다. UseCase 에서 네이밍을 바꿔서 사용합니다.

## Dokka

domain 구조는 Dokka 를 통해 문서화를 해두었습니다. [여기](https://runnerbe.xyz/docs/android)서 확인 가능합니다.

# 문제

현재 FirebaseRepository 에 Bitmap 을 인자로 받는 함수가
있습니다. ([#46](https://github.com/runner-be/RunnerBe-Android/issues/46))
이는 domain 은 platform aware 해야 한다는 규칙을 깨는 부분이고, 테스트를 어렵게 만드는 주요 원인입니다. 이 문제가 발생한 문제는 온보딩 과정에서 사용자에게
사진을 입력받는데, 입력받는 방식이 2가지가 있습니다.

- 카메라로부터 찍기
- 갤러리에서 가져오기

카메라로 찍은 경우에는 Bitmap 으로 받아오고, 갤러리에서 가져온 경우엔 Uri 로 받아올 수 있습니다. Uri 을 Bitmap 으로 바꾸는건 쉽게 할 수 있게 때문에, 일단은
이렇게 해 두었습니다. Bitmap 을 Uri 로 바꾸기 위해선 Bitmap 을 내부 저장소에 저장해야 하고, 해당 위치를 받아와야 합니다. 추후 이 방법을 사용하여 이
Bitmap 인자 문자를 해결할 예정입니다.