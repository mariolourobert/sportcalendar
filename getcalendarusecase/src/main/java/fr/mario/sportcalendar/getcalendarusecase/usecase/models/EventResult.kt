package fr.mario.sportcalendar.getcalendarusecase.usecase.models

sealed class EventResult {
    object Draw : EventResult()
    data class EventCancelled(val reason: String?) : EventResult()
    data class ValidWin(val winningTeam: Team, val losingTeam: Team) : EventResult()
}
