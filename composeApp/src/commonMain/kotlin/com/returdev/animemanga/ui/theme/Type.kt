package com.returdev.animemanga.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Black
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_BlackItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Bold
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_BoldItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_ExtraBold
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_ExtraBoldItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_ExtraLight
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_ExtraLightItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Italic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Light
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_LightItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Medium
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_MediumItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Regular
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_SemiBold
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_SemiBoldItalic
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_Thin
import animemangavaultkmp.composeapp.generated.resources.Inter_18pt_ThinItalic
import animemangavaultkmp.composeapp.generated.resources.Outfit_Black
import animemangavaultkmp.composeapp.generated.resources.Outfit_Bold
import animemangavaultkmp.composeapp.generated.resources.Outfit_ExtraBold
import animemangavaultkmp.composeapp.generated.resources.Outfit_ExtraLight
import animemangavaultkmp.composeapp.generated.resources.Outfit_Light
import animemangavaultkmp.composeapp.generated.resources.Outfit_Medium
import animemangavaultkmp.composeapp.generated.resources.Outfit_Regular
import animemangavaultkmp.composeapp.generated.resources.Outfit_SemiBold
import animemangavaultkmp.composeapp.generated.resources.Outfit_Thin
import animemangavaultkmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

//
//val Typography = Typography(
//    labelSmall = TextStyle(
//        fontSize = 12.sp,
//        fontWeight = FontWeight.Light
//    )
//)

@Composable
private fun displayFontFamily() = FontFamily(
    Font(Res.font.Outfit_Light, FontWeight.Light),
    Font(Res.font.Outfit_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.Outfit_Thin, FontWeight.Thin),
    Font(Res.font.Outfit_Regular, FontWeight.Normal),
    Font(Res.font.Outfit_Medium, FontWeight.Medium),
    Font(Res.font.Outfit_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Outfit_Bold, FontWeight.Bold),
    Font(Res.font.Outfit_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.Outfit_Black, FontWeight.Black)
)

@Composable
private fun bodyFontFamily() = FontFamily(
    Font(Res.font.Inter_18pt_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.Inter_18pt_Light, FontWeight.Light),
    Font(Res.font.Inter_18pt_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.Inter_18pt_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.Inter_18pt_ThinItalic, FontWeight.Thin, FontStyle.Italic),
    Font(Res.font.Inter_18pt_Thin, FontWeight.Thin),
    Font(Res.font.Inter_18pt_Italic, style = FontStyle.Italic),
    Font(Res.font.Inter_18pt_Regular, FontWeight.Normal),
    Font(Res.font.Inter_18pt_MediumItalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.Inter_18pt_Medium, FontWeight.Medium),
    Font(Res.font.Inter_18pt_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.Inter_18pt_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Inter_18pt_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.Inter_18pt_Bold, FontWeight.Bold),
    Font(Res.font.Inter_18pt_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.Inter_18pt_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.Inter_18pt_BlackItalic, FontWeight.Black, FontStyle.Italic),
    Font(Res.font.Inter_18pt_Black, FontWeight.Black)
)

val baseline = Typography()

@Composable
fun appTypography() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily()),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily()),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily()),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily()),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily()),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily()),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily()),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily()),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily()),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily()),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily()),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily()),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily()),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily()),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily()),
)
