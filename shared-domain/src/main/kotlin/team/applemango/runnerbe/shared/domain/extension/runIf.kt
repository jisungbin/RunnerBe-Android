/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [runIf.kt] created by Ji Sungbin on 22. 2. 6. 오후 4:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else this

inline fun <T> T.runIf(condition: (T) -> Boolean, run: T.() -> T) = if (condition(this)) {
    run()
} else this
