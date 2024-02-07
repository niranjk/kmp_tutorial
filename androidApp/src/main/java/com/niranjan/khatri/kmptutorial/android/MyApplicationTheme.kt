package com.niranjan.khatri.kmptutorial.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val startGradientColor = Color(0xFFBEADFA)
val endGradientColor = Color(0xFFDFCCFB)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We define the primary, secondary, and tertiary colors
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF638889),
            secondary = Color(0xFF9DBC98),
            tertiary = Color(0xFFB4B4B8)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFFBFD8AF),
            secondary = Color(0xFFD4E7C5),
            tertiary = Color(0xFFE1F0DA)
        )
    }
    // Define Typography for different Text Styles
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    // Shapes defines how you would like your corners on all kind of Controls
    // Ex. Buttons, Text Field Borders, Floating Action Button
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    // We use Material3 Compose library
    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
