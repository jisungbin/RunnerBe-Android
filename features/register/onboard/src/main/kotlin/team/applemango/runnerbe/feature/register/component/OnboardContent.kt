/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [OnboardContent.kt] created by Ji Sungbin on 22. 2. 8. 오전 3:29
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import team.applemango.runnerbe.feature.register.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.constant.ButtonType
import team.applemango.runnerbe.feature.register.onboard.constant.Step
import team.applemango.runnerbe.theme.ColorAsset
import team.applemango.runnerbe.theme.Typography

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun OnboardContent(
    modifier: Modifier,
    step: Step,
    bottomCTAButtonEnabled: Boolean,
    bottomCTAButtonType: ButtonType = ButtonType.Normal,
    onBottomCTAButtonAction: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val title = when (step) {
        Step.Terms -> StringAsset.Title.ReadTerms
        Step.Birthday -> StringAsset.Title.InputYear
        Step.Gender -> StringAsset.Title.SelectGender
        Step.Job -> StringAsset.Title.WhatsJob
        Step.VerifyWithEmail -> StringAsset.Title.VerifyWithEmail
        Step.VerifyWithEmployeeId -> StringAsset.Title.VerifyWitheEmployeeId
        Step.EmailVerifyDone -> StringAsset.Title.EmailVerifyDone
        Step.EmployeeIdVerifyRequestDone -> StringAsset.Title.EmployeeIdVerifyRequestDone
    }
    val subtitle = when (step) {
        Step.Birthday -> StringAsset.Subtitle.AgeVisibleDescription
        Step.Job -> StringAsset.Subtitle.JobCanEditOnMypage
        Step.VerifyWithEmail -> StringAsset.Subtitle.VerifyWithEmail
        Step.VerifyWithEmployeeId -> StringAsset.Subtitle.VerifyWithEmployeeId
        Step.EmailVerifyDone -> StringAsset.Subtitle.EmailVerifyDone
        Step.EmployeeIdVerifyRequestDone -> StringAsset.Subtitle.EmployeeIdVerifyRequestDone
        else -> StringAsset.Empty
    }
    val bottomCTAButtonText = when (step) {
        Step.VerifyWithEmail -> StringAsset.Button.NoEmail
        Step.VerifyWithEmployeeId -> StringAsset.Button.Verify
        Step.EmailVerifyDone -> StringAsset.Button.Start
        Step.EmployeeIdVerifyRequestDone -> StringAsset.Button.GotoMain
        else -> StringAsset.Button.Next
    }
    val animatedBottomCTAButtonBackgroundColor by animateColorAsState(
        when (bottomCTAButtonEnabled) {
            true -> ColorAsset.Primary
            else -> ColorAsset.G3
        }
    )
    val animatedBottomCTAButtonTextColor by animateColorAsState(
        when (bottomCTAButtonEnabled) {
            true -> Color.Black
            else -> ColorAsset.G4_5
        }
    )
    val bottomCTAButtonColor = when (bottomCTAButtonType) {
        ButtonType.Normal -> ButtonDefaults.buttonColors(backgroundColor = animatedBottomCTAButtonBackgroundColor)
        ButtonType.NoEmail -> ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    }
    val bottomCTAButtonBorder = when (bottomCTAButtonType) {
        ButtonType.NoEmail -> BorderStroke(width = 1.dp, color = ColorAsset.Primary)
        else -> null
    }

    ConstraintLayout(modifier = modifier) {
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
                    text = subtitle,
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
        Button(
            modifier = Modifier
                .constrainAs(bottomCTAButton) {
                    bottom.linkTo(parent.bottom, 28.dp)
                    width = Dimension.matchParent
                    height = Dimension.value(48.dp)
                }
                .padding(horizontal = 16.dp),
            onClick = {
                if (bottomCTAButtonEnabled) {
                    onBottomCTAButtonAction()
                }
            },
            colors = bottomCTAButtonColor,
            elevation = null,
            border = bottomCTAButtonBorder,
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = bottomCTAButtonText,
                style = Typography.Body14M.copy(color = animatedBottomCTAButtonTextColor)
            )
        }
    }
}
