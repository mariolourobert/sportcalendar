package fr.mario.sportcalendar.calendarrepository.repository

import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.commontools.Either

interface CalendarRepository {
    suspend fun getCalendar(): Either<CalendarDataModel, GetCalendarDataFailure>
}