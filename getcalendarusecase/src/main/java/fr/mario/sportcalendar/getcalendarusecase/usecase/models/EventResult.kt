package fr.mario.sportcalendar.getcalendarusecase.usecase.models

sealed class EventResult {
    data class Draw(val teams: List<Team>) : EventResult()
    data class ValidWin(val winningTeam: Team, val losingTeam: Team) : EventResult()
}
