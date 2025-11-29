package com.returdev.animemanga.ui.core.composable.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Displays a customizable modal bottom sheet with two action buttons and additional content.
 *
 * @param modifier Optional [Modifier] applied to the bottom sheet.
 * @param primaryActionText Text label displayed on the primary action button.
 * @param secondaryActionText Text label displayed on the secondary action button.
 * @param onPrimaryAction Callback invoked when the primary action button is clicked.
 * @param onSecondaryAction Callback invoked when the secondary action button is clicked.
 * @param onDismiss Called when the sheet is dismissed either manually or programmatically.
 * @param content Composable content displayed below the action buttons inside the sheet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBottomSheet(
    modifier: Modifier = Modifier,
    primaryActionText: String,
    secondaryActionText: String,
    onPrimaryAction: () -> Unit,
    onSecondaryAction: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val hideDialog: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismiss,
    ) {

        ActionButtons(
            primaryActionText = primaryActionText,
            secondaryActionText = secondaryActionText,
            onPrimaryAction = {
                onPrimaryAction()
                hideDialog()
            },
            onSecondaryAction = {
                onSecondaryAction()
                hideDialog()
            }
        )

        content()

        Spacer(Modifier.height(16.dp))
    }
}

/**
 * Displays two horizontally arranged text buttons for primary and secondary actions.
 *
 * @param primaryActionText Text label for the primary button.
 * @param secondaryActionText Text label for the secondary button.
 * @param onPrimaryAction Callback executed when the primary button is clicked.
 * @param onSecondaryAction Callback executed when the secondary button is clicked.
 */
@Composable
private fun ActionButtons(
    primaryActionText: String,
    secondaryActionText: String,
    onPrimaryAction: () -> Unit,
    onSecondaryAction: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TextButton(onClick = onPrimaryAction) {
            Text(text = primaryActionText)
        }

        TextButton(onClick = onSecondaryAction) {
            Text(text = secondaryActionText)
        }
    }
}
