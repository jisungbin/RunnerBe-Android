<p align="center">
  <img src="https://github.com/applemango-runnerbe/.github/blob/main/art/logo/signature_transparent.png?raw=true" width="15%" />
</p>
<p align="center">직장인 타겟 러닝 모임 O2O 플랫폼 🐝</p>
<p align="center">
  <a href="https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/badge/License-MIT-blue"/></a>
  <a href="https://developer.android.com/about/versions/lollipop"><img alt="API 21+" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg"/></a>
  <a href="https://github.com/applemango-runnerbe/RunnerBe-Android"><img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/applemango-runnerbe/RunnerBe-Android"/></a>
  <br/>
  <a href="https://www.codefactor.io/repository/github/applemango-runnerbe/runnerbe-android/overview"><img src="https://www.codefactor.io/repository/github/applemango-runnerbe/runnerbe-android/badge" alt="CodeFactor"/></a>
  <a href="https://sonarcloud.io/summary/new_code?id=applemango-runnerbe_RunnerBe-Android"><img src="https://sonarcloud.io/api/project_badges/measure?project=applemango-runnerbe_RunnerBe-Android&metric=sqale_rating" alt="Maintainability Rating"/>     
  <a href="https://sonarcloud.io/summary/new_code?id=applemango-runnerbe_RunnerBe-Android"><img src="https://sonarcloud.io/api/project_badges/measure?project=applemango-runnerbe_RunnerBe-Android&metric=ncloc" alt="Lines of Code"/></a>
  <br/>
  <a href="https://kotlin.link"><img src="https://kotlin.link/awesome-kotlin.svg" alt="awesome-kotlin"/></a>
  <a href="https://ktlint.github.io/"><img src="https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg" alt="ktlint"/></a>
  <a href="https://wakatime.com/badge/user/2da851dd-14d7-47dd-821a-7d902e52c1c2/project/eead2f63-3468-4e8a-98b2-12de1e4cebb2"><img src="https://wakatime.com/badge/user/2da851dd-14d7-47dd-821a-7d902e52c1c2/project/eead2f63-3468-4e8a-98b2-12de1e4cebb2.svg" alt="wakatime"></a>
</p>

---

# 🥇 마지막 실력 향상 프로젝트

토스/뱅샐/당근 에 지원(2022년 12월 26일)하기 전에, 마지막으로 진행하는 *엄청난* 실력 향상용 프로젝트 입니다.<br/>(엄청난 실력 향상의 변천사: 중3, 고2, 이 프로젝트 이후 예정)

따라서 **아키텍처가 다소 오버하게 적용**됐으며, 아래와 같은 기술들을 처음 적용합니다.

- DFM
- [CI](https://github.com/applemango-runnerbe/RunnerBe-Android/issues/22)/CD
- TDD
- Unit Test + [Test Coverage 100% 목표](https://github.com/applemango-runnerbe/RunnerBe-Android/issues/35)
- DataStore
- WindowInsets
- Feature별 모듈 분리
- Complex Custom View

---

## Tech Skill

#### Architecture

- TDD
- MVW Pattern
- Layered Architecture
- Dynamic Feature Module (:features:register)

#### CI/CD

- Github Actions (unit test)
- Fastlane + Firebase App Distribution

#### Jetpack

- Hilt
- Compose
- DataStore

#### Test

- JUnit5
- Hamcrest
- kotlinx-coroutines-test

#### Etc

- GMS
- Dagger2
- WindowInsets
- Coroutine/Flow
- Retrofit, OkHttp

## Layer

#### Project Dependencies Graph

![](art/project-dependency-graph/graph.dot.png)

<p align="center" >
  <img src="https://img.shields.io/badge/Module-Android-%23baffc9" />
  <img src="https://img.shields.io/badge/Module-Dynamic%20Feature-%23c9baff" /> 
  <img src="https://img.shields.io/badge/Module-Android%20Library-%2381D4FA" />
</p>

#### Dependency Injection Graph

> TODO

## Developer

> [2월 4일] 개발 시작

- **[PM]** [@jisungbin](https://github.com/jisungbin)

---

## 회고 중간 기록

- develop 브런치 만들었어야 했는데 초기에 까먹고 main 브런치에 다 때려박아서 그냥 develop 브런치 버리고 main 으로 통합함
- 세부 feature 별로 브런치 만들어서 진행해야 하는데 아직 feature 브런치에 익숙하지 않아 대분류 feature 만 브런치를 만들며 작업해서 아쉬움

## License

RunnerBe는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE) 파일을 확인해 주세요.
