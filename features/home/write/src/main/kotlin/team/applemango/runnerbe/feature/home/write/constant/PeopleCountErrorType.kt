/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [PeopleCountErrorType.kt] created by Ji Sungbin on 22. 3. 19. 오후 10:10
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.constant

/**
 * 모집 연령 에러 메시지 타입
 *
 * @property None 에러 없음
 * @property Min 최소 2명 이상
 * @property Max 최대 8명까지 가능
 */
enum class PeopleCountErrorType {
    None,
    Min,
    Max
}
