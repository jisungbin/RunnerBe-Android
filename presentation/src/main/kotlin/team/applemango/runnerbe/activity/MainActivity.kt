/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [BoardActivity.kt] created by Ji Sungbin on 22. 2. 9. 오전 1:06
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.async
import team.applemango.runnerbe.R
import team.applemango.runnerbe.component.IconBottomBar
import team.applemango.runnerbe.component.StateIcon
import team.applemango.runnerbe.constant.ScreenType
import team.applemango.runnerbe.data.runningitem.repository.RunningItemRepositoryImpl
import team.applemango.runnerbe.domain.constant.Gender
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.domain.runningitem.filter.AgeFilter
import team.applemango.runnerbe.domain.runningitem.filter.DistanceFilter
import team.applemango.runnerbe.domain.runningitem.filter.JobFilter
import team.applemango.runnerbe.domain.runningitem.filter.KeywordFilter
import team.applemango.runnerbe.domain.runningitem.filter.RunningItemFilter
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runningitem.usecase.LoadRunningItemsUseCase
import team.applemango.runnerbe.feature.home.board.MainBoard
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.GradientAsset
import team.applemango.runnerbe.shared.compose.theme.Typography

class MainActivity : ComponentActivity() {

    val runningItems by lazy {
        lifecycleScope.async {
            LoadRunningItemsUseCase(RunningItemRepositoryImpl()).invoke(
                itemType = RunningItemType.Before,
                includeEndItems = true,
                itemFilter = RunningItemFilter.New,
                distanceFilter = DistanceFilter.None,
                genderFilter = Gender.All,
                ageFilter = AgeFilter.None,
                jobFilter = JobFilter.None,
                locate = Locate(
                    address = "",
                    latitude = 0.0,
                    longitude = 0.0
                ),
                keywordFilter = KeywordFilter.None
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(Unit) {
                    systemUiController.setStatusBarColor(Color.Transparent)
                    systemUiController.setNavigationBarColor(ColorAsset.G6)
                }
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = GradientAsset.BlackGradientBrush)
                        .systemBarsPadding(start = false, end = false)
                )
            }
        }
    }

    @Composable
    private fun MainScreen(modifier: Modifier) {
        var runningItemsState by remember { mutableStateOf(emptyList<RunningItem>()) }

        LaunchedEffect(Unit) {
            runningItemsState = runningItems.await().getOrThrow()
        }

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bee))

        val bottomBarStateIcons = remember {
            listOf(
                StateIcon(
                    id = ScreenType.Main,
                    activate = R.drawable.ic_round_home_24,
                    inactivate = R.drawable.ic_outlined_home_24
                ),
                StateIcon(
                    id = ScreenType.Bookmark,
                    activate = R.drawable.ic_round_bookmark_24,
                    inactivate = R.drawable.ic_outlined_bookmark_24
                ),
                StateIcon(
                    id = ScreenType.Mypage,
                    activate = R.drawable.ic_round_me_24,
                    inactivate = R.drawable.ic_outlined_me_24
                )
            )
        }
        var selectedScreenType by remember { mutableStateOf(bottomBarStateIcons.first().id) }

        ConstraintLayout(modifier = modifier) {
            val (content, bottomBars) = createRefs()

            Crossfade(
                modifier = Modifier.constrainAs(content) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomBars.top)
                },
                targetState = selectedScreenType
            ) { screen ->
                when (screen) {
                    ScreenType.Main -> {
                        MainBoard(runningItemsState = runningItemsState)
                    }
                    else -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CoilImage(
                                modifier = Modifier.size(250.dp),
                                imageModel = R.drawable.ic_logo_symbol
                            )
                            Text(
                                modifier = Modifier.padding(top = 30.dp),
                                text = "이 기능은 아직 이용할 수 없어요!",
                                style = Typography.Title18R.copy(color = ColorAsset.PrimaryDark)
                            )
                        }
                    }
                }
            }
            IconBottomBar(
                modifier = Modifier.constrainAs(bottomBars) {
                    bottom.linkTo(parent.bottom)
                },
                stateIcons = bottomBarStateIcons,
                backgroundColor = ColorAsset.G6,
                activateIconTint = Color.Unspecified
            ) { screenType ->
                selectedScreenType = screenType
            }
        }
    }
}
