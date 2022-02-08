/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Step.kt] created by Ji Sungbin on 22. 2. 8. 오전 2:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.constant

internal enum class Step(val index: Int) {
    Terms(0),
    Birthday(1),
    Gender(2),
    Job(3), // 직군 확인
    VerifyWithEmail(4), // 회사 이메일 인증
    VerifyWithEmployeeId(5), // 사원증 인증
    EmailVerifyDone(6), // 이메일 인증 완료
    EmployeeIdVerifyRequestDone(7) // 사원증 인증 완료
}
