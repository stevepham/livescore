package com.ht117.livescore.ui.compose.team

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ht117.data.model.Team
import com.ht117.data.model.UiState
import com.ht117.livescore.ui.compose.component.ShowError
import com.ht117.livescore.ui.compose.component.ShowLoading
import com.ht117.livescore.ui.compose.component.TeamItem
import com.ht117.livescore.ui.screen.team.TeamViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllTeamScreen(controller: NavHostController = rememberNavController(), modifier: Modifier = Modifier, viewModel: TeamViewModel = koinViewModel()) {
    when(val state = viewModel.uiState.collectAsState(initial = UiState.Loading).value) {
        is UiState.Loading -> ShowLoading(modifier)
        is UiState.Failed -> ShowError(modifier, err = state.err)
        is UiState.Result -> {
            AllTeamResult(controller, modifier, state)
        }
        else -> {}
    }
}

@Composable
fun AllTeamResult(controller: NavHostController = rememberNavController(), modifier: Modifier = Modifier, state: UiState.Result<List<Team>>) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(state.data.size) {
            val team = state.data[it]
            TeamItem(team = team) {
                controller.navigate("team/${team.id}")
            }
        }
    }
}
