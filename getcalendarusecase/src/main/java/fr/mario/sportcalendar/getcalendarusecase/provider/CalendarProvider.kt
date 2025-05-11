package fr.mario.sportcalendar.getcalendarusecase.provider

import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.commontools.Either

interface CalendarProvider {
    suspend fun getCalendar(): Either<CalendarDataModel, GetCalendarDataFailure>
}