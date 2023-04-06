package fr.mario.sportcalendar.getcalendarusecase.usecase.models

import fr.mario.sportcalendar.testtools.nextLocalDate
import fr.mario.sportcalendar.testtools.nextLocalTime
import fr.mario.sportcalendar.testtools.nextString
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

internal fun Random.nextCalendar(
    eventsCount: Int = nextInt(2, 50)
): Calendar =
    Calendar(
        events = List(eventsCount) {
            nextCalendarEvent()
        }
    )

internal fun Random.nextCalendarEvent(): Event = listOf(
    nextCalendarValidWinEvent(),
    nextCalendarDrawEvent()
).random()

internal fun Random.nextCalendarValidWinEvent(
    winningTeamName: String = nextString(),
    winningTeamScore: Int = nextInt(1, 5),
    losingTeamName: String = nextString(),
    losingTeamScore: Int = nextInt(0, winningTeamScore),
    stringDate: String = nextLocalDate().format(dateFormatter),
    stringTime: String = nextLocalTime(second = 0).format(timeFormatter),
): Event {
    val winningTeam = Team(
        teamName = winningTeamName,
        teamScore = winningTeamScore,
        teamSide = TeamSide.values().random(),
    )

    val losingTeam = Team(
        teamName = losingTeamName,
        teamScore = losingTeamScore,
        teamSide = TeamSide.values().first { it != winningTeam.teamSide }
    )

    return Event(
        stringDate = stringDate,
        stringTime = stringTime,
        result = EventResult.ValidWin(
            winningTeam = winningTeam,
            losingTeam = losingTeam,
        )
    )
}

internal fun Random.nextCalendarDrawEvent(
    firstTeamName: String = nextString(),
    secondTeamName: String = nextString(),
    commonScore: Int = nextInt(0, 5),
    stringDate: String = nextLocalDate().format(dateFormatter),
    stringTime: String = nextLocalTime(second = 0).format(timeFormatter),
): Event {
    val firstTeam = Team(
        teamName = firstTeamName,
        teamScore = commonScore,
        teamSide = TeamSide.values().random(),
    )

    val secondTeam = Team(
        teamName = secondTeamName,
        teamScore = commonScore,
        teamSide = TeamSide.values().first { it != firstTeam.teamSide }
    )

    return Event(
        stringDate = stringDate,
        stringTime = stringTime,
        result = EventResult.Draw(
            teams = listOf(firstTeam, secondTeam),
        )
    )
}

private val dateFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRANCE)
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE)