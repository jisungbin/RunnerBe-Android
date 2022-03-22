# :domain

모든 API 들의 최소한의 모양만 작성해 두었습니다.

> usecase, repository

## UseCase

- API exception catch 를 위해 API Call 에서 exception 발생시 throw 시키고, UseCase 에서 runCatch 를 통해 Result 로 결과를 받아옵니다.
따라서 UseCase 를 통해 보다 쉽게 API result handling 을 할 수 있습니다.
- Repository 까지는 추후 코드 파악을 위해 서버에서 사용한 네이밍을 그대로 사용합니다. UseCase 에서 네이밍을 바꿔서 사용합니다. 

## Dokka

domain 구조는 Dokka 를 통해 문서화를 해두었습니다. [여기](https://runnerbe.xyz/docs/android)서 확인 가능합니다.