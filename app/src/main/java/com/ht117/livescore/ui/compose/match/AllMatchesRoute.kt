package com.ht117.livescore.ui.compose.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ht117.data.AppErr
import com.ht117.data.model.Match
import com.ht117.data.model.UiState
import com.ht117.livescore.R
import com.ht117.livescore.ui.compose.component.TabContent
import com.ht117.livescore.ui.screen.match.MatchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllMatchesRoute(
    controller: NavHostController,
    modifier: Modifier,
    viewModel: MatchViewModel = koinViewModel()
) {
    var tabIndex by remember {
        mutableStateOf(0)
    }

    val titles = listOf(
        stringResource(id = R.string.prev_match),
        stringResource(id = R.string.upcoming_match)
    )

    when (val state = viewModel.uiState.collectAsState(initial = UiState.Loading).value) {
        is UiState.Loading -> {
            TabContent(modifier) {
                CircularProgressIndicator()
            }
        }

        is UiState.Failed -> {
            TabContent(modifier) {
                Text("Failed")
            }
        }

        is UiState.Result -> {
            TabContent(modifier) {
                Text("Content")
            }
        }
    }
}

@Composable
fun AllMatchesLoading(
    modifier: Modifier = Modifier,
    tabIndex: Int,
    titles: List<String>,
    onTabClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(selectedTabIndex = tabIndex) {
            titles.forEachIndexed { index, title ->
                val isSelected = index == tabIndex
                if (isSelected) {
                    modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                } else {
                    modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xff1e76da))
                }
                Tab(selected = isSelected,
                    modifier = modifier,
                    onClick = { onTabClick.invoke(index) },
                    text = {
                        Text(text = title, color = Color(0xff6FAAEE))
                    }
                )
            }
        }

        CircularProgressIndicator(modifier)
    }
}

@Composable
fun AllMatchesFailed(
    modifier: Modifier = Modifier,
    tabIndex: Int,
    titles: List<String>,
    err: AppErr,
    onTabClick: (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = tabIndex) {
            titles.forEachIndexed { index, title ->
                val isSelected = index == tabIndex
                if (isSelected) {
                    modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                } else {
                    modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xff1e76da))
                }
                Tab(selected = isSelected,
                    modifier = modifier,
                    onClick = { onTabClick.invoke(index) },
                    text = {
                        Text(text = title, color = Color(0xff6FAAEE))
                    }
                )
            }
        }

        val msg = when (err) {
            is AppErr.LostNetworkErr -> stringResource(id = R.string.err_no_network)
            is AppErr.NetworkErr -> err.message
            is AppErr.UnknownErr -> stringResource(id = R.string.err_unknown)
        }
        Text(text = msg)
    }
}

@Composable
fun AllMatchesResult(
    modifier: Modifier,
    tabIndex: Int,
    titles: List<String>,
    matches: List<Match>,
    onTabClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = modifier
                .padding(8.dp)
                .wrapContentHeight()
        ) {
            titles.forEachIndexed { index, title ->
                val isSelected = index == tabIndex
                Tab(selected = isSelected,
                    onClick = { onTabClick.invoke(index) },
                    text = {
                        Text(text = title, color = Color(0xff6FAAEE))
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(matches.size) {
                val match = matches[it]
                MatchItem(match = match) {
                }
            }
        }
    }
}
