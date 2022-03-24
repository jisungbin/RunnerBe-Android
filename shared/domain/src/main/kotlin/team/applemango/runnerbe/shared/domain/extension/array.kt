/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [array.kt] created by Ji Sungbin on 22. 3. 23. 오후 2:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

fun <T> MutableList<T>.updateIndex(index: Int, newValue: T): MutableList<T> {
    return apply { set(index, newValue) }
}

fun <T> MutableList<T>.updateIndex(index: Int, newValueBuilder: T.() -> T): MutableList<T> {
    return apply { set(index, newValueBuilder(get(index))) }
}
