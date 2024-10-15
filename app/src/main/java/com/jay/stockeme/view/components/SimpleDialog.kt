package com.jay.stockeme.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SimpleAlertDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    confirmText: String = "OK",
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(message)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(confirmText)
                }
            }
        )
    }
}
