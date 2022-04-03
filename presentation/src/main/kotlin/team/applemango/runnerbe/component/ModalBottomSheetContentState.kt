package team.applemango.runnerbe.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

sealed class ModalBottomSheetContentValue {
    object Hidden : ModalBottomSheetContentValue()
    class Expand(val content: @Composable () -> Unit) : ModalBottomSheetContentValue()
}

class ModalBottomSheetContentState(initialValue: ModalBottomSheetContentValue) {
    internal var state by mutableStateOf<ModalBottomSheetContentValue>(initialValue)

    fun expand(content: @Composable () -> Unit) {
        state = ModalBottomSheetContentValue.Expand(content)
    }

    fun hide() {
        state = ModalBottomSheetContentValue.Hidden
    }
}

@Composable
fun rememberModalBottomSheetContentState(
    initialValue: ModalBottomSheetContentValue
) = remember {
    ModalBottomSheetContentState(initialValue)
}
