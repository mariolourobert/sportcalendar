package fr.mario.sportcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.mario.sportcalendar.calendarfeature.calendarscreen.CalendarScreen
import fr.mario.sportcalendar.designsystem.theme.SportCalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportCalendarTheme {
                CalendarScreen()
            }
        }
    }
}