/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardFragment.kt] created by Ji Sungbin on 22. 3. 20. 오후 11:02
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoardFragment.kt] created by Ji Sungbin on 22. 3. 15. 오후 6:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.feature.home.board.component.MainBoardComposable
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState
import team.applemango.runnerbe.shared.android.extension.basicExceptionHandler
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.setWindowInsets
import team.applemango.runnerbe.shared.android.extension.toast
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.shared.domain.extension.defaultCatch

@AndroidEntryPoint
class MainBoardFragment : Fragment() {

    private val vm: MainBoardViewModel by activityViewModels()
    private val isEmptyState = MutableStateFlow(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireActivity()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            MainBoardComposable(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = GradientAsset.BlackGradientBrush)
                    .statusBarsPadding()
                    .padding(16.dp),
                isBookmarkPage = arguments?.getBoolean("bookmark") ?: false,
                isEmptyState = isEmptyState.collectAsState().value
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setWindowInsets()
        vm.exceptionFlow
            .defaultCatch { exception ->
                requireActivity().basicExceptionHandler(exception)
            }.collectWithLifecycle(lifecycleOwner = viewLifecycleOwner) { exception ->
                requireActivity().basicExceptionHandler(exception)
            }
        vm.observe(
            lifecycleOwner = this.viewLifecycleOwner,
            state = ::handleState,
        )
    }

    private fun handleState(state: MainBoardState) {
        val message = when (state) {
            MainBoardState.None -> EmptyString
            MainBoardState.NonRegisterUser -> getString(R.string.mainboard_toast_only_see_unregister_user)
            MainBoardState.RunningItemEmpty -> {
                isEmptyState.value = true
                EmptyString
            }
            MainBoardState.BookmarkToggleRequestFail -> EmptyString // TODO
        }
        if (message.isNotEmpty()) {
            toast(message)
        }
    }
}
