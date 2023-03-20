package com.ht117.livescore.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ht117.livescore.R
import com.ht117.livescore.ui.compose.match.AllMatchesRoute
import com.ht117.livescore.ui.compose.match.MatchScreen
import com.ht117.livescore.ui.compose.team.AllTeamsRoute
import com.ht117.livescore.ui.compose.team.TeamScreen
import com.ht117.livescore.ui.compose.watching.WatchingRoute

sealed class Destiny(val route: String, val icon: Int) {
    object TeamDestiny: Destiny("teams", R.drawable.ic_team)
    object MatchDestiny: Destiny("matches", coil.base.R.drawable.ic_100tb)
    object WatchingDestiny: Destiny("watching", R.drawable.ic_favorite)
    object TeamDetailDestiny: Destiny("teams/{id}", -1)
    object MatchDetailDestiny: Destiny("matches/{id}", -1)
}

@Composable
fun LiveGraph(controller: NavHostController, modifier: Modifier) {
    NavHost(navController = controller, startDestination = Destiny.TeamDestiny.route) {
        composable(Destiny.TeamDestiny.route) {
            AllTeamsRoute(controller = controller, modifier = modifier)
        }
        composable(Destiny.MatchDestiny.route) {
            AllMatchesRoute(controller = controller, modifier = modifier)
        }
        composable(Destiny.WatchingDestiny.route) {
            WatchingRoute(controller = controller, modifier = modifier)
        }
        composable(Destiny.TeamDetailDestiny.route) { entry ->
            TeamScreen(
                controller = controller,
                modifier = modifier,
                id = entry.arguments?.getString("id").orEmpty()
            )
        }
        composable(Destiny.MatchDetailDestiny.route) { entry ->
            MatchScreen(
                controller = controller,
                modifier = modifier,
                id = entry.arguments?.getString("id").orEmpty()
            )
        }
    }
}
