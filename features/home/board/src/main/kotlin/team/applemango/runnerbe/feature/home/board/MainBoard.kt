/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainBoard.kt] created by Ji Sungbin on 22. 2. 23. 오후 11:50
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.board

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import org.orbitmvi.orbit.viewmodel.observe
import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.feature.home.board.component.RunningItem
import team.applemango.runnerbe.feature.home.board.di.module.RepositoryModule
import team.applemango.runnerbe.feature.home.board.di.module.UseCaseModule
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardSideEffect
import team.applemango.runnerbe.feature.home.board.mvi.MainBoardState
import team.applemango.runnerbe.shared.compose.component.ToggleTopBar
import team.applemango.runnerbe.shared.compose.component.ToggleTopBarItem
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.FontAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.constant.EmptyString
import team.applemango.runnerbe.shared.domain.extension.toMessage
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.toast

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainBoard(
    modifier: Modifier = Modifier,
    isBookmarkPage: Boolean = false,
    userToken: UserToken,
    runningItems: List<RunningItem>,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    val beforeText = stringResource(R.string.onboard_toggletopbaritem_before)
    val afterText = stringResource(R.string.onboard_toggletopbaritem_after)
    val offText = stringResource(R.string.onboard_toggletopbaritem_off)
    val appNameText = stringResource(R.string.mainboard_title_app_name)
    val bookmarkText = stringResource(R.string.mainboard_title_bookmark_list)
    val titleText = remember(isBookmarkPage) {
        when (isBookmarkPage) {
            true -> bookmarkText
            else -> appNameText
        }
    }
    val titleTextStyle = remember(isBookmarkPage) {
        when (isBookmarkPage) {
            true -> Typography.Body16R.copy(color = ColorAsset.G3)
            else -> TextStyle( // Custom, Typography 에 존재하지 않음
                fontFamily = FontAsset.Aggro,
                color = ColorAsset.PrimaryDark,
                fontSize = 16.sp,
                lineHeight = 31.sp,
                letterSpacing = (-0.4).sp
            )
        }
    }
    val toggleTabBarItems = remember {
        listOf(
            ToggleTopBarItem(id = RunningItemType.Before, text = beforeText),
            ToggleTopBarItem(id = RunningItemType.After, text = afterText),
            ToggleTopBarItem(id = RunningItemType.Off, text = offText),
        )
    }

    var includeFinishState by remember { mutableStateOf(false) }
    var runningItemsState by remember { mutableStateOf(runningItems) }
    val forEachItemsBookmarkedState = remember(runningItemsState.size) {
        mutableStateListOf(*runningItemsState.map { it.bookmarked }.toTypedArray())
    }
    var selectedRunningItemTypeState by remember { mutableStateOf(RunningItemType.Before) }

    val vm = remember(userToken) {
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

    LaunchedEffect(Unit) {
        vm.exceptionFlow.collectWithLifecycle(lifecycleOwner = lifecycleOwner) { exception ->
            handleException(
                context = context,
                exception = exception
            )
        }
        vm.observe(
            lifecycleOwner = lifecycleOwner,
            state = { state ->
                handleState(
                    context = context,
                    state = state
                )
            },
            sideEffect = { sideEffect ->
                when (sideEffect) {
                    is MainBoardSideEffect.UpdateRunningItemSideEffect -> {
                        runningItemsState = sideEffect.items
                    }
                    is MainBoardSideEffect.ToggleBookmarkState -> {
                        forEachItemsBookmarkedState[sideEffect.itemIndex] =
                            !forEachItemsBookmarkedState[sideEffect.itemIndex]
                    }
                }
            }
        )
    }

    ModalBottomSheetLayout(
        sheetContent = {}, // TODO
        content = {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                    }
                }
            ) {
                Column(modifier = modifier.fillMaxSize()) {
                    Box( // ToolBar
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = titleText,
                            style = titleTextStyle
                        )
                        if (!isBookmarkPage) { // 타이틀 오른쪽 검색, 알림 아이템들
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 16.dp,
                                    alignment = Alignment.End
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_outlined_search_24),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_outlined_bell_24),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                    ToggleTopBar(
                        modifier = Modifier.padding(top = 4.dp),
                        toggleTopBarItems = toggleTabBarItems,
                        baseBackgroundColor = ColorAsset.G6,
                        activateBackgroundColor = ColorAsset.Primary,
                        activateTextColor = ColorAsset.G6,
                        inactivateTextColor = ColorAsset.G4_5,
                        activateTextStyle = Typography.Body14M,
                        inactivateTextStyle = Typography.Body14R,
                        onItemClick = { runningItemType ->
                            selectedRunningItemTypeState = runningItemType
                        }
                    )
                    if (!isBookmarkPage) { // ToggleTopBar 아래 마감 포함, 거리순, 필터 아이템들
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 16.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "마감 포함",
                                    style = Typography.Body12R.copy(color = ColorAsset.G4)
                                )
                                Checkbox(
                                    modifier = Modifier.padding(start = 4.dp),
                                    checked = includeFinishState,
                                    onCheckedChange = { includeFinishState = it }
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "거리순",
                                    style = Typography.Body12R.copy(color = ColorAsset.G4),
                                )
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 2.dp)
                                        .clickable {
                                            // TODO: sort
                                        },
                                    painter = painterResource(R.drawable.ic_round_chevron_down),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                            Icon(
                                modifier = Modifier.clickable {
                                    // TODO: filter
                                },
                                painter = painterResource(R.drawable.ic_round_filter_24),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier,
                    ) {
                        itemsIndexed(items = runningItemsState) { index, item ->
                            RunningItem(
                                item = item,
                                bookmarkState = forEachItemsBookmarkedState[index],
                                requestToggleBookmarkState = {
                                    vm.updateBookmarkState(
                                        itemIndex = index,
                                        itemId = item.itemId,
                                        bookmarked = !forEachItemsBookmarkedState[index]
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

private fun handleState(context: Context, state: MainBoardState) {
    val message = when (state) {
        MainBoardState.None -> EmptyString
        MainBoardState.NonRegisterUser -> "아직 가입되지 않은 유저는 둘러보기만 가능해요."
    }
    if (message.isNotEmpty()) {
        toast(context, message)
    }
}

private fun handleException(context: Context, exception: Throwable) {
    toast(context, exception.toMessage(), Toast.LENGTH_LONG)
    logeukes(type = LoggerType.E) { exception }
}
