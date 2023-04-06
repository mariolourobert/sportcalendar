package fr.mario.sportcalendar.getcalendarusecase.usecase.mappers

import fr.mario.sportcalendar.calendarrepository.repository.models.CalendarDataModel
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.EventResult
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.GetCalendarFailure
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.TeamSide
import fr.mario.sportcalendar.getcalendarusecase.usecase.random.nextCalendarDataModel
import fr.mario.sportcalendar.getcalendarusecase.usecase.random.nextDataEvent
import fr.mario.sportcalendar.testtools.nextString
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class CalendarDataModelMapperTest {
    private val sut = CalendarDataModelMapper()

    // region toCalendar
    @Test
    fun `GIVEN valid data calendar events WHEN toCalendar THEN it returns Success AND as much events as given`() {
        // GIVEN
        val calendarDataModel = Random.nextCalendarDataModel()

        // WHEN
        val result = sut.toCalendar(calendarDataModel)

        // THEN
        assert(result.isSuccess())
        assertEquals(calendarDataModel.events.size, result.getSuccessValueOrThrow().events.size)
    }

    @Test
    fun `GIVEN valid data calendar events EXCEPT 1 event WHEN toCalendar THEN it returns Failure InvalidCalendar`() {
        // GIVEN
        val validEvents = List(Random.nextInt(1, 50)) {
            Random.nextDataEvent()
        }
        val invalidEvent = listOf(
            Random.nextDataEvent(
                leftTeamScore = Random.nextInt(-5, 0)
            ),
            Random.nextDataEvent(
                rightTeamScore = Random.nextInt(-5, 0)
            )
        ).random()
        val calendarDataModel = CalendarDataModel(
            events = validEvents + invalidEvent
        )

        // WHEN
        val result = sut.toCalendar(calendarDataModel)

        // THEN
        assertEquals(
            GetCalendarFailure.InvalidCalendar,
            result.getFailureValueOrThrow()
        )
    }
    // endregion

    // region toEventOrNUll
    @Test
    fun `GIVEN valid event WHEN toEventOrNUll THEN it returns non-null event`() {
        // GIVEN
        val validEvent = Random.nextDataEvent()

        // WHEN
        val result = sut.toEventOrNUll(validEvent)

        // THEN
        assertNotNull(result)
    }

    @Test
    fun `GIVEN invalid event WHEN toEventOrNUll THEN it returns null event`() {
        // GIVEN
        val invalidEvent = listOf(
            Random.nextDataEvent(
                leftTeamScore = Random.nextInt(-5, 0)
            ),
            Random.nextDataEvent(
                rightTeamScore = Random.nextInt(-5, 0)
            )
        ).random()

        // WHEN
        val result = sut.toEventOrNUll(invalidEvent)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN event with teams scores equals WHEN toEventOrNUll THEN it returns event with Draw Result`() {
        // GIVEN
        val score = Random.nextInt(0, 5)
        val event = Random.nextDataEvent(
            leftTeamScore = score,
            rightTeamScore = score,
        )

        // WHEN
        val result = sut.toEventOrNUll(event)

        // THEN
        with(sut) {
            val teams = listOf(
                event.leftTeamResult.toTeam(isLeftTeam = true),
                event.rightTeamResult.toTeam(isLeftTeam = false),
            )

            assertEquals(
                result!!.result, EventResult.Draw(
                    teams = teams,
                )
            )
        }
    }

    @Test
    fun `GIVEN event with leftTeam wins WHEN toEventOrNUll THEN it returns event with ValidWin Result`() {
        // GIVEN
        val leftScore = Random.nextInt(1, 5)
        val rightScore = leftScore - 1
        val event = Random.nextDataEvent(
            leftTeamScore = leftScore,
            rightTeamScore = rightScore,
        )

        // WHEN
        val result = sut.toEventOrNUll(event)

        // THEN
        with(sut) {
            assertEquals(
                result!!.result,
                EventResult.ValidWin(
                    winningTeam = event.leftTeamResult.toTeam(isLeftTeam = true),
                    losingTeam = event.rightTeamResult.toTeam(isLeftTeam = false),
                )
            )
        }
    }

    @Test
    fun `GIVEN event with rightTeam wins WHEN toEventOrNUll THEN it returns event with ValidWin Result`() {
        // GIVEN
        val rightScore = Random.nextInt(1, 5)
        val leftScore = rightScore - 1
        val event = Random.nextDataEvent(
            leftTeamScore = leftScore,
            rightTeamScore = rightScore,
        )

        // WHEN
        val result = sut.toEventOrNUll(event)

        // THEN
        with(sut) {
            assertEquals(
                result!!.result,
                EventResult.ValidWin(
                    winningTeam = event.rightTeamResult.toTeam(isLeftTeam = false),
                    losingTeam = event.leftTeamResult.toTeam(isLeftTeam = true),
                )
            )
        }
    }

    @Test
    fun `GIVEN valid event WHEN toEventOrNUll THEN it returns event same date and time`() {
        // GIVEN
        val event = Random.nextDataEvent()

        // WHEN
        val result = sut.toEventOrNUll(event)

        // THEN
        assertEquals(event.stringDate, result!!.stringDate)
        assertEquals(event.stringTime, result.stringTime)
    }
    // endregion

    // region toTeam
    @Test
    fun `GIVEN TeamResult WHEN toTeam THEN it returns same teamName and teamsScore`() {
        // GIVEN
        val teamName = Random.nextString()
        val teamScore = Random.nextInt(0, 5)
        val teamResult = CalendarDataModel.Event.TeamResult(
            teamName = teamName,
            teamScore = teamScore,
        )

        with(sut) {
            // WHEN
            val result = teamResult.toTeam(isLeftTeam = Random.nextBoolean())

            // THEN
            assertEquals(teamName, result.teamName)
            assertEquals(teamScore, result.teamScore)
        }
    }

    @Test
    fun `GIVEN isLeftTeam is true WHEN toTeam THEN it returns TeamSide#LEFT`() {
        // GIVEN
        val teamName = Random.nextString()
        val teamScore = Random.nextInt(0, 5)
        val teamResult = CalendarDataModel.Event.TeamResult(
            teamName = teamName,
            teamScore = teamScore,
        )
        val isLeftTeam = true

        with(sut) {
            // WHEN
            val result = teamResult.toTeam(isLeftTeam = isLeftTeam)

            // THEN
            assertEquals(TeamSide.LEFT, result.teamSide)
        }
    }

    @Test
    fun `GIVEN isLeftTeam is false WHEN toTeam THEN it returns TeamSide#RIGHT`() {
        // GIVEN
        val teamName = Random.nextString()
        val teamScore = Random.nextInt(0, 5)
        val teamResult = CalendarDataModel.Event.TeamResult(
            teamName = teamName,
            teamScore = teamScore,
        )
        val isLeftTeam = false

        with(sut) {
            // WHEN
            val result = teamResult.toTeam(isLeftTeam = isLeftTeam)

            // THEN
            assertEquals(TeamSide.RIGHT, result.teamSide)
        }
    }
    // endregion
}