package fr.mario.sportcalendar.calendarfeature.calendarscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.mario.sportcalendar.getcalendarusecase.usecase.GetCalendarUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalendarScreenViewModel(
    private val calendarScreenFailureUiModelMapper: CalendarScreenFailureUiModelMapper,
    private val calendarScreenUiModelMapper: CalendarScreenUiModelMapper,
    private val getCalendarUseCase: GetCalendarUseCase,
) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        viewModelScope.launch {
            _state.emit(State.Loading)
            getCalendarUseCase()
                .mapSuccess(calendarScreenUiModelMapper::toUiModel)
                .mapFailure(calendarScreenFailureUiModelMapper::toUiModel)
                .onSuccess { uiModel ->
                    _state.emit(State.Success(uiModel))
                }.onFailure { uiModel ->
                    _state.emit(State.Failure(uiModel))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Failure(val failureUiModel: CalendarScreenFailureUiModel) : State()
        data class Success(val calendarUiModel: CalendarScreenUiModel) : State()
    }
}