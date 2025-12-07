package com.returdev.animemanga.ui.core.composable.sheets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.button_cancel_label
import animemangavaultkmp.composeapp.generated.resources.button_save_label
import animemangavaultkmp.composeapp.generated.resources.ic_check
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

/**
 * Displays a bottom sheet allowing the user to select or clear a library status
 * (e.g., Following, Completed, Dropped) and confirm or cancel the selection.
 *
 * @param modifier Optional [Modifier] applied to the bottom sheet.
 * @param currentUserLibraryStatus The currently saved status for the item, or `null` if none.
 * @param onSave Callback invoked when the user confirms their selection.
 * @param onCancel Callback invoked when dismissing or cancelling the action.
 */
@Composable
fun AddToLibraryBottomSheet(
    modifier : Modifier = Modifier,
    currentUserLibraryStatus : UserLibraryStatusUI?,
    onSave : (UserLibraryStatusUI?) -> Unit,
    onCancel : () -> Unit
) {

    val statusSelected = remember { mutableStateOf(currentUserLibraryStatus) }

    ActionBottomSheet(
        modifier = modifier,
        primaryActionText = stringResource(Res.string.button_cancel_label),
        secondaryActionText = stringResource(Res.string.button_save_label),
        onPrimaryAction = {},
        onSecondaryAction = { onSave(statusSelected.value) },
        onDismiss = onCancel
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            UserLibraryStatusUI.entries.forEach { status ->
                StatusButton(
                    status = status.getText(),
                    isSelected = statusSelected.value == status
                ) { isSelected ->
                    statusSelected.value = if (isSelected) null else status
                }
            }
        }
    }
}

/**
 * A selectable button representing a single library status.
 *
 * @param modifier Optional [Modifier] applied to the row.
 * @param status The textual representation of the status (already localized).
 * @param isSelected Whether this status is currently selected.
 * @param onClick Callback invoked when the button is clicked.
 * Receives the current selection state (`true` if it was selected before clicking).
 */
@Composable
private fun StatusButton(
    modifier : Modifier = Modifier,
    status : String,
    isSelected : Boolean,
    onClick : (Boolean) -> Unit
) {

    val backgroundColor : Color
    val contentColor : Color

    if (isSelected) {
        backgroundColor = MaterialTheme.colorScheme.secondary
        contentColor = MaterialTheme.colorScheme.onSecondary
    } else {
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(25))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(25)
            )
            .clickable { onClick(isSelected) }
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = status,
            color = contentColor
        )

        AnimatedVisibility(visible = isSelected) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_check),
                contentDescription = "Checked",
                tint = contentColor
            )
        }
    }
}