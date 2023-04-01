package fr.mario.sportcalendar.calendarrepository.repository

import fr.mario.sportcalendar.calendarrepository.datasource.CalendarApi
import fr.mario.sportcalendar.calendarrepository.repository.mappers.CalendarApiResponseMapper
import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.commontools.Either
import retrofit2.HttpException
import java.io.IOException

internal class DefaultCalendarRepository(
    private val calendarApi: CalendarApi,
    private val calendarApiResponseMapper: CalendarApiResponseMapper,
) : CalendarRepository {
    override suspend fun getCalendar(): Either<CalendarDataModel, GetCalendarDataFailure> =
        try {
            val response = calendarApi.getCalendar()

            if (response.isSuccessful && response.body() != null) {
                Either.Success(calendarApiResponseMapper.toCalendarDataModel(response.body()!!))
            } else {
                Either.Failure(GetCalendarDataFailure.Network(
                    httpsStatusCode = response.code(),
                ))
            }
        } catch (throwable: Throwable) {
            val failure = when (throwable) {
                is IOException -> {
                    GetCalendarDataFailure.IO(throwable)
                }
                is HttpException -> {
                    GetCalendarDataFailure.Network(throwable.code())
                }
                else -> {
                    GetCalendarDataFailure.Default(throwable)
                }
            }
            Either.Failure(failure)
        }
}