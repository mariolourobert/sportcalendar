package fr.mario.sportcalendar.getcalendarusecase.usecase

import fr.mario.sportcalendar.commontools.Either
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.Calendar
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.GetCalendarFailure

interface GetCalendarUseCase {
    suspend operator fun invoke(): Either<Calendar, GetCalendarFailure>
}