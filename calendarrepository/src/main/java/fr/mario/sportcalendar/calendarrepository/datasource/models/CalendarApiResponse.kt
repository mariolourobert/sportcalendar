package fr.mario.sportcalendar.calendarrepository.datasource.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CalendarApiResponse(
    val events: List<Event>,
) {
    @Serializable
    data class Event(
        @SerialName("left_team")
        val leftTeam: Team,
        @SerialName("right_team")
        val rightTeam: Team,
        @SerialName("date")
        val stringDate: String,
        @SerialName("time")
        val stringTime: String,
    ) {
        @Serializable
        data class Team(
            @SerialName("name")
            val name: String,
            @SerialName("score")
            val score: Int,
        )
    }
}
