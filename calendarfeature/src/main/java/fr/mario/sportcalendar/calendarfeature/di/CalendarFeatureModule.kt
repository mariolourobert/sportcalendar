package fr.mario.sportcalendar.calendarfeature.di

import fr.mario.sportcalendar.getcalendarusecase.di.getCalendarUseCaseModule
import org.koin.dsl.module

val calendarFeatureModule = module {

    includes(
        getCalendarUseCaseModule,
    )
}