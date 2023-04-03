package fr.mario.sportcalendar.calendarfeature.di

import fr.mario.sportcalendar.calendarfeature.calendarscreen.CalendarScreenUiModelMapper
import fr.mario.sportcalendar.calendarfeature.calendarscreen.CalendarScreenViewModel
import fr.mario.sportcalendar.calendarfeature.calendarscreen.CalendarScreenFailureUiModelMapper
import fr.mario.sportcalendar.getcalendarusecase.di.getCalendarUseCaseModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val calendarFeatureModule = module {
    factoryOf(::CalendarScreenUiModelMapper)
    factoryOf(::CalendarScreenFailureUiModelMapper)
    viewModelOf(::CalendarScreenViewModel)
    includes(
        getCalendarUseCaseModule,
    )
}