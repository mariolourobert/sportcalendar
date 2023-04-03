package fr.mario.sportcalendar.calendarrepository.di

import fr.mario.sportcalendar.calendarrepository.datasource.CalendarApi
import fr.mario.sportcalendar.calendarrepository.repository.CalendarRepository
import fr.mario.sportcalendar.calendarrepository.repository.DefaultCalendarRepository
import fr.mario.sportcalendar.calendarrepository.repository.mappers.CalendarApiResponseMapper
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit

val calendarRepositoryModule = module {
    single<Retrofit> {
        val oktHttpClient: OkHttpClient = get()
        val jsonConverterFactory: Converter.Factory = get()

        Retrofit.Builder()
            .baseUrl(CalendarApi.baseUrl)
            .client(oktHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    single<CalendarApi> {
        get<Retrofit>().create(CalendarApi::class.java)
    }

    factoryOf(::CalendarApiResponseMapper)

    factory {
        DefaultCalendarRepository(
            calendarApi = get(),
            calendarApiResponseMapper = get(),
        )
    } bind CalendarRepository::class
}