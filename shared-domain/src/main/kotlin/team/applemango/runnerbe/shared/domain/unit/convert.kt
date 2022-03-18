package team.applemango.runnerbe.shared.domain.unit

import android.content.res.Resources

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [convert.kt] created by Ji Sungbin on 22. 3. 17. 오후 9:11
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

private val displayMetrics by lazy { Resources.getSystem().displayMetrics }

val Int.dp
    get() = Dp(this * displayMetrics.density + 0.5f)

val Int.sp
    get() = Sp(this * displayMetrics.scaledDensity)

private val Double.sp
    get() = this * displayMetrics.scaledDensity

val Int.px
    get() = Px(dp.value * displayMetrics.density)

val Double.em
    get() = Em((sp * 0.05).toFloat())
