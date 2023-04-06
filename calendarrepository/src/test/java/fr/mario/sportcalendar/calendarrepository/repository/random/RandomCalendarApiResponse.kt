package fr.mario.sportcalendar.calendarrepository.repository.random

import fr.mario.sportcalendar.calendarrepository.datasource.models.CalendarApiResponse
import fr.mario.sportcalendar.testtools.nextLocalDate
import fr.mario.sportcalendar.testtools.nextLocalTime
import fr.mario.sportcalendar.testtools.nextString
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

internal fun Random.nextCalendarApiResponse(
    eventsCount: Int = nextInt(2, 40),
): CalendarApiResponse = CalendarApiResponse(
    events = List(eventsCount) {
        nextResponseEvent()
    }
)

internal fun Random.nextResponseEvent(
    leftTeamName: String = nextString(),
    leftTeamScore: Int = nextInt(0, 5),
    rightTeamName: String = nextString(),
    rightTeamScore: Int = nextInt(0, 5),
    stringDate: String = nextLocalDate().format(apiDateFormatter),
    stringTime: String = nextLocalTime(second = 0).format(apiTimeFormatter),
): CalendarApiResponse.Event =
    CalendarApiResponse.Event(
        leftTeam = CalendarApiResponse.Event.Team(
            name = leftTeamName,
            score = leftTeamScore,
        ),
        rightTeam = CalendarApiResponse.Event.Team(
            name = rightTeamName,
            score = rightTeamScore,
        ),
        stringDate = stringDate,
        stringTime = stringTime,
    )

private val apiDateFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRANCE)
private val apiTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE)