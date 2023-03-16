package com.ht117.livescore.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ht117.livescore.R
import com.ht117.livescore.ext.findActivity
import com.ht117.livescore.ui.compose.match.AllMatchesScreen
import com.ht117.livescore.ui.compose.match.MatchScreen
import com.ht117.livescore.ui.compose.team.AllTeamScreen
import com.ht117.livescore.ui.compose.team.TeamScreen
import com.ht117.livescore.ui.compose.watching.WatchingScreen
import kotlinx.coroutines.selects.whileSelect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LiveTheme() {
    val darkTheme = isSystemInDarkTheme()
//    val colorScheme = if (!darkTheme) lightColorScheme() else darkColorScheme()
    val view = LocalView.current
    val context = LocalContext.current
//    SideEffect {
//        WindowCompat.getInsetsController(context.findActivity().window, view).isAppearanceLightStatusBars = !darkTheme
//    }

    val controller = rememberNavController()
    var selectedTab by remember {
        mutableStateOf(0)
    }
    var items = listOf(
        R.drawable.ic_team,
        coil.base.R.drawable.ic_100tb,
        R.drawable.ic_favorite
    )

    Scaffold(bottomBar = {
        BottomNavigation {
            items.forEachIndexed { index, icon ->
                val isSelected = index == selectedTab
                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            selectedTab = index
                            when (index) {
                                0 -> controller.navigate("teams")
                                1 -> controller.navigate("matches")
                                else -> controller.navigate("watching")
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "",
                            modifier = Modifier
                                .width(32.dp)
                                .height(32.dp)
                        )
                    }
                )
            }
        }
    }) {
        val modifier = Modifier.padding(it)
        LiveGraph(controller, modifier)
    }
}

@Composable
fun LiveGraph(controller: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    NavHost(navController = controller, startDestination = "teams") {
        composable("teams") {
            AllTeamScreen(controller = controller, modifier = modifier)
        }
        composable("matches") {
            AllMatchesScreen(controller = controller, modifier = modifier)
        }
        composable("team/{teamId}") { entry ->
            TeamScreen(
                controller = controller,
                modifier = modifier,
                id = entry.arguments?.getString("teamId").orEmpty()
            )
        }
        composable("match/{matchId}") { entry ->
            MatchScreen(
                controller = controller,
                modifier = modifier,
                id = entry.arguments?.getString("matchId").orEmpty()
            )
        }
        composable("watching") {
            WatchingScreen(controller = controller, modifier = modifier)
        }
    }
}
