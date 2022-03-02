/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [Diligence.kt] created by Ji Sungbin on 22. 2. 25. 오전 4:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.constant

/**
 * 러너 성실도
 *
 * 초보러너 | 불량러너(1~32)
 * 노력러너(33~65) | 성실러너(66~100)
 */
enum class Tag(val message: String) {
    Starter("초보 러너"),
    Bad("불량 러너"),
    Effort("노력 러너"),
    Awesome("성실 러너")
}
