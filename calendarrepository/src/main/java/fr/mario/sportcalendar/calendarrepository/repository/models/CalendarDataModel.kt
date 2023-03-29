package fr.mario.sportcalendar.calendarrepository.repository.models

data class CalendarDataModel(
    val events: List<Event>,
) {
    data class Event(
        val leftTeamResult: TeamResult,
        val rightTeamResult: TeamResult,
        val stringDate: String,
        val stringTime: String,
    ) {
        data class TeamResult(
            val teamName: String,
            val teamScore: Int,
        )
    }
}
