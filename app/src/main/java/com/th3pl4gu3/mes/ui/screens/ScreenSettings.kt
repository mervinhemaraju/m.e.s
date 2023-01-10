package com.th3pl4gu3.mes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
fun ScreenSettings(
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Settings Screen Content")
    }
}

@Preview
@Composable
fun DialogPreview(){
    MesTheme {
        ScreenSettings()
    }
}