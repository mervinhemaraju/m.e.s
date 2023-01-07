package com.th3pl4gu3.mes.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.components.MesEmergencyButton
import com.th3pl4gu3.mes.ui.components.MesIcon
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
fun ScreenHome() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Emergency Police Help Needed ?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Hold the button to quick call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        MesEmergencyButton(
            onClick = {},
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Need other quick emergency actions?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Click one below to call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        )
    }
}

@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ScreenHomePreview() {
    MesTheme {
        ScreenHome()
    }
}