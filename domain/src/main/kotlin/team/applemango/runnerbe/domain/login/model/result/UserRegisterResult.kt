/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserRegisterResult.kt] created by Ji Sungbin on 22. 2. 12. 오후 2:57
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.login.model.result

/**
 * @property Success 가입 요청 성공 (1005, 1006)
 * @property DuplicateUuid 중복된 uuid (3001)
 * @property DuplicateEmail 중복된 이메일 (3002)
 * @property DuplicateNickname 중복된 닉네임 (랜덤 6자리 숫자로 자동 생성 됐지만 중복됨) (3004)
 * @property DatabaseError 서버 데이터베이스 에러
 * @property Exception 프론트엔드 데이터 처리 에러 (2001 ~ 2009, 3003)
 */
sealed class UserRegisterResult {
    data class Success(val jwt: String) : UserRegisterResult()
    object DuplicateUuid : UserRegisterResult()
    object DuplicateEmail : UserRegisterResult()
    object DuplicateNickname : UserRegisterResult()
    object DatabaseError : UserRegisterResult()
    data class Exception(val code: Int) : UserRegisterResult()
}
