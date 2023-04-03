package fr.mario.sportcalendar.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
private fun getDarkColorScheme() = darkColorScheme(
    primary = primaryBlue(),
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = OnPrimaryText,
    onSecondary = DarkSecondaryText,
    onBackground = DarkPrimaryText,
    onSurface = DarkPrimaryText,
)

@Composable
private fun getLightColorScheme() = lightColorScheme(
    primary = primaryBlue(),
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onPrimary = OnPrimaryText,
    onSecondary = LightSecondaryText,
    onBackground = LightPrimaryText,
    onSurface = LightPrimaryText,
)

@Composable
fun SportCalendarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        getDarkColorScheme()
    } else {
        getLightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getTypography(colorScheme),
        shapes = Shapes,
        content = content
    )
}