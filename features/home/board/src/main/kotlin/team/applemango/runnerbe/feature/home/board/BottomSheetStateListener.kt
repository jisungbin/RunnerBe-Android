/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BottomSheetStateListener.kt] created by Ji Sungbin on 22. 4. 1. 오후 2:21
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

fun interface BottomSheetStateListener {
    fun onBottomSheetStateChanged(isExpended: Boolean)
}

object BottomSheetStateListenerHolder {
    internal var bottomSheetStateListener: BottomSheetStateListener? = null

    fun setBottomSheetStateListener(listener: BottomSheetStateListener) {
        bottomSheetStateListener = listener
    }
}
