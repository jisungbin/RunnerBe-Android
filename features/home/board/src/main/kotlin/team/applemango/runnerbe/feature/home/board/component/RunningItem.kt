/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItem.kt] created by Ji Sungbin on 22. 3. 2. 오후 4:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.skydoves.landscapist.coil.CoilImage
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.feature.home.board.MainBoardViewModel
import team.applemango.runnerbe.feature.home.board.R
import team.applemango.runnerbe.feature.home.board.di.module.RepositoryModule
import team.applemango.runnerbe.feature.home.board.di.module.UseCaseModule
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle

@Composable
internal fun RunningItem(item: RunningItem, userToken: UserToken) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    // TODO: https://github.com/applemango-runnerbe/RunnerBe-Android/issues/52
    val vm = remember {
        val viewModelProvider = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val userRepository = RepositoryModule.provideUserRepository()
                val runningItemRunningItem = RepositoryModule.provideRunningItemRepository()
                val updateBookmarkItemUseCase =
                    UseCaseModule.provideUpdateBookmarkItemUseCase(userRepository)
                val loadRunningItemsUseCase =
                    UseCaseModule.provideLoadRunningItemsUseCase(runningItemRunningItem)
                return MainBoardViewModel(
                    updateBookmarkItemUseCase = updateBookmarkItemUseCase,
                    loadRunningItemsUseCase = loadRunningItemsUseCase,
                    userToken = userToken
                ) as T
            }
        }
        ViewModelProvider(
            owner = viewModelStoreOwner,
            factory = viewModelProvider
        )[MainBoardViewModel::class.java]
    }

    // TODO
    LaunchedEffect(Unit) {
        vm.exceptionFlow.collectWithLifecycle(lifecycleOwner = lifecycleOwner) {
        }
        vm.observe(lifecycleOwner = lifecycleOwner)
    }

    var bookmarkState by remember { mutableStateOf(item.bookmarked) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color = ColorAsset.G5_5)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                CoilImage(
                    modifier = Modifier,
                    imageModel = item.ownerProfileImageUrl
                )
                Text(
                    text = item.title,
                    style = Typography.EngBody12M
                )
            }
            Icon(
                modifier = Modifier.clickable {
                    vm.updateBookmarkState(
                        itemId = item.itemId,
                        bookmarked = !bookmarkState
                    )
                },
                painter = painterResource(
                    when (bookmarkState) {
                        true -> R.drawable.ic_round_bookmark_24
                        else -> R.drawable.ic_outlined_bookmark_24
                    }
                ),
                contentDescription = null
            )
        }
    }
}
