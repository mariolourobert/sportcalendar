package fr.mario.sportcalendar.testtools

import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import kotlin.random.Random

fun Random.nextLocalDate(
    year: Int = nextInt(2000, 2050),
    month: Month = Month.of(nextInt(1, 13)),
    day: Int = nextInt(1, YearMonth.of(year, month).lengthOfMonth() + 1),
): LocalDate =
    LocalDate.of(year, month, day)