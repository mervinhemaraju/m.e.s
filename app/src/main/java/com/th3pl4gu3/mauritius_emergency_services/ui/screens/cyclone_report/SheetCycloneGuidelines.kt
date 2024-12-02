package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneGuideline
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesModalBottomSheet
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenError
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenLinearLoading
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetCycloneGuidelinesDecisionsUi(
    currentCycloneLevel: Int,
    showSheetCycloneGuidelines: Boolean,
    cycloneGuidelinesUiState: CycloneGuidelinesUiState,
    onDismissSheetCycloneNames: () -> Unit,
    retryAction: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {

    if (showSheetCycloneGuidelines) {

        MesModalBottomSheet(
            title = "Cyclone $currentCycloneLevel Guideline",
            sheetState = sheetState,
            onDismiss = onDismissSheetCycloneNames
        ) {

            when (cycloneGuidelinesUiState) {
                is CycloneGuidelinesUiState.Success -> {

                    Text(
                        text = cycloneGuidelinesUiState.guideline.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.size(32.dp))

                    Text(
                        text = "Precautions",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    LazyColumn {
                        items(cycloneGuidelinesUiState.guideline.precautions) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.small)
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                    .padding(8.dp)
                            )

                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }

                }

                is CycloneGuidelinesUiState.Loading -> {
                    MesScreenLinearLoading()
                }

                is CycloneGuidelinesUiState.Error -> {
                    MesScreenError(retryAction = retryAction)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Cyclone Guidelines Light Preview", showBackground = true)
@Preview(
    "Cyclone Guidelines Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SheetCycloneGuidelinesPreview() {

    val mockDataGuideline = CycloneGuideline(
        level = "1",
        description = "This is a class 1 guideline",
        precautions = listOf(
            "This is a test",
            "This is a test",
            "This is a test",
            "This is a test",
        )
    )

    MesTheme {
        SheetCycloneGuidelinesDecisionsUi(
            currentCycloneLevel = 1,
            showSheetCycloneGuidelines = true,
            cycloneGuidelinesUiState = CycloneGuidelinesUiState.Success(mockDataGuideline),
            onDismissSheetCycloneNames = {},
            retryAction = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Cyclone Guidelines Loading Light Preview", showBackground = true)
@Preview(
    "Cyclone Guidelines Loading Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SheetCycloneGuidelinesLoadingPreview() {
    MesTheme {
        SheetCycloneGuidelinesDecisionsUi(
            currentCycloneLevel = 1,
            showSheetCycloneGuidelines = true,
            cycloneGuidelinesUiState = CycloneGuidelinesUiState.Loading,
            onDismissSheetCycloneNames = {},
            retryAction = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Cyclone Guidelines Error Light Preview", showBackground = true)
@Preview(
    "Cyclone Guidelines Error Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SheetCycloneGuidelinesErrorPreview() {
    MesTheme {
        SheetCycloneGuidelinesDecisionsUi(
            currentCycloneLevel = 1,
            showSheetCycloneGuidelines = true,
            cycloneGuidelinesUiState = CycloneGuidelinesUiState.Error,
            onDismissSheetCycloneNames = {},
            retryAction = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}