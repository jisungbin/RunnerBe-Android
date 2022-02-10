/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [EmailVerify.kt] created by Ji Sungbin on 22. 2. 10. 오후 4:26
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.register.onboard.step

import android.util.Patterns
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.datastore.preferences.core.edit
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.jisungbin.logeukes.logeukes
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
import team.applemango.runnerbe.shared.util.extension.runIf
import team.applemango.runnerbe.shared.util.extension.toMessage

private val Shape = RoundedCornerShape(8.dp)

@Composable
internal fun EmailVerify() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var uuid by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val emailInputFlow = remember { MutableStateFlow("") }
    val emailInputState by emailInputFlow.collectAsStateWithLifecycleRemember("")
    var emailVerifyState by remember { mutableStateOf<EmailVerifyState>(EmailVerifyState.None) }
    var emailSendButtonEnabled by remember { mutableStateOf(false) }

    val emailSendButtonBackgroundColor = animateColorAsState(
        when (emailSendButtonEnabled) {
            true -> ColorAsset.Primary
            else -> ColorAsset.G3
        }
    ).value
    val emailSendButtonTextColor = animateColorAsState(
        when (emailSendButtonEnabled) {
            true -> ColorAsset.G6
            else -> ColorAsset.G4_5
        }
    ).value

    context.dataStore.data.collectWithLifecycleRememberOnLaunchedEffect { preferences ->
        preferences[DataStoreKey.Onboard.Email]?.let { restoreEmail ->
            emailInputFlow.emit(restoreEmail)
        }
        uuid = preferences[DataStoreKey.Login.Uuid]!! // must NonNull
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
                onValueChange = { inputEmail ->
                    if (emailVerifyState == EmailVerifyState.Sent) {
                        emailVerifyState = EmailVerifyState.None
                    }
                    if (!inputEmail.contains(" ")) {
                        emailSendButtonEnabled =
                            Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
                        coroutineScope.launch {
                            emailInputFlow.emit(inputEmail)
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
                    textColor = ColorAsset.G2,
                    cursorColor = ColorAsset.Primary,
                    focusedBorderColor = ColorAsset.Primary,
                    unfocusedBorderColor = ColorAsset.Primary,
                    placeholderColor = ColorAsset.G3_5
                ),
                textStyle = Typography.EngBody16R,
                shape = Shape
            )

            Box(
                modifier = Modifier
                    .constrainAs(button) {
                        end.linkTo(parent.end)
                        top.linkTo(field.top)
                        bottom.linkTo(field.bottom)
                        width = Dimension.wrapContent
                        height = Dimension.fillToConstraints
                    }
                    .clip(Shape)
                    .runIf(emailSendButtonEnabled) {
                        clickable(
                            indication = rememberRipple(),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                val email = emailInputFlow.value
                                logeukes { email }
                                Firebase.auth
                                    .sendSignInLinkToEmail(
                                        email,
                                        getVerifyCodeSettings(uuid)
                                    )
                                    .addOnSuccessListener {
                                        emailVerifyState = EmailVerifyState.Sent
                                    }
                                    .addOnFailureListener { exception ->
                                        emailVerifyState = EmailVerifyState.Exception(exception)
                                    }
                            }
                        )
                    }
                    .background(color = emailSendButtonBackgroundColor, shape = Shape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = StringAsset.Button.Verify,
                    style = Typography.Body14M.copy(color = emailSendButtonTextColor)
                )
            }
        }
        Crossfade(
            modifier = Modifier.padding(top = 12.dp),
            targetState = emailVerifyState
        ) { state ->
            var message = ""
            var style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            when (state) {
                EmailVerifyState.None -> {}
                EmailVerifyState.Sent -> { // success
                    message = StringAsset.Hint.SentVerifyLink
                    style = Typography.Body12R.copy(color = ColorAsset.Primary)
                }
                EmailVerifyState.Duplicate -> {
                    message = StringAsset.Hint.AlreadyUseEmail
                }
                EmailVerifyState.ErrorUuid -> {
                    message = StringAsset.Hint.ErrorUuid
                }
                is EmailVerifyState.Exception -> {
                    message = state.throwable.toMessage()
                }
            }
            if (message.isNotEmpty()) {
                Text(text = message, style = style)
            }
        }
    }
}

private fun getVerifyCodeSettings(uuid: String) = actionCodeSettings {
    url = "https://runnerbe-auth.shop/welcome?verify=$uuid" // 이 값은 입력받는 이메일과 항상 일치해야 함
    handleCodeInApp = true // 필수!
    setAndroidPackageName(
        "team.applemango.runnerbe", // 리다이렉트될 앱 패키지명
        true, // 이용 불가능시 플레이스토어 이동해서 설치 요청
        "21" // min sdk level
    )
}
