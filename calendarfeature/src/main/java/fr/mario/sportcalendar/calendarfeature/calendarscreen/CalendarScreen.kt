package fr.mario.sportcalendar.calendarfeature.calendarscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import fr.mario.sportcalendar.calendarfeature.R
import fr.mario.sportcalendar.designsystem.components.MainScaffold
import fr.mario.sportcalendar.designsystem.theme.GreenText
import fr.mario.sportcalendar.designsystem.theme.RedText
import fr.mario.sportcalendar.designsystem.theme.mediumMarginDimen
import fr.mario.sportcalendar.designsystem.theme.smallMarginDimen
import org.koin.androidx.compose.get

@Composable
fun CalendarScreen() {
    MainScaffold(
        title = stringResource(R.string.calendar_screen_title),
    ) {
        val viewModel: CalendarScreenViewModel = get()

        when (val state = viewModel.state.collectAsState().value) {
            is CalendarScreenViewModel.State.Failure ->
                FailureState(state.failureUiModel)
            CalendarScreenViewModel.State.Loading ->
                LoadingState()
            is CalendarScreenViewModel.State.Success ->
                SuccessState(state.calendarUiModel)
        }
    }
}

@Composable
private fun FailureState(failure: CalendarScreenFailureUiModel) {
    val failureMessage = when (failure) {
        CalendarScreenFailureUiModel.InvalidCalendar ->
            stringResource(R.string.invalid_calendar_failure)
        is CalendarScreenFailureUiModel.Network ->
            stringResource(id = R.string.network_failure, failure.httpStatus.toString())
        CalendarScreenFailureUiModel.Unknown ->
            stringResource(R.string.unknown_failure)
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(all = mediumMarginDimen),
        contentAlignment = Alignment.Center,
    ) {
        Text(failureMessage)
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessState(calendar: CalendarScreenUiModel) {
    LazyColumn(
        contentPadding = PaddingValues(all = mediumMarginDimen),
        verticalArrangement = Arrangement.spacedBy(mediumMarginDimen),
    ) {
        items(calendar.events) { event ->
            EventItem(event)
        }
    }
}

@Composable
private fun EventItem(event: CalendarScreenUiModel.Event) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(mediumMarginDimen)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = event.formattedDate, style = MaterialTheme.typography.labelMedium)
                    Text(text = event.formattedTime, style = MaterialTheme.typography.labelSmall)
                }
                Divider(
                    modifier = Modifier.padding(top = smallMarginDimen),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
                Spacer(modifier = Modifier.padding(top = smallMarginDimen))
                event.teams.forEach { team ->
                    TeamItem(team)
                }
            }
        }
    }
}

@Composable
private fun TeamItem(team: CalendarScreenUiModel.Event.Team) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = team.teamName, fontWeight = FontWeight.Medium)
        val teamScoreColor =
            getScoreTextColor(scoreState = team.teamScoreState)
        Text(text = team.teamScore, color = teamScoreColor, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun getScoreTextColor(scoreState: CalendarScreenUiModel.Event.ScoreState): Color =
    when (scoreState) {
        CalendarScreenUiModel.Event.ScoreState.WIN ->
            GreenText
        CalendarScreenUiModel.Event.ScoreState.LOSS ->
            RedText
        CalendarScreenUiModel.Event.ScoreState.DRAW ->
            MaterialTheme.colorScheme.onSecondary
    }