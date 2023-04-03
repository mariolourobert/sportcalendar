package fr.mario.sportcalendar.calendarfeature.calendarscreen

sealed class CalendarScreenFailureUiModel {
    object Unknown : CalendarScreenFailureUiModel()
    object InvalidCalendar : CalendarScreenFailureUiModel()
    data class Network(val httpStatus: Int) : CalendarScreenFailureUiModel()
}
