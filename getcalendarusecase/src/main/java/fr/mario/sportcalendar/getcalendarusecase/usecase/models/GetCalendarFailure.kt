package fr.mario.sportcalendar.getcalendarusecase.usecase.models

sealed class GetCalendarFailure {
    data class Default(val throwable: Throwable?) : GetCalendarFailure()
    data class Network(val httpsStatusCode: Int) : GetCalendarFailure()
    object InvalidCalendar : GetCalendarFailure()
}
