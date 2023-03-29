package fr.mario.sportcalendar.calendarrepository.repository.mappers

import fr.mario.sportcalendar.calendarrepository.datasource.models.CalendarApiResponse
import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel

internal class CalendarApiResponseMapper {
    fun toCalendarDataModel(apiResponse: CalendarApiResponse): CalendarDataModel =
        CalendarDataModel(
            events = apiResponse.events.map(::toCalendarDataModelEvent)
        )

    private fun toCalendarDataModelEvent(
        apiResponse: CalendarApiResponse.Event,
    ): CalendarDataModel.Event =
        CalendarDataModel.Event(
            leftTeamResult = CalendarDataModel.Event.TeamResult(
                teamName = apiResponse.leftTeam.name,
                teamScore = apiResponse.leftTeam.score,
            ),
            rightTeamResult = CalendarDataModel.Event.TeamResult(
                teamName = apiResponse.rightTeam.name,
                teamScore = apiResponse.rightTeam.score,
            ),
            stringDate = apiResponse.stringDate,
            stringTime = apiResponse.stringTime,
        )
}