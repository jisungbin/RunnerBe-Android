/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [throwable.kt] created by Ji Sungbin on 22. 2. 10. 오후 10:19
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.util.extension

import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import team.applemango.runnerbe.shared.BuildConfig

fun Throwable.toMessage(): String {
    logeukes(type = LoggerType.E) { this }
    return when (BuildConfig.DEBUG) {
        true -> message?.trim() ?: "Error message is null."
        else -> "일시적인 에러가 발생했어요.\n잠시 후 다시 시도해 주세요."
    }
}
