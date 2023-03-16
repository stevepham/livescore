package com.ht117.livescore.ui.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ht117.data.AppErr
import com.ht117.data.model.Match
import com.ht117.data.model.Team
import com.ht117.livescore.R
import com.ht117.livescore.ext.formatDate

@Composable
fun ShowLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier)
    }
}

@Composable
fun ShowError(modifier: Modifier = Modifier, err: AppErr) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val msg = when (err) {
            is AppErr.LostNetworkErr -> stringResource(id = R.string.err_no_network)
            is AppErr.NetworkErr -> err.message
            else -> stringResource(id = R.string.err_unknown)
        }
        Text(text = msg, color = Color.Red, fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MatchItem(match: Match, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = {
            onClick.invoke()
        }, modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = match.home, modifier = modifier
                    .weight(1F)
                    .width(110.dp)
                    .wrapContentHeight()
                    .padding(start = 8.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.weight(1F)
            ) {
                Text(
                    text = "VS",
                    modifier = modifier
                        .padding(top = 8.dp)
                        .weight(1F)
                        .wrapContentHeight()
                )
                Text(
                    text = match.date.formatDate(),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .weight(1F)
                        .wrapContentHeight()
                )
            }

            Text(
                text = match.away,
                modifier = modifier
                    .weight(1F)
                    .width(110.dp)
                    .wrapContentHeight()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamItem(team: Team, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = { onClick.invoke() },
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = team.logo,
                contentDescription = "",
                modifier = modifier
                    .width(90.dp)
                    .fillMaxHeight()
                    .background(Color.Blue)
            )
            Text(
                modifier = modifier
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                text = team.name,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
