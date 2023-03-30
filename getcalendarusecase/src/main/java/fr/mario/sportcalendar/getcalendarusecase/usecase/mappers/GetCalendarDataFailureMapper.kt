package fr.mario.sportcalendar.getcalendarusecase.usecase.mappers

import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.GetCalendarFailure

class GetCalendarDataFailureMapper {
    fun toGetCalendarFailure(dataFailure: GetCalendarDataFailure): GetCalendarFailure =
        when (dataFailure) {
            is GetCalendarDataFailure.Default ->
                GetCalendarFailure.Default(throwable = dataFailure.throwable)
            is GetCalendarDataFailure.Network ->
                GetCalendarFailure.Network(httpsStatusCode = dataFailure.httpsStatusCode)
        }
}