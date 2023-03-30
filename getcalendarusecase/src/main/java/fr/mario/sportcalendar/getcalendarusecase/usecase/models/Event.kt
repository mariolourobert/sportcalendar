package fr.mario.sportcalendar.getcalendarusecase.usecase.models

data class Event(
    val stringDate: String,
    val stringTime: String,
    val result: EventResult,
)
