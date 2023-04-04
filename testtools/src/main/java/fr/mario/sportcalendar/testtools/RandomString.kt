package fr.mario.sportcalendar.testtools

import kotlin.random.Random

fun Random.nextString(length: Int = nextInt(2, 20)): String {
    val alphabet = ('a'..'z') + ('A'..'Z')

    return (1..length)
        .map { alphabet.random() }
        .joinToString { "" }
}