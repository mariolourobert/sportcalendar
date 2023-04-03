package fr.mario.sportcalendar.calendarfeature.calendarscreen

import fr.mario.sportcalendar.calendarfeature.calendarscreen.CalendarScreenUiModel.Event.ScoreState
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.Calendar
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.Event
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.EventResult
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.TeamSide

class CalendarScreenUiModelMapper {
    fun toUiModel(calendar: Calendar): CalendarScreenUiModel =
        CalendarScreenUiModel(
            events = calendar.events.mapNotNull(::toUiModelEvent)
        )

    private fun toUiModelEvent(event: Event): CalendarScreenUiModel.Event? {
        val result = event.result
        val allTeams = when (result) {
            is EventResult.Draw -> result.teams
            is EventResult.ValidWin -> listOf(result.winningTeam, result.losingTeam)
        }
        val firstTeam = allTeams.firstOrNull { it.teamSide == TeamSide.LEFT }
            ?: return null
        val secondTeam = allTeams.firstOrNull { it.teamSide == TeamSide.RIGHT }
            ?: return null

        val firstTeamScoreState = when (result) {
            is EventResult.Draw ->
                ScoreState.DRAW
            is EventResult.ValidWin ->
                ScoreState.WIN.takeIf { firstTeam == result.winningTeam }
                    ?: ScoreState.LOSS
        }

        val secondTeamScoreState = when (result) {
            is EventResult.Draw ->
                ScoreState.DRAW
            is EventResult.ValidWin ->
                ScoreState.WIN.takeIf { secondTeam == result.winningTeam }
                    ?: ScoreState.LOSS
        }

        return CalendarScreenUiModel.Event(
            formattedDate = event.stringDate,
            formattedTime = event.stringTime,
            teams = listOf(
                CalendarScreenUiModel.Event.Team(
                    teamName = firstTeam.teamName,
                    teamScore = firstTeam.teamScore.toString(),
                    teamScoreState = firstTeamScoreState,
                ),
                CalendarScreenUiModel.Event.Team(
                    teamName = secondTeam.teamName,
                    teamScore = secondTeam.teamScore.toString(),
                    teamScoreState = secondTeamScoreState,
                ),
            ),
        )
    }
}