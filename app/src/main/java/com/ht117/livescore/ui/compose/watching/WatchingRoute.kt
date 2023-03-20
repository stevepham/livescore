package com.ht117.livescore.ui.compose.watching

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ht117.data.model.Match
import com.ht117.data.model.UiState
import com.ht117.livescore.ui.compose.component.ShowError
import com.ht117.livescore.ui.compose.component.ShowLoading
import com.ht117.livescore.ui.compose.match.MatchItem
import com.ht117.livescore.ui.screen.watching.WatchingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WatchingRoute(
    controller: NavHostController,
    modifier: Modifier,
    viewModel: WatchingViewModel = koinViewModel()
) {
    when (val state = viewModel.uiState.collectAsState(initial = UiState.Loading).value) {
        is UiState.Loading -> ShowLoading()
        is UiState.Failed -> ShowError(err = state.err)
        is UiState.Result -> WatchingMatchesResult(controller, state.data)
    }
}

@Composable
internal fun WatchingMatchesResult(controller: NavHostController, matches: List<Match>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(matches.size) {
            val match = matches[it]
            MatchItem(match = match) {

            }
        }
    }
}
