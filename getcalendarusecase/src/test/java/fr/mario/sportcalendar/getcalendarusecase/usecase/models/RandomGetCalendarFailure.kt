package fr.mario.sportcalendar.getcalendarusecase.usecase.models

import fr.mario.sportcalendar.testtools.nextString
import kotlin.random.Random

internal fun Random.nextGetCalendarFailure(): GetCalendarFailure =
    listOf(
        GetCalendarFailure.InvalidCalendar,
        GetCalendarFailure.Network(httpsStatusCode = nextInt(400, 600)),
        GetCalendarFailure.Default(throwable = Exception(nextString())),
    ).random()