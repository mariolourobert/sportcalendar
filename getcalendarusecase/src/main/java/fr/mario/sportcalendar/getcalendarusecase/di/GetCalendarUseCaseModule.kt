package fr.mario.sportcalendar.getcalendarusecase.di

import fr.mario.sportcalendar.calendarrepository.di.calendarRepositoryModule
import fr.mario.sportcalendar.getcalendarusecase.usecase.DefaultGetCalendarUseCase
import fr.mario.sportcalendar.getcalendarusecase.usecase.GetCalendarUseCase
import fr.mario.sportcalendar.getcalendarusecase.usecase.mappers.CalendarDataModelMapper
import fr.mario.sportcalendar.getcalendarusecase.usecase.mappers.GetCalendarDataFailureMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val getCalendarUseCaseModule = module {
    factoryOf(::CalendarDataModelMapper)
    factoryOf(::GetCalendarDataFailureMapper)
    factoryOf(::DefaultGetCalendarUseCase) bind GetCalendarUseCase::class
    includes(
        calendarRepositoryModule,
    )
}