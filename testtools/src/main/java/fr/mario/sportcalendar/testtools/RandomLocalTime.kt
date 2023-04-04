package fr.mario.sportcalendar.testtools

import java.time.LocalTime
import kotlin.random.Random

fun Random.nextLocalTime(
    hour: Int = nextInt(0, 24),
    minute: Int = nextInt(0, 60),
    second: Int = nextInt(0, 60),
): LocalTime =
    LocalTime.of(hour, minute, second)