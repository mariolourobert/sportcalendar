package fr.mario.sportcalendar.calendarfeature.calendarscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.mario.sportcalendar.designsystem.components.MainScaffold

@Composable
fun CalendarScreen() {
    MainScaffold(
        title = "Calendrier",
    ) {
        Text("Content")
    }
}