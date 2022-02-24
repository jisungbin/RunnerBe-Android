/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [throwable.kt] created by Ji Sungbin on 22. 2. 10. 오후 10:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

import team.applemango.runnerbe.shared.domain.BuildConfig

fun Throwable.toMessage() = when (BuildConfig.DEBUG) {
    true -> message?.trim() ?: "Error message is null."
    else -> "일시적인 에러가 발생했어요.\n잠시 후 다시 시도해 주세요."
}
