package fr.mario.sportcalendar

import android.app.Application
import fr.mario.sportcalendar.calendarfeature.di.calendarFeatureModule
import fr.mario.sportcalendar.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SportCalendarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SportCalendarApplication)
            modules(
                calendarFeatureModule,
                networkModule(isDebug = BuildConfig.DEBUG)
            )
        }
    }
}