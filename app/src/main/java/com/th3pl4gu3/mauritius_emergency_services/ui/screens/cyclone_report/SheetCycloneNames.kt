package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneName
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesDataTable
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesModalBottomSheet
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenError
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenLinearLoading
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.capitalize
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetCycloneNamesDecisionsUi(
    showSheetCycloneNames: Boolean,
    cycloneNamesUiState: CycloneNamesUiState,
    onDismissSheetCycloneNames: () -> Unit,
    retryAction: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {

    if (showSheetCycloneNames) {
        MesModalBottomSheet(
            title = "Cyclone Names",
            sheetState = sheetState,
            onDismiss = onDismissSheetCycloneNames
        ) {
            when (cycloneNamesUiState) {
                is CycloneNamesUiState.Success -> {

                    // Extract the attribute names
                    val header = CycloneName::class.members
                        .filterIsInstance<kotlin.reflect.KProperty1<CycloneName, *>>()
                        .map {
                            it.name.replace("_", " ").capitalize()
                        }

                    // Extract the values
                    val data = cycloneNamesUiState.names.map { cyclone ->
                        listOf(cyclone.name, cyclone.gender, cyclone.provided_by, cyclone.named_by)
                    }

                    MesDataTable(
                        data, header
                    )
                }

                is CycloneNamesUiState.Loading -> {
                    MesScreenLinearLoading()
                }

                is CycloneNamesUiState.Error -> {
                    MesScreenError(retryAction = retryAction)
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Cyclone Names Light Preview", showBackground = true)
@Preview(
    "Cyclone Names Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SheetCycloneNamesPreview() {
    val cycloneNamesMockData = listOf(
        CycloneName("Alice", "female", "Bob", "Eve"),
        CycloneName("Bob", "male", "Charlie", "Dave"),
        CycloneName("Charlie", "male", "Alice", "Eve"),
        CycloneName("Dave", "male", "Bob", "Eve"),
        CycloneName("Eve", "female", "Charlie", "Dave")
    )

    MesTheme {
        SheetCycloneNamesDecisionsUi(
            showSheetCycloneNames = true,
            cycloneNamesUiState = CycloneNamesUiState.Success(cycloneNamesMockData),
            onDismissSheetCycloneNames = {},
            retryAction = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Cyclone Names Loading Light Preview", showBackground = true)
@Preview(
    "Cyclone Names Dark Loading Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SheetCycloneNamesLoadingPreview() {
    MesTheme {
        SheetCycloneNamesDecisionsUi(
            showSheetCycloneNames = true,
            cycloneNamesUiState = CycloneNamesUiState.Loading,
            onDismissSheetCycloneNames = {},
            retryAction = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Cyclone Names Error Light Preview", showBackground = true)
@Preview(
    "Cyclone Names Dark Error Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SheetCycloneNamesErrorPreview() {
    MesTheme {
        SheetCycloneNamesDecisionsUi(
            showSheetCycloneNames = true,
            cycloneNamesUiState = CycloneNamesUiState.Error,
            onDismissSheetCycloneNames = {},
            retryAction = {},
            sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
        )
    }
}