package fr.mario.sportcalendar.calendarrepository.datasource

import fr.mario.sportcalendar.calendarrepository.datasource.models.CalendarApiResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface CalendarApi {
    companion object {
        const val baseUrl = "https://hr2v36jyr7.execute-api.eu-west-3.amazonaws.com/default/"
    }

    @GET("events")
    suspend fun getCalendar(): Response<CalendarApiResponse>
}