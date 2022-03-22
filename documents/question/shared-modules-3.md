안녕하세요, 클릭해 주셔서 감사합니다. 현재 모듈간 의존성이 아래와 같이 돼 있습니다.

![dependencies](https://github.com/runner-be/RunnerBe-Android/raw/develop/art/project-dependency-graph/graph.dot.png)

마지막 부분을 보시면 shared 모듈만 3개가 만들어져 있습니다.

![image](https://user-images.githubusercontent.com/40740128/158545047-dc14b1f2-358c-4531-9a84-54cb5441cb80.png)

각각 shared 모듈들의 역할은 아래와 같습니다.

### :shared

`:domain` 을 제외한 모든 모듈에서 쓰일 수 있으며, 안드로이드 의존성을 가지고 있음 (Context, Activity, DataStore... 등등)

> 안드로이드 의존성을 가진 모듈에서 공통되는 함수들을 담기 위해 만들어 짐

<img width="378" alt="image" src="https://user-images.githubusercontent.com/40740128/158544543-7065b0fe-5f99-4b18-a4f6-a5ae184fa5df.png">

### :shared-domain

`:domain` 을 포함한 모든 모듈에서 쓰일 수 있으며, 순수 코틀린 의존성만 가지고 있음 (Coroutines, Logeukes)

> domain 에서도 필요한 공통되는 함수들을 담기 위해 만들어 짐
> 
> Logeukes: 코틀린 로깅 라이브러리

<img width="432" alt="image" src="https://user-images.githubusercontent.com/40740128/158544762-cd372cf0-9265-4172-a5aa-469d220e1fc5.png">


### :shared-compose

Jetpack Compose 의존성을 가지고 있으며, UI 모듈에서만 쓰일 수 있음

> Jetpack Compose 를 사용하는 UI 모듈에서 공통되는 함수(composable, style, typography 등등)들을 담기 위해 만들어 짐

<img width="455" alt="image" src="https://user-images.githubusercontent.com/40740128/158544649-1a11eda7-2d7d-4273-a1d0-439d6d4b2191.png">

사용하는덴 문제가 없지만, 이런식으로 shared 모듈만 3개가 나오는게 맞는 구조인가 너무 찝찝해서 여쭤봅니다. 실무 환경에서도 필요에 따라서 이런식으로 모듈이 나오기도 하나요? 만약 개선할 수 있다면 어떤식으로 개선할 수 있을까요?

---

# 답변

#45
