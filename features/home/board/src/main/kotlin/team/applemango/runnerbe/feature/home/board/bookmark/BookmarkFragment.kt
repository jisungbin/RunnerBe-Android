/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BookmarkFragment.kt] created by Ji Sungbin on 22. 3. 16. 오전 9:18
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.bookmark

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import team.applemango.runnerbe.feature.home.board.MainBoardComposable
import team.applemango.runnerbe.feature.home.board.MainBoardViewModel
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.shared.domain.extension.toMessage
import team.applemango.runnerbe.shared.util.extension.toast

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val vm: MainBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainBoardComposable(
                    modifier = Modifier.fillMaxSize(),
                    isBookmarkPage = true,
                    vm = vm,
                )
            }
        }
    }

    private fun handleState(context: Context, state: MainBoardState) {
        val message = when (state) {
            MainBoardState.None -> EmptyString
            MainBoardState.NonRegisterUser -> getString(R.string.mainboard_toast_only_see_unregister_user)
            MainBoardState.RunningItemEmpty -> EmptyString // TODO
            MainBoardState.BookmarkToggleRequestFail -> EmptyString // TODO
        }
        if (message.isNotEmpty()) {
            toast(context, message)
        }
    }

    private fun handleException(context: Context, exception: Throwable) {
        toast(context, exception.toMessage(), Toast.LENGTH_LONG)
        logeukes(type = LoggerType.E) { exception }
    }
}
