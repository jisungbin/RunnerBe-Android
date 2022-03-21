/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [viewmodel.kt] created by Ji Sungbin on 22. 3. 19. 오후 10:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.extension

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import team.applemango.runnerbe.shared.compose.optin.LocalActivityUsageApi

@LocalActivityUsageApi
@Composable
inline fun <reified VM : ViewModel> activityViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = null,
): VM = viewModel(
    VM::class.java,
    LocalActivity.current,
    key,
    factory
)
