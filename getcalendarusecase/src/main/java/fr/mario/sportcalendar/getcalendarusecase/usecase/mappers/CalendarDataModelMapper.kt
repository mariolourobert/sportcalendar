package fr.mario.sportcalendar.getcalendarusecase.usecase.mappers

import androidx.annotation.VisibleForTesting
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

    @VisibleForTesting
    internal fun toEventOrNUll(dataEvent: CalendarDataModel.Event): Event? {
        val allTeams = listOf(
            dataEvent.leftTeamResult.toTeam(isLeftTeam = true),
            dataEvent.rightTeamResult.toTeam(isLeftTeam = false),
        )

        if (allTeams.map { it.teamScore }.any { it < 0 }) {
            return null
        }

        val eventResult = if (
            dataEvent.leftTeamResult.teamScore == dataEvent.rightTeamResult.teamScore
        ) {
            EventResult.Draw(
                teams = allTeams,
            )
        } else {
            EventResult.ValidWin(
                winningTeam = allTeams.maxBy { it.teamScore },
                losingTeam = allTeams.minBy { it.teamScore },
            )
        }

        return Event(
            stringDate = dataEvent.stringDate,
            stringTime = dataEvent.stringTime,
            result = eventResult,
        )
    }

    @VisibleForTesting
    internal fun CalendarDataModel.Event.TeamResult.toTeam(
        isLeftTeam: Boolean,
    ): Team =
        Team(
            teamName = this.teamName,
            teamScore = this.teamScore,
            teamSide = TeamSide.LEFT.takeIf { isLeftTeam } ?: TeamSide.RIGHT,
        )
}