package com.example.motionlayoutdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = Colors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)
private val LightColorPalette = Colors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

)
data class Colors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color
)

@Composable
fun MotionLayoutDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors: Colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
}