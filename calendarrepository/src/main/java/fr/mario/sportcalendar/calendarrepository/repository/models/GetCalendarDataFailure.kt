package fr.mario.sportcalendar.calendarrepository.repository.models

sealed class GetCalendarDataFailure {
    abstract val throwable: Throwable?

    data class IO(override val throwable: Throwable?) : GetCalendarDataFailure()
    data class Default(override val throwable: Throwable?) : GetCalendarDataFailure()
    data class Network(val httpsStatusCode: Int) : GetCalendarDataFailure() {
        override val throwable: Throwable? = null
    }
}
