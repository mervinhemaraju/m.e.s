package com.th3pl4gu3.mes.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
fun ScreenSettings(
) {
    openDialog()
}

@Composable
fun openDialog(){
}

@Preview
@Composable
fun DialogPreview(){
    MesTheme {
        openDialog()
    }
}