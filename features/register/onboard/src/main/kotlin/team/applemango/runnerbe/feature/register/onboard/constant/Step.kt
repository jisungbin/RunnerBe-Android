/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Step.kt] created by Ji Sungbin on 22. 2. 8. 오전 2:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.constant

/**
 * @property Terms 약관 동의
 * @property Year 출생 년도
 * @property Gender 성별
 * @property Job 직군
 * @property VerifyWithEmail 화사 이메일로 인증
 * @property VerifyWithEmployeeId 사원증으로 인증
 * @property VerifyWithEmailDone 화서 이메일로 인증 완료
 * @property VerifyWithEmployeeIdRequestDone 사원증으로 인증 완료
 */
internal enum class Step(val index: Int) {
    Terms(0),
    Year(1),
    Gender(2),
    Job(3),
    VerifyWithEmail(4),
    VerifyWithEmployeeId(5),
    VerifyWithEmailDone(6),
    VerifyWithEmployeeIdRequestDone(7),
}
