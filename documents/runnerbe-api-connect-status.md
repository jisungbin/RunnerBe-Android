# 러너비 API 연결 현황

> 모든 연결 완료(✅)의 기준은 repository, repositoryimpl, usecase 의 완성임

### 회원가입 관련 - [RegisterRepository](https://github.com/applemango-runnerbe/RunnerBe-Android/blob/develop/domain/src/main/kotlin/team/applemango/runnerbe/domain/register/runnerbe/repository/RegisterRepository.kt)

1. 카카오 로그인 [✅] - 1번 API
2. 네이버 로그인 [✅] - 2번 API
3. 회원가입 [✅] - 3번 API
4. 이메일 중복 확인 [✅] - 4번 API

### 유저 관련 - [UserRepository](https://github.com/applemango-runnerbe/RunnerBe-Android/blob/develop/domain/src/main/kotlin/team/applemango/runnerbe/domain/user/repository/UserRepository.kt)

1. 닉네임 설정 [✅] - 5번 API
2. ~~JWT 로 사원증 인증 여부 확인 [✖️]~~ - 9번 API **[미사용]**
3. 찜 관리 (등록/해제) [✅] - 20번 API
4. 찜 목록 조회 [✅] - 21번 API
5. 프로필 사진 변경 [✅] - 22번 API
6. 직군 변경 [✅] - 23번 API
7. 마이페이지 정보 조회 [✅] - 24번 API
8. ~~푸시 알림 [✖️]~~ - 26번 API, **[구현 후순위]**
9. 출석 [✅] - 27번 API

### 러닝 아이템 관련 - [RunningItemRepository](https://github.com/applemango-runnerbe/RunnerBe-Android/blob/develop/domain/src/main/kotlin/team/applemango/runnerbe/domain/runningitem/repository/RunningItemRepository.kt)

1. 러닝 아이템 작성 [✅] - 6번 API
2. 러닝 아이템 리스트 조회 [✅] - 7번 API
3. 러닝 아이템 상세 정보 조회 [✅] - 8번 API
4. 러너 모집 마감 (러닝 아이템 작성자 전용) [✅] - 10번 API
5. 러닝 아이템 수정 [✅] - 11번 API
6. 러닝 아이템 삭제 [✅] - 12번 API
7. 러닝 참여 신청 (러닝 아이템 작성자는 불가능) [✅] - 18번 API
8. 러닝 참여 신청 관리 (러닝 아이템 작성자 전용) [✅] - 19번 API
9. 러닝 아이템 신고 [✅] - 25번 API
