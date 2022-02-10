/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [EmailVerify.kt] created by Ji Sungbin on 22. 2. 10. 오후 4:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.constant.EmailVerifyState
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.compose.util.collectAsStateWithLifecycleRemember
import team.applemango.runnerbe.shared.compose.util.collectWithLifecycleRememberOnLaunchedEffect
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.dataStore

private val Shape = RoundedCornerShape(8.dp)

@Composable
internal fun EmailVerify() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val emailInputFlow = remember { MutableStateFlow("") }
    val emailInputState by emailInputFlow.collectAsStateWithLifecycleRemember("")
    var emailVerifyState by remember { mutableStateOf(EmailVerifyState.None) }
    val focusManager = LocalFocusManager.current

    context.dataStore.data.collectWithLifecycleRememberOnLaunchedEffect { preferences ->
        preferences[DataStoreKey.Onboard.Email]?.let { restoreEmail ->
            emailInputFlow.emit(restoreEmail)
        }
        cancel("onboard restore execute must be once.")
    }
    emailInputFlow.collectWithLifecycleRememberOnLaunchedEffect(debounceTimeout = 500L) { email ->
        context.dataStore.edit { preferences ->
            preferences[DataStoreKey.Onboard.Email] = email
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (field, button) = createRefs()

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(field) {
                        start.linkTo(parent.start)
                        end.linkTo(button.start, 8.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .border(width = 1.dp, color = ColorAsset.Primary, shape = Shape),
                value = emailInputState,
                onValueChange = {
                    if (!it.contains(" ")) {
                        coroutineScope.launch {
                            emailInputFlow.emit(it)
                        }
                    }
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                placeholder = {
                    Text(text = StringAsset.Hint.PlaceholderEmail, style = Typography.EngBody16R)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = ColorAsset.G5_5,
                    textColor = ColorAsset.G1,
                    cursorColor = ColorAsset.Primary,
                    focusedBorderColor = ColorAsset.Primary,
                    unfocusedBorderColor = ColorAsset.Primary,
                    placeholderColor = ColorAsset.G2
                ),
                textStyle = Typography.EngBody16R,
                shape = Shape
            )
            Button(
                modifier = Modifier.constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(field.top)
                    bottom.linkTo(field.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                },
                shape = Shape,
                colors = ButtonDefaults.buttonColors(backgroundColor = ColorAsset.Primary),
                onClick = {
                    // TODO
                }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = StringAsset.Button.Verify,
                    style = Typography.Body14M.copy(color = ColorAsset.G5)
                )
            }
        }
        Crossfade(
            modifier = Modifier.padding(top = 12.dp),
            targetState = emailVerifyState
        ) { state ->
            when (state) {
                EmailVerifyState.None -> {}
                EmailVerifyState.Sent -> {
                    Text(
                        text = StringAsset.Hint.SentVerifyLink,
                        style = Typography.Body12R.copy(color = ColorAsset.Primary)
                    )
                }
                EmailVerifyState.Duplicate -> {
                    Text(
                        text = StringAsset.Hint.AlreadyUseEmail,
                        style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
                    )
                }
            }
        }
    }
}
