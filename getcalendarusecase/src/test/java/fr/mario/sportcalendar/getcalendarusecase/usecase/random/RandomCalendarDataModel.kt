package fr.mario.sportcalendar.getcalendarusecase.usecase.random

import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.testtools.nextLocalDate
import fr.mario.sportcalendar.testtools.nextLocalTime
import fr.mario.sportcalendar.testtools.nextString
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

internal fun Random.nextCalendarDataModel(
    eventsCount: Int = nextInt(2, 40),
): CalendarDataModel = CalendarDataModel(
    List(eventsCount) {
        nextDataEvent()
    }
)

internal fun Random.nextDataEvent(
    leftTeamName: String = nextString(),
    leftTeamScore: Int = nextInt(0, 5),
    rightTeamName: String = nextString(),
    rightTeamScore: Int = nextInt(0, 5),
    stringDate: String = nextLocalDate().format(dateFormatter),
    stringTime: String = nextLocalTime(second = 0).format(timeFormatter),
): CalendarDataModel.Event = CalendarDataModel.Event(
    leftTeamResult = CalendarDataModel.Event.TeamResult(
        teamName = leftTeamName,
        teamScore = leftTeamScore,
    ),
    rightTeamResult = CalendarDataModel.Event.TeamResult(
        teamName = rightTeamName,
        teamScore = rightTeamScore,
    ),
    stringDate = stringDate,
    stringTime = stringTime,
)

private val dateFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRANCE)
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE)