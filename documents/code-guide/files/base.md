# 기본 구성

러너비 프로젝트는 빌드 속도 향상과 추후 유지보수의 용이성을 위해 각 feature 마다 개별 모듈인, 멀티 모듈 구조로 설계됐습니다.

## Architecture

- MVI Pattern
- Layered Architecture
- Dynamic Feature Module (:features:register)

## Jetpack

- Hilt
- Room
- Compose
- DataStore
- Navigation

## Etc

- GMS
- WindowInsets
- Coroutine/Flow
- Retrofit, OkHttp

## Project Dependencies Graph

![](/art/project-dependency-graph/graph.dot.png)

점선은 `api` 를 뜻하고, 실선은 `implementation` 을 뜻합니다. 