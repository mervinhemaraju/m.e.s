package com.th3pl4gu3.mes.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
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
        letterSpacing = 9.sp
)

private val MesTitleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 9.sp
)

private val MesHeadlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 5.sp
)

private val MesHeadlineSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        letterSpacing = 2.sp
)

private val MesBodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
)

private val MesBodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
)

/**
 * Set of Material typography styles to for the Mes App
 **/
val MesTypography = Typography(
        titleLarge = MesTitleLarge,
        titleMedium = MesTitleMedium,
        headlineLarge = MesHeadlineLarge,
        headlineSmall = MesHeadlineSmall,
        bodyLarge = MesBodyLarge,
        bodySmall = MesBodySmall
).defaultFontFamily(
        primaryFontFamily = Montserrat,
        secondaryFontFamily = Montserrat_Alternates
)