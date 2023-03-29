package fr.mario.sportcalendar.calendarrepository.repository.models

sealed class GetCalendarDataFailure {
    data class Default(val throwable: Throwable?) : GetCalendarDataFailure()
    data class Network(val httpsStatusCode: Int) : GetCalendarDataFailure()
}
