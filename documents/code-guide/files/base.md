# 기본 구성

러너비 프로젝트는 빌드 속도 향상과 추후 유지보수의 용이성을 위해 각 feature 마다 개별 모듈인, 멀티 모듈 구조로 설계됐습니다.

## Architecture

- MVI Pattern
  - 단방향 데이터 흐름을 위해 Model-View-Intent 패턴을 사용하였습니다.
- Layered Architecture
  - 멀티 모듈을 채택하면서 자연스럽게 Layered Architecture 로 설계하였습니다.
- Dynamic Feature Module (:features:register)
  - 회원가입은 앱 최소 사용시 딱 1번만 진행되므로, 회원가입 관련 UI 가 담긴 :register 모듈을 다 DFM 로 설계하여 앱 용량을 감소시켰습니다.

## Jetpack

- Hilt
- Room
  - 오프라인 지원을 위해 사용하였습니다.
- Compose
  - 빠른 UI 개발을 위해 Jetpack Compose 를 이용하여 모든 UI 를 제작하였습니다.
  - 단, Jetpack Compose 로 개발하기에 무리가 있는 일부 UI 들만 :xml 모듈에 따로 xml 으로 구현하였습니다. 
- DataStore
  - 온보딩 과정에서 데이터 저장과, 데이터 복원을 위해 안정적이게 DataStore 를 사용하였습니다.
  - SharedPreference 의 경우 데이터 IO 중 오류가 발생하면 그냥 throw 를 하지만, DataStore 는 Flow 기반이라 catch 에 잡히게 됩니다.
- Navigation
  - :presentation 에서 :features 간에 UI 전환을 위해 사용하였습니다. 
  - :presentation 은 그냥 presenter 이기 때문에 직접 UI 를 그리지 않고, UI navigate 만 해주게 설계했습니다. 

## Etc

- GMS
- WindowInsets
  - UI 디테일을 위해 기본 WindowInsets 들을 모두 사용하지 않습니다.
- Coroutine/Flow
  - Rx 와 비교하여 Coroutines 이 숙련도가 꽤 있기에, Rx 대신에 사용하였습니다.
- Retrofit, OkHttp

## Project Dependencies Graph

![](/art/project-dependency-graph/graph.dot.png)

> 점선은 `api` 를 뜻하고, 실선은 `implementation` 을 뜻합니다. 
