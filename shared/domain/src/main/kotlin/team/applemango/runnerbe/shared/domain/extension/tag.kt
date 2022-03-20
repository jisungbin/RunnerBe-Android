/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [tag.kt] created by Ji Sungbin on 22. 3. 20. 오후 11:34
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.domain.extension

import android.os.Build
import java.util.regex.Pattern

private const val MaxLogLength = 4000
private const val MaxTagLength = 23
private val AnonymousClass = Pattern.compile("(\\$\\d+)+$")

val Throwable.tag
    get() = stackTrace
        .first()
        .let(::createStackElementTag)

private fun createStackElementTag(element: StackTraceElement): String {
    var tag = element.className.substringAfterLast('.')
    val m = AnonymousClass.matcher(tag)
    if (m.find()) {
        tag = m.replaceAll("")
    }
    return if (tag.length <= MaxTagLength || Build.VERSION.SDK_INT >= 26) {
        tag
    } else {
        tag.substring(0, MaxTagLength)
    }
}
