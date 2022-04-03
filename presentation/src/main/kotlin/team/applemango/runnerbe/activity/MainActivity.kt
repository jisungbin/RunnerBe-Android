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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.birjuvachhani.locus.Locus
import dagger.hilt.android.AndroidEntryPoint
import team.applemango.runnerbe.R
import team.applemango.runnerbe.constant.Screen
import team.applemango.runnerbe.shared.android.datastore.Me
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
                initialValue = ModalBottomSheetValue.Expanded,
                skipHalfExpanded = true
            )

            ModalBottomSheetLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorAsset.G6),
                sheetState = modalBottomSheetState,
                sheetShape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                ),
                sheetBackgroundColor = Color.Cyan,
                scrimColor = Color.Black.copy(alpha = 0.5f),
                sheetContent = {
                    Box(modifier = Modifier.fillMaxWidth().height(300.dp))
                },
            ) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (contentRef, bottomBarRef) = createRefs()
                    var screenState by remember { mutableStateOf(Screen.Home) }

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
                        modifier = Modifier
                            .navigationBarsPadding()
                            .constrainAs(bottomBarRef) {
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
