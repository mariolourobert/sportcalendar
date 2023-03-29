package fr.mario.sportcalendar.calendarrepository.repository

import fr.mario.sportcalendar.calendarrepository.datasource.CalendarApi
import fr.mario.sportcalendar.calendarrepository.repository.mappers.CalendarApiResponseMapper
import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.commontools.Either

internal class DefaultCalendarRepository(
    private val calendarApi: CalendarApi,
    private val calendarApiResponseMapper: CalendarApiResponseMapper,
) : CalendarRepository {
    override suspend fun getCalendar(): Either<CalendarDataModel, GetCalendarDataFailure> =
        try {
            val response = calendarApi.getCalendar()
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                Either.Success(calendarApiResponseMapper.toCalendarDataModel(responseBody))
            } else {
                Either.Failure(GetCalendarDataFailure.Network(
                    httpsStatusCode = response.code(),
                ))
            }
        } catch (exception: Exception) {
            Either.Failure(GetCalendarDataFailure.Default(
                throwable = exception,
            ))
        }
}