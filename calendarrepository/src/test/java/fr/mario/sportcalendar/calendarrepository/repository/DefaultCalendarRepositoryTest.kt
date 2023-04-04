package fr.mario.sportcalendar.calendarrepository.repository

import fr.mario.sportcalendar.calendarrepository.datasource.CalendarApi
import fr.mario.sportcalendar.calendarrepository.datasource.models.CalendarApiResponse
import fr.mario.sportcalendar.calendarrepository.repository.mappers.CalendarApiResponseMapper
import fr.mario.sportcalendar.calendarrepository.repository.models.GetCalendarDataFailure
import fr.mario.sportcalendar.calendarrepository.repository.random.nextCalendarApiResponse
import fr.mario.sportcalendar.calendarrepository.repository.random.nextCalendarDataModel
import fr.mario.sportcalendar.testtools.nextString
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultCalendarRepositoryTest {
    private val mockCalendarApi = mockk<CalendarApi> {
        coEvery { getCalendar() } returns Response.success(Random.nextCalendarApiResponse())
    }

    private val mockCalendarApiResponseMapper = mockk<CalendarApiResponseMapper> {
        every { toCalendarDataModel(any()) } returns Random.nextCalendarDataModel()
    }

    private val testDispatcher = StandardTestDispatcher()

    private val sut = DefaultCalendarRepository(
        calendarApi = mockCalendarApi,
        calendarApiResponseMapper = mockCalendarApiResponseMapper,
        ioDispatcher = testDispatcher,
    )

    @Test
    fun `WHEN getCalendar THEN api is called`() = runTest(testDispatcher) {
        // WHEN
        sut.getCalendar()

        // THEN
        coVerify {
            mockCalendarApi.getCalendar()
        }
    }

    @Test
    fun `GIVEN api returns success WHEN getCalendar THEN mapper is called AND repo returns mapped value`() =
        runTest(testDispatcher) {
            // GIVEN
            val calendarApiResponse = Random.nextCalendarApiResponse()
            coEvery {
                mockCalendarApi.getCalendar()
            } returns Response.success(calendarApiResponse)

            val calendarMappedValue = Random.nextCalendarDataModel()
            every {
                mockCalendarApiResponseMapper.toCalendarDataModel(any())
            } returns calendarMappedValue

            // WHEN
            val result = sut.getCalendar()

            // THEN
            coVerify {
                mockCalendarApiResponseMapper.toCalendarDataModel(calendarApiResponse)
            }
            assertEquals(calendarMappedValue, result.getSuccessValueOrThrow())
        }

    @Test
    fun `GIVEN api returns failure with http status code WHEN getCalendar THEN repo returns Network failure with status code`() =
        runTest(testDispatcher) {
            // GIVEN
            val httpStatusCode = Random.nextInt(400, 600)
            coEvery {
                mockCalendarApi.getCalendar()
            } returns mockk {
                every { isSuccessful } returns false
                every { code() } returns httpStatusCode
            }

            // WHEN
            val result = sut.getCalendar()

            // THEN
            assertEquals(
                GetCalendarDataFailure.Network(httpStatusCode),
                result.getFailureValueOrThrow()
            )
        }

    @Test
    fun `GIVEN api returns success but null body WHEN getCalendar THEN repo returns Network failure`() =
        runTest(testDispatcher) {
            // GIVEN
            val httpStatusCode = Random.nextInt(200, 300)
            coEvery {
                mockCalendarApi.getCalendar()
            } returns mockk {
                every { isSuccessful } returns true
                every { code() } returns httpStatusCode
                every { body() } returns null
            }

            // WHEN
            val result = sut.getCalendar()

            // THEN
            assertEquals(
                GetCalendarDataFailure.Network(httpStatusCode),
                result.getFailureValueOrThrow()
            )
        }

    @Test
    fun `GIVEN api throws IOException WHEN getCalendar THEN repo returns IO failure`() =
        runTest(testDispatcher) {
            // GIVEN
            val exception = IOException(Random.nextString())
            coEvery {
                mockCalendarApi.getCalendar()
            } throws exception

            // WHEN
            val result = sut.getCalendar()

            // THEN
            assertEquals(
                GetCalendarDataFailure.IO(exception),
                result.getFailureValueOrThrow()
            )
        }

    @Test
    fun `GIVEN api throws HttpException WHEN getCalendar THEN repo returns Network failure`() =
        runTest(testDispatcher) {
            // GIVEN
            val httpStatusCode = Random.nextInt(400, 600)
            val response = mockk<Response<CalendarApiResponse>> {
                every { code() } returns httpStatusCode
                every { message() } returns Random.nextString()
            }
            val exception = HttpException(response)
            coEvery {
                mockCalendarApi.getCalendar()
            } throws exception

            // WHEN
            val result = sut.getCalendar()

            // THEN
            assertEquals(
                GetCalendarDataFailure.Network(httpStatusCode),
                result.getFailureValueOrThrow()
            )
        }

    @Test
    fun `GIVEN api throws random exception WHEN getCalendar THEN repo returns Default failure`() =
        runTest(testDispatcher) {
            // GIVEN
            val exception = Exception(Random.nextString())
            coEvery {
                mockCalendarApi.getCalendar()
            } throws exception

            // WHEN
            val result = sut.getCalendar()

            // THEN
            assertEquals(
                GetCalendarDataFailure.Default(exception),
                result.getFailureValueOrThrow()
            )
        }
}