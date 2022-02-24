/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Job.kt] created by Ji Sungbin on 22. 2. 10. 오전 5:07
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runner

enum class Job(val code: String) {
    PSV("공무원"),
    EDU("교육"),
    DEV("개발"),
    PSM("기획/전략/경영"),
    DES("디자인"),
    MPR("마케팅/PR"),
    SER("서비스"),
    PRO("생산"),
    RES("연구"),
    SAF("영업/제휴"),
    MED("의료"),
    HUR("인사"),
    ACC("제무/회계"),
    CUS("CS")
}
