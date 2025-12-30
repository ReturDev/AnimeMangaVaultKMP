package com.returdev.animemanga.ui.screen.onboarding.datepicker.composable.picker.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A custom text field specifically designed for date input.
 *
 * This composable ensures that only numeric input is allowed, restricts input
 * to a specified [maxLength], and provides a custom placeholder. It also
 * supports keyboard actions for moving focus or completing input.
 *
 * @param modifier Modifier to adjust layout or behavior of this text field.
 * @param focusManager The [FocusManager] used to control keyboard focus movement.
 * @param value The current text value displayed in the text field.
 * @param placeholder The placeholder text to show when the field is empty.
 * @param maxLength The maximum number of characters allowed in the text field.
 * @param onValueChange Lambda triggered when the text value changes.
 * @param onFocusChange Lambda triggered when the focus state changes (true if focused).
 * @param onDone Optional lambda triggered when the "Done" IME action is pressed.
 */
@Composable
fun CustomDateTextField(
    modifier : Modifier = Modifier,
    focusManager : FocusManager,
    value : String,
    placeholder : String,
    maxLength : Int,
    onValueChange : (String) -> Unit,
    onFocusChange : (Boolean) -> Unit,
    onDone : (() -> Unit)? = null
) {

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        modifier = modifier.requiredWidth(IntrinsicSize.Min)
            .onFocusChanged { state ->
                onFocusChange(state.isFocused)
            },
        value = TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        ),
        onValueChange = { newTextFieldValue ->

            val newValue = newTextFieldValue.text

            if (newValue.length > maxLength) return@BasicTextField
            if (! newValue.all { it.isDigit() }) return@BasicTextField

            onValueChange(newValue)

        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            showKeyboardOnFocus = true,
            imeAction = if (onDone == null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Next) },
            onDone = { onDone?.invoke() }
        ),
        decorationBox = {
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = it,
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeholder
                    )
                },
                enabled = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }
    )

}
