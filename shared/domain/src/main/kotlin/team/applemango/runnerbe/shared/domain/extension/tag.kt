// Thanks: https://github.com/JakeWharton/timber/blob/trunk/timber/src/main/java/timber/log/Timber.kt#L204

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
