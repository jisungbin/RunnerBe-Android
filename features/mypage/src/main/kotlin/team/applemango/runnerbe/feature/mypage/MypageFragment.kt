/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MypageFragment.kt] created by Ji Sungbin on 22. 3. 16. 오전 9:12
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

@AndroidEntryPoint
class MypageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ColorAsset.Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "TODO", style = Typography.Custom.MainBoardTitle)
                }
            }
        }
    }
}
