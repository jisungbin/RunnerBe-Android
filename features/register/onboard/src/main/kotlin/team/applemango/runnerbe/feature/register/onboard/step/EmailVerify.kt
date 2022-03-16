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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import team.applemango.runnerbe.feature.register.onboard.OnboardViewModel
import team.applemango.runnerbe.feature.register.onboard.asset.StringAsset
import team.applemango.runnerbe.feature.register.onboard.constant.EmailVerifyState
import team.applemango.runnerbe.shared.compose.component.RunnerbeDialog
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.domain.extension.runIf
import team.applemango.runnerbe.shared.domain.extension.toMessage
import team.applemango.runnerbe.shared.domain.flowExceptionMessage
import team.applemango.runnerbe.shared.util.extension.dataStore

private const val EmailVerifyStateDoneMessage = "EmailVerifyStateDone"
private val Shape = RoundedCornerShape(8.dp)

@OptIn(FlowPreview::class)
@Composable
internal fun EmailVerify(vm: OnboardViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var emailSentState by remember { mutableStateOf(false) }
    val emailInputFlow = remember { MutableStateFlow("") }
    val emailInputFlowWithLifecycle = remember(emailInputFlow, lifecycleOwner) {
        emailInputFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
    }
    val emailInputStateWithLifecycle by emailInputFlowWithLifecycle.collectAsState("")
    var emailVerifyState by remember { mutableStateOf<EmailVerifyState>(EmailVerifyState.None) }
    var emailSendButtonEnabledState by remember { mutableStateOf(false) }
    var emailVerifyNoticeDialogVisibleState by remember { mutableStateOf(true) }

    EmailVerifyLinkNoticeDialog(
        visible = emailVerifyNoticeDialogVisibleState,
        onDismissRequest = {
            emailVerifyNoticeDialogVisibleState = false
        }
    )

    val emailSendButtonBackgroundColor by animateColorAsState(
        when (emailSendButtonEnabledState) {
            true -> ColorAsset.Primary
            else -> ColorAsset.G3
        }
    )
    val emailSendButtonTextColor by animateColorAsState(
        when (emailSendButtonEnabledState) {
            true -> ColorAsset.G6
            else -> ColorAsset.G4_5
        }
    )

    LaunchedEffect(Unit) {
        context
            .dataStore
            .data
            .cancellable()
            .catch { exception ->
                vm.emitException(
                    Exception(
                        flowExceptionMessage(
                            flowName = "EmailVerify DataStore",
                            exception = exception
                        )
                    )
                )
            }
            .collect { preferences ->
                preferences[DataStoreKey.Onboard.Email]?.let { restoreEmail ->
                    emailInputFlow.emit(restoreEmail)
                    emailSendButtonEnabledState =
                        Patterns.EMAIL_ADDRESS.matcher(restoreEmail).matches()
                }
                cancel("onboard restore execute must be once.")
            }
    }

    LaunchedEffect(Unit) {
        emailInputFlowWithLifecycle
            .debounce(300L)
            .collect { email ->
                context.dataStore.edit { preferences ->
                    preferences[DataStoreKey.Onboard.Email] = email
                }
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
                value = emailInputStateWithLifecycle,
                onValueChange = { inputEmail ->
                    if (emailVerifyState == EmailVerifyState.Sent) {
                        emailVerifyState = EmailVerifyState.None
                    }
                    if (!inputEmail.contains(" ")) {
                        emailSendButtonEnabledState =
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
                    .runIf(emailSendButtonEnabledState) {
                        clickable(
                            indication = rememberRipple(),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                coroutineScope.launch {
                                    emailVerifyState = EmailVerifyState.Loading
                                    val email = emailInputFlow.value
                                    if (vm.checkUsableEmail(email)) {
                                        vm.sendVerifyMail(
                                            email = email,
                                            onSuccess = {
                                                emailSentState = true
                                                emailVerifyState = EmailVerifyState.Sent
                                            },
                                            onException = { exception ->
                                                emailVerifyState =
                                                    EmailVerifyState.Exception(exception)
                                            }
                                        )
                                    } else {
                                        emailVerifyState = EmailVerifyState.Duplicate
                                    }
                                }
                            }
                        )
                    }
                    .background(color = emailSendButtonBackgroundColor, shape = Shape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = if (!emailSentState) StringAsset.Button.Verify else StringAsset.Button.ReVerify,
                    style = Typography.Body14M.copy(color = emailSendButtonTextColor)
                )
            }
        }
        Crossfade(
            modifier = Modifier.padding(top = 12.dp),
            targetState = emailVerifyState
        ) { state ->
            val message: String
            var style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            when (state) {
                EmailVerifyState.None -> {
                    message = EmailVerifyStateDoneMessage
                }
                EmailVerifyState.Sent -> { // success
                    message = StringAsset.Hint.SentVerifyLink
                    style = Typography.Body12R.copy(color = ColorAsset.Primary)
                }
                EmailVerifyState.Loading -> { // success
                    message = StringAsset.Hint.EmailSendingRequest
                    style = Typography.Body12R.copy(color = ColorAsset.Primary)
                }
                EmailVerifyState.Duplicate -> {
                    message = StringAsset.Hint.DuplicateEmail
                }
                EmailVerifyState.ErrorUuid -> {
                    message = StringAsset.Hint.ErrorUuid
                }
                is EmailVerifyState.Exception -> {
                    val throwable = state.throwable
                    message = if (throwable.message.orEmpty().contains("by another account")) {
                        StringAsset.Hint.DuplicateEmail
                    } else {
                        throwable.toMessage()
                    }
                }
            }
            if (message != EmailVerifyStateDoneMessage) {
                Text(text = message, style = style)
            }
        }
    }
}

@Composable
private fun EmailVerifyLinkNoticeDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest
    ) {
        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            text = StringAsset.Dialog.EmailVerifyLinkNotice,
            style = Typography.Title18R.copy(color = ColorAsset.G1)
        )
        Text(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.End)
                .clickable {
                    onDismissRequest()
                }
                .padding(12.dp),
            text = StringAsset.OK,
            style = Typography.Body14M.copy(color = ColorAsset.Primary)
        )
    }
}
