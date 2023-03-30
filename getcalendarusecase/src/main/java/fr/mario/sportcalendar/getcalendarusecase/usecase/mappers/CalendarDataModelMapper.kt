package fr.mario.sportcalendar.getcalendarusecase.usecase.mappers

import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.commontools.Either
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.*

internal class CalendarDataModelMapper {
    fun toCalendar(calendarDataModel: CalendarDataModel): Either<Calendar, GetCalendarFailure> {
        val mappedEvents = calendarDataModel.events.map(::toEventOrNUll)

        return if (mappedEvents.any { it == null }) {
            Either.Failure(GetCalendarFailure.InvalidCalendar)
        } else {
            Either.Success(
                Calendar(
                    events = mappedEvents.filterNotNull(),
                )
            )
        }
    }

    private fun toEventOrNUll(dataEvent: CalendarDataModel.Event): Event? {
        val allTeams = listOf(
            dataEvent.leftTeamResult,
            dataEvent.rightTeamResult,
        )

        if (allTeams.map { it.teamScore }.any { it < 0 }) {
            return null
        }

        val eventResult = if (
            dataEvent.leftTeamResult.teamScore == dataEvent.rightTeamResult.teamScore
        ) {
            EventResult.Draw
        } else {

            EventResult.ValidWin(
                winningTeam = allTeams.maxBy { it.teamScore }.let(::toTeam),
                losingTeam = allTeams.minBy { it.teamScore }.let(::toTeam),
            )
        }

        return Event(
            stringDate = dataEvent.stringDate,
            stringTime = dataEvent.stringTime,
            result = eventResult,
        )
    }

    private fun toTeam(teamResult: CalendarDataModel.Event.TeamResult): Team =
        Team(
            teamName = teamResult.teamName,
            teamScore = teamResult.teamScore,
        )
}