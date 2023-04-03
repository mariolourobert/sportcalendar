package fr.mario.sportcalendar.calendarfeature.calendarscreen

data class CalendarScreenUiModel(
    val events: List<Event>,
) {
    data class Event(
        val formattedDate: String,
        val formattedTime: String,
        val teams: List<Team>,
    ) {
        data class Team(
            val teamName: String,
            val teamScore: String,
            val teamScoreState: ScoreState,
        )
        enum class ScoreState {
            WIN,
            LOSS,
            DRAW,
        }
    }
}
