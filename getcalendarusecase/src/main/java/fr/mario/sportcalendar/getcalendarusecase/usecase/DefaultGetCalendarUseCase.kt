package fr.mario.sportcalendar.getcalendarusecase.usecase

import fr.mario.sportcalendar.calendarrepository.repository.CalendarRepository
import fr.mario.sportcalendar.commontools.Either
import fr.mario.sportcalendar.commontools.mapSuccessEither
import fr.mario.sportcalendar.getcalendarusecase.usecase.mappers.CalendarDataModelMapper
import fr.mario.sportcalendar.getcalendarusecase.usecase.mappers.GetCalendarDataFailureMapper
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.Calendar
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.GetCalendarFailure

internal class DefaultGetCalendarUseCase(
    private val calendarRepository: CalendarRepository,
    private val calendarDataModelMapper: CalendarDataModelMapper,
    private val getCalendarDataFailureMapper: GetCalendarDataFailureMapper,
) : GetCalendarUseCase {
    override suspend fun invoke(): Either<Calendar, GetCalendarFailure> =
        calendarRepository.getCalendar()
            .mapFailure<GetCalendarFailure> { dataFailure ->
                getCalendarDataFailureMapper.toGetCalendarFailure(dataFailure)
            }
            .mapSuccessEither { calendarDataModel ->
                calendarDataModelMapper.toCalendar(calendarDataModel)
            }

}