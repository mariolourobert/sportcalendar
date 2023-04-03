package fr.mario.sportcalendar.calendarfeature.calendarscreen

import fr.mario.sportcalendar.getcalendarusecase.usecase.models.GetCalendarFailure

class CalendarScreenFailureUiModelMapper {
    fun toUiModel(failure: GetCalendarFailure): CalendarScreenFailureUiModel = when(failure) {
        is GetCalendarFailure.Default -> CalendarScreenFailureUiModel.Unknown
        GetCalendarFailure.InvalidCalendar -> CalendarScreenFailureUiModel.InvalidCalendar
        is GetCalendarFailure.Network -> CalendarScreenFailureUiModel.Network(httpStatus = failure.httpsStatusCode)
    }
}