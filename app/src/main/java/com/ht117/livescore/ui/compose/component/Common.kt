package com.ht117.livescore.ui.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ht117.data.AppErr
import com.ht117.livescore.R
import com.ht117.livescore.ui.compose.Destiny

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

@Composable
fun LiveBottomBar(controller: NavHostController) {
    var selectedTab by rememberSaveable {
        mutableStateOf(0)
    }

    val menus = listOf(
        Destiny.TeamDestiny,
        Destiny.MatchDestiny,
        Destiny.WatchingDestiny
    )
    BottomNavigation {
        menus.forEachIndexed { index, destiny ->
            val isSelected = index == selectedTab
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (index != selectedTab) {
                        selectedTab = index
                        controller.navigate(destiny.route)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = destiny.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    )
                })
        }
    }
}

@Composable
fun TabContent(modifier: Modifier,
               content: @Composable () -> Unit) {
    var selectedTab by rememberSaveable {
        mutableStateOf(0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 8.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.prev_match),
                modifier = Modifier
                    .weight(1F)
                    .wrapContentHeight()
                    .align(CenterVertically)
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                    .border(
                        1.dp,
                        Color.Black,
                        RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    )
                    .background(if (selectedTab == 0) Color.LightGray else Color.White)
                    .padding(vertical = 8.dp)
                    .clickable {
                        if (selectedTab != 0) {
                            selectedTab = 0
                        }
                    },
                color = if (selectedTab == 0) Color.DarkGray else Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.upcoming_match),
                modifier = Modifier
                    .wrapContentHeight()
                    .align(CenterVertically)
                    .weight(1F)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                    .background(if (selectedTab == 1) Color.LightGray else Color.White)
                    .padding(vertical = 8.dp)
                    .clickable {
                        if (selectedTab != 1) {
                            selectedTab = 1
                        }
                    },
                color = if (selectedTab == 1) Color.Gray else Color.Black,
                textAlign = TextAlign.Center
            )
        }

        content.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabRow() {
    TabContent(modifier = Modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(16.dp)
                .height(96.dp)
                .width(96.dp)
        )
    }
}
