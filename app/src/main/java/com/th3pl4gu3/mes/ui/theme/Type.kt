package com.th3pl4gu3.mes.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.utils.defaultFontFamily


/**
 * Defines all the typography separately here and reference them
 * in the main MesTypography below
 **/

// TODO(Fix typography)
private val Montserrat_Alternates = FontFamily(
    Font(R.font.montserrat_alternates_regular),
    Font(R.font.montserrat_alternates_medium, FontWeight.Medium),
    Font(R.font.montserrat_alternates_bold, FontWeight.Bold)
)

private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

private val MesTitleLarge = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
    letterSpacing = 8.sp
)

private val MesTitleMedium = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    letterSpacing = 4.sp
)

private val MesTitleSmall = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    letterSpacing = 2.sp
)

private val MesHeadlineLarge = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
    letterSpacing = 0.4.sp
)

private val MesHeadlineMedium = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 22.sp,
    letterSpacing = 0.4.sp
)

private val MesHeadlineSmall = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    letterSpacing = 0.4.sp
)

private val MesBodyLarge = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.2.sp
)
private val MesBodyMedium = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.1.sp,
    lineHeight = 20.sp
)

private val MesBodySmall = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = 0.1.sp
)

/**
 * Set of Material typography styles to for the Mes App
 **/
val MesTypography = Typography(
    titleLarge = MesTitleLarge,
    titleMedium = MesTitleMedium,
    titleSmall = MesTitleSmall,
    headlineLarge = MesHeadlineLarge,
    headlineMedium = MesHeadlineMedium,
    headlineSmall = MesHeadlineSmall,
    bodyLarge = MesBodyLarge,
    bodyMedium = MesBodyMedium,
    bodySmall = MesBodySmall
).defaultFontFamily(
    primaryFontFamily = Montserrat,
    secondaryFontFamily = Montserrat_Alternates
)

@Preview("Typography Light", showBackground = true)
@Preview("Typography Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TypographyPreview() {
    MesTheme {
        Column {

            Text(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Title Medium",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Title Small",
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Headline Large",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Headline Small",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Body Large",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Body Medium",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Body Small",
                style = MaterialTheme.typography.bodySmall
            )

        }
    }
}
