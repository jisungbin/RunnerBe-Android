/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardContent.kt] created by Ji Sungbin on 22. 2. 8. 오전 3:29
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.shared.compose.extension.parseHtml
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.util.extension.runIf

private val BottomCTAButtonShape = RoundedCornerShape(24.dp)

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun OnboardContent(
    step: Step,
    bottomCTAButtonEnabled: Boolean,
    onBottomCTAButtonAction: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val title = remember(step) {
        when (step) {
            Step.Terms -> StringAsset.Title.ReadTerms
            Step.Year -> StringAsset.Title.InputYear
            Step.Gender -> StringAsset.Title.SelectGender
            Step.Job -> StringAsset.Title.WhatsJob
            Step.VerifyWithEmail -> StringAsset.Title.VerifyWithEmail
            Step.VerifyWithEmployeeId -> StringAsset.Title.VerifyWitheEmployeeId
            Step.VerifyWithEmailDone -> StringAsset.Title.EmailVerifyDone
            Step.VerifyWithEmployeeIdRequestDone -> StringAsset.Title.EmployeeIdVerifyRequestDone
        }
    }
    val subtitle = remember(step) {
        when (step) {
            Step.Year -> StringAsset.Subtitle.AgeVisibleDescription
            Step.Job -> StringAsset.Subtitle.JobCanEditOnMypage
            Step.VerifyWithEmail -> StringAsset.Subtitle.VerifyWithEmail
            Step.VerifyWithEmployeeId -> StringAsset.Subtitle.VerifyWithEmployeeId
            Step.VerifyWithEmailDone -> StringAsset.Subtitle.EmailVerifyDone
            Step.VerifyWithEmployeeIdRequestDone -> StringAsset.Subtitle.EmployeeIdVerifyRequestDone
            else -> StringAsset.Empty
        }
    }
    val bottomCTAButtonText = remember(step) {
        when (step) {
            Step.VerifyWithEmail -> StringAsset.Button.NoEmail
            Step.VerifyWithEmployeeId -> StringAsset.Button.Verify
            Step.VerifyWithEmailDone -> StringAsset.Button.Start
            Step.VerifyWithEmployeeIdRequestDone -> StringAsset.Button.GotoMain
            else -> StringAsset.Button.Next
        }
    }
    val animatedBottomCTAButtonBackgroundColor by animateColorAsState(
        when (bottomCTAButtonEnabled) {
            true -> ColorAsset.Primary
            else -> ColorAsset.G3
        }
    )
    val animatedBottomCTAButtonTextColor by animateColorAsState(
        when (step) {
            Step.VerifyWithEmail -> ColorAsset.Primary
            else -> {
                when (bottomCTAButtonEnabled) {
                    true -> ColorAsset.G6
                    else -> ColorAsset.G4_5
                }
            }
        }
    )

    // Do NOT remember: if remember, color change not immediately. (maybe state issue)
    val bottomCTAButtonBackgroundColor = when (step) {
        Step.VerifyWithEmail -> Color.Transparent
        else -> animatedBottomCTAButtonBackgroundColor
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topContent, mainContent, bottomCTAButton) = createRefs()

        Column(
            modifier = Modifier.constrainAs(topContent) {
                top.linkTo(parent.top)
                width = Dimension.matchParent
                height = Dimension.wrapContent
            }
        ) {
            Text(
                modifier = Modifier.padding(top = 26.dp),
                text = title,
                style = Typography.Header28M.copy(color = ColorAsset.Primary)
            )
            if (subtitle.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = subtitle.parseHtml(),
                    style = Typography.Body14R.copy(color = ColorAsset.G2_5)
                )
            }
        }
        Box(
            modifier = Modifier
                .constrainAs(mainContent) {
                    top.linkTo(topContent.bottom, 52.dp)
                    bottom.linkTo(bottomCTAButton.top, 52.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.matchParent
                }
        ) {
            content()
        }
        // 내 지식에선 버튼은 리플 제거가 안되서 Box 로 변경
        // enabled 속성으로 변경하면 텍스트 색상도 같이 변경됨
        // (contentBackgroundColor 옵션 설정 해봤음)
        Box(
            modifier = Modifier
                .constrainAs(bottomCTAButton) {
                    bottom.linkTo(parent.bottom, 28.dp)
                    width = Dimension.matchParent
                    height = Dimension.value(48.dp)
                }
                .clip(BottomCTAButtonShape)
                .runIf(bottomCTAButtonEnabled) {
                    clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onBottomCTAButtonAction()
                        }
                    )
                }
                .runIf(step == Step.VerifyWithEmail) {
                    border(width = 1.dp, color = ColorAsset.Primary, shape = BottomCTAButtonShape)
                }
                .background(color = bottomCTAButtonBackgroundColor, shape = BottomCTAButtonShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bottomCTAButtonText,
                style = Typography.Body14M.copy(color = animatedBottomCTAButtonTextColor)
            )
        }
    }
}
