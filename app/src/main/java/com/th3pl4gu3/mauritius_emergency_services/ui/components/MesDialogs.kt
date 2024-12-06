package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun MesOneActionDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismissRequest: () -> Unit,
    content: @Composable (() -> Unit),
    confirmButtonAction: () -> Unit,
    confirmButtonLabel: String
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = content,
        confirmButton = {
            TextButton(
                onClick = confirmButtonAction)
            {
                Text(text = confirmButtonLabel)
            }
        },
        modifier = modifier
    )
}

@Composable
@ExperimentalMaterial3Api
fun MesTwoActionDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismissRequest: () -> Unit,
    content: @Composable (() -> Unit),
    confirmButtonAction: () -> Unit,
    confirmButtonLabel: String,
    denyButtonAction: () -> Unit,
    denyButtonLabel: String
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        text = content,
        confirmButton = {
            TextButton(
                onClick = confirmButtonAction)
            {
                Text(text = confirmButtonLabel)
            }
        },
        dismissButton = {
            TextButton(
                onClick = denyButtonAction)
            {
                Text(text = denyButtonLabel)
            }
        },
        modifier = modifier
    )
}

@Preview("Mes One Action Dialog Light", showBackground = true)
@Preview("Mes One Action Dialog Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewMesOneActionDialog() {
    MesTheme {
        MesOneActionDialog(
            title = "This is a title",
            onDismissRequest = {},
            content = { Text("This is a text") },
            confirmButtonAction = {},
            confirmButtonLabel = "Proceed"
        )
    }
}

@Preview("Mes Two Action Dialog Light", showBackground = true)
@Preview("Mes Two Action Dialog Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewMesTwoActionDialog() {
    MesTheme {
        MesTwoActionDialog(
            title = "This is a title",
            onDismissRequest = {},
            content = { Text("This is a text") },
            confirmButtonAction = {},
            confirmButtonLabel = "Proceed",
            denyButtonAction = {},
            denyButtonLabel = "Close"
        )
    }
}