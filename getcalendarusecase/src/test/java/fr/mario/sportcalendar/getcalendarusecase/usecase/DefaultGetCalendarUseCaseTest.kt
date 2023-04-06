package fr.mario.sportcalendar.getcalendarusecase.usecase

import fr.mario.sportcalendar.calendarrepository.repository.CalendarRepository
import fr.mario.sportcalendar.commontools.Either
import fr.mario.sportcalendar.getcalendarusecase.usecase.mappers.CalendarDataModelMapper
import fr.mario.sportcalendar.getcalendarusecase.usecase.mappers.GetCalendarDataFailureMapper
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.GetCalendarFailure
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.nextCalendar
import fr.mario.sportcalendar.getcalendarusecase.usecase.models.nextGetCalendarFailure
import fr.mario.sportcalendar.getcalendarusecase.usecase.random.nextCalendarDataModel
import fr.mario.sportcalendar.getcalendarusecase.usecase.random.nextGetCalendarDataFailure
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultGetCalendarUseCaseTest {
    private val mockCalendarRepository = mockk<CalendarRepository> {
        coEvery { getCalendar() } returns Either.Success(Random.nextCalendarDataModel())
    }
    private val mockCalendarDataModelMapper = mockk<CalendarDataModelMapper> {
        every { toCalendar(any()) } returns Either.Success(Random.nextCalendar())
    }
    private val mockGetCalendarDataFailureMapper = mockk<GetCalendarDataFailureMapper>()

    private val sut = DefaultGetCalendarUseCase(
        mockCalendarRepository,
        mockCalendarDataModelMapper,
        mockGetCalendarDataFailureMapper,
    )

    @Test
    fun `WHEN invoke THEN repo is called`() = runTest {
        // WHEN
        sut()

        // THEN
        coVerify {
            mockCalendarRepository.getCalendar()
        }
    }

    @Test
    fun `GIVEN repo returns Success AND mapper returns Success WHEN invoke THEN UC returns mapped Success`() = runTest {
        // GIVEN
        val calendarDataModel = Random.nextCalendarDataModel()
        coEvery {
            mockCalendarRepository.getCalendar()
        } returns Either.Success(calendarDataModel)

        val calendar = Random.nextCalendar()
        every {
            mockCalendarDataModelMapper.toCalendar(any())
        } returns Either.Success(calendar)

        // WHEN
        val result = sut()

        // THEN
        assertEquals(Either.Success(calendar), result)
    }

    @Test
    fun `GIVEN repo returns Success BUT mapper returns Failure WHEN invoke THEN UC returns Failure`() = runTest {
        // GIVEN
        val calendarDataModel = Random.nextCalendarDataModel()
        coEvery {
            mockCalendarRepository.getCalendar()
        } returns Either.Success(calendarDataModel)

        val failure = Random.nextGetCalendarFailure()
        every {
            mockCalendarDataModelMapper.toCalendar(any())
        } returns Either.Failure(failure)

        // WHEN
        val result = sut()

        // THEN
        assertEquals(Either.Failure(failure), result)
    }

    @Test
    fun `GIVEN repo returns Failure WHEN invoke THEN UC returns mapped Failure AND calendar mapper is not called`() = runTest {
        // GIVEN
        val dataFailure = Random.nextGetCalendarDataFailure()
        coEvery {
            mockCalendarRepository.getCalendar()
        } returns Either.Failure(dataFailure)

        val mappedFailure = Random.nextGetCalendarFailure()
        every {
            mockGetCalendarDataFailureMapper.toGetCalendarFailure(any())
        } returns mappedFailure

        // WHEN
        val result = sut()

        // THEN
        assertEquals(Either.Failure(mappedFailure), result)
        verify(exactly = 0) {
            mockCalendarDataModelMapper.toCalendar(any())
        }
    }
}