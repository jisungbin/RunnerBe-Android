/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BaseViewModel.kt] created by Ji Sungbin on 22. 2. 5. 오후 9:32
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _exceptionChannel = Channel<Throwable>()
    val exceptionFlow = _exceptionChannel.receiveAsFlow()

    open fun emitException(exception: Throwable) = viewModelScope.launch {
        _exceptionChannel.send(exception)
    }
}
