package com.th3pl4gu3.mauritius_emergency_services.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.th3pl4gu3.mauritius_emergency_services.R

// Define 'Poppins' Font as the main body FF
private val bodyFontFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

// Define the 'Lato' Font as the main display (header) FF
private val displayFontFamily = FontFamily(
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold)
)

// Default Material 3 typography values
private val baseline = Typography()

val MesTypography = Typography(

    // Display Text
    displayLarge = baseline.displayLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 104.sp
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 80.sp
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 64.sp
    ),

    // Headline
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = displayFontFamily,
        fontSize = 56.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily, fontSize = 48.sp),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),

    // Title
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),

    // Body
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),

    // Label
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily, fontSize = 12.sp),
    labelSmall = baseline.labelSmall.copy(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
    ),
)

@Preview("Typography Light", showBackground = true)
@Preview("Typography Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TypographyPreview() {
    val modifier = Modifier.padding(top = 16.dp)

    MesTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Display Large",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Display Medium",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Display Small",
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = modifier
            )

            Text(
                text = "Headline Large",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Headline Small",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = modifier
            )


            Text(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Title Medium",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Title Small",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = modifier
            )


            Text(
                text = "Body Large",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Body Medium",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Body Small",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = modifier
            )


            Text(
                text = "Label Large",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Label Medium",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
            Text(
                text = "Label Small",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
        }
    }
}