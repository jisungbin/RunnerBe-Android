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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.birjuvachhani.locus.Locus
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.R
import team.applemango.runnerbe.component.ModalBottomSheetContentValue
import team.applemango.runnerbe.component.rememberModalBottomSheetContentState
import team.applemango.runnerbe.constant.Screen
import team.applemango.runnerbe.shared.android.datastore.Me
import team.applemango.runnerbe.shared.android.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.android.extension.setWindowInsetsUsage
import team.applemango.runnerbe.shared.compose.component.BottomBar
import team.applemango.runnerbe.shared.compose.component.BottomBarItem
import team.applemango.runnerbe.shared.compose.default.RunnerbeBottomBarDefaults
import team.applemango.runnerbe.shared.compose.theme.ColorAsset

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // TODO: Firebase Analytics 설정
    // https://firebase.google.com/docs/analytics/userid
    // https://firebase.google.com/docs/analytics/user-properties
    // https://firebase.google.com/docs/analytics/configure-data-collection

    // TODO: Firebase Crashlytics 설정
    // https://firebase.google.com/docs/crashlytics/customize-crash-reports

    private val bottomBarItems = listOf(
        BottomBarItem(
            id = Screen.Home,
            activateIcon = R.drawable.ic_round_home_24,
            inactivateIcon = R.drawable.ic_outlined_home_24
        ),
        BottomBarItem(
            id = Screen.Bookmark,
            activateIcon = R.drawable.ic_round_bookmark_24,
            inactivateIcon = R.drawable.ic_outlined_bookmark_24
        ),
        BottomBarItem(
            id = Screen.Mail,
            activateIcon = R.drawable.ic_round_mail_24,
            inactivateIcon = R.drawable.ic_outlined_mail_24
        ),
        BottomBarItem(
            id = Screen.MyPage,
            activateIcon = R.drawable.ic_round_me_24,
            inactivateIcon = R.drawable.ic_outlined_me_24
        ),
    )

    @OptIn(ExperimentalMaterialApi::class) // rememberModalBottomSheetState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // getCurrentLocate 로 하면 작동하지 않음
        Locus.startLocationUpdates(this) { result ->
            result.location?.let { location ->
                Me.updateLocate(
                    Me.locate.value.copy(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
                Locus.stopLocationUpdates()
            }
        }

        setWindowInsetsUsage()
        setContent {
            val modalBottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            )
            val modalBottomSheetContentState = rememberModalBottomSheetContentState(
                initialValue = ModalBottomSheetContentValue.Hidden
            )
            var statusBarColor by remember { mutableStateOf(Color.Transparent) }

            LaunchedEffect(Unit) {
                snapshotFlow { modalBottomSheetContentState.state }
                    .collectWithLifecycle(this@MainActivity) { state ->
                        when (state) {
                            ModalBottomSheetContentValue.Hidden -> {
                                modalBottomSheetState.hide()
                            }
                            is ModalBottomSheetContentValue.Expand -> {
                                modalBottomSheetState.show()
                            }
                        }
                    }
            }

            ModalBottomSheetLayout(
                modifier = Modifier.fillMaxSize(),
                sheetState = modalBottomSheetState,
                sheetShape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                ),
                sheetBackgroundColor = ColorAsset.G6,
                scrimColor = Color.Black.copy(alpha = 0.5f),
                sheetContent = {
                    when (val state = modalBottomSheetContentState.state) {
                        ModalBottomSheetContentValue.Hidden -> Box(modifier = Modifier.fillMaxSize())
                        is ModalBottomSheetContentValue.Expand -> state.content()
                    }
                },
            ) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (statusBarColorRef, contentRef, bottomBarRef) = createRefs()
                    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current).dp
                    var screenState by remember { mutableStateOf(Screen.Home) }

                    Box(
                        modifier = Modifier
                            .constrainAs(statusBarColorRef) {
                                top.linkTo(anchor = parent.top)

                                width = Dimension.matchParent
                                height = Dimension.value(statusBarHeight)
                            }
                            .background(color = statusBarColor)
                    )
                    Crossfade(
                        modifier = Modifier
                            .constrainAs(contentRef) {
                                top.linkTo(anchor = parent.top)
                                bottom.linkTo(
                                    anchor = bottomBarRef.top,
                                    margin = 16.dp
                                )

                                width = Dimension.matchParent
                                height = Dimension.fillToConstraints
                            }
                            .background(color = Color.Red),
                        targetState = screenState
                    ) { screenType ->
                        when (screenType) {
                            Screen.Home -> {
                            }
                            Screen.Bookmark -> {
                            }
                            Screen.Mail -> {
                            }
                            Screen.MyPage -> {
                            }
                        }
                    }
                    BottomBar(
                        modifier = Modifier.constrainAs(bottomBarRef) {
                            bottom.linkTo(anchor = parent.bottom)

                            width = Dimension.matchParent
                        },
                        colors = RunnerbeBottomBarDefaults.colors(),
                        items = bottomBarItems
                    ) { selectedItem ->
                        screenState = selectedItem.id
                    }
                }
            }
        }
    }
}
