package fr.mario.sportcalendar.getcalendarusecase.usecase.random

import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.testtools.nextString
import kotlin.random.Random

internal fun Random.nextGetCalendarDataFailure(): GetCalendarDataFailure =
    listOf(
        GetCalendarDataFailure.Default(throwable = Exception(nextString())),
        GetCalendarDataFailure.Network(httpsStatusCode = nextInt(400, 600)),
        GetCalendarDataFailure.IO(throwable = Exception(nextString())),
    ).random()