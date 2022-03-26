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

val Int.domainDp
    get() = Dp(this * displayMetrics.density)

val Int.domainSp
    get() = Sp(this * displayMetrics.scaledDensity)

private val Double.domainSp
    get() = this * displayMetrics.scaledDensity

val Int.domainPx
    get() = Px(domainDp.value * displayMetrics.density)

val Double.domainEm
    get() = Em((this.domainSp * 0.05).toFloat())
