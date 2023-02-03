package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
fun MesScreenLoading(
    loadingMessage: String = stringResource(id = R.string.message_loading_basic),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 12.dp,
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
        )

        Text(
            text = loadingMessage,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MesScreenError(
    retryAction: () -> Unit,
    image: Painter = painterResource(id = R.drawable.il_error),
    errorMessage: String = stringResource(id = R.string.message_error_content_failed),
    errorButtonText: String = stringResource(id = R.string.action_retry),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = image,
            contentDescription = errorMessage,
            modifier = Modifier.padding(32.dp)
        )

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        MesTextButton(
            text = errorButtonText,
            onClick = retryAction
        )
    }
}

@Composable
fun MesScreenNoContent(
    message: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.il_no_data),
            contentDescription = message,
            modifier = Modifier.padding(32.dp)
        )

        Text(
            text = message,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview("Loading Light Mes Loading Preview", showBackground = true)
@Preview(
    "Loading Dark Mes Loading Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MesScreenLoadingPreview() {
    MesTheme {
        MesScreenLoading()
    }
}

@Preview("Loading Light Mes Error Preview", showBackground = true)
@Preview(
    "Loading Dark Mes Error Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MesScreenErrorPreview() {
    MesTheme {
        MesScreenError(
            retryAction = {}
        )
    }
}