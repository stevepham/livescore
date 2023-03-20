package com.ht117.livescore.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ht117.livescore.ext.findActivity
import com.ht117.livescore.ui.compose.component.LiveBottomBar

@Composable
fun LiveTheme() {
    val darkTheme = isSystemInDarkTheme()
//    val colorScheme = if (!darkTheme) lightColorScheme() else darkColorScheme()
    val view = LocalView.current
    val context = LocalContext.current
    SideEffect {
        WindowCompat.getInsetsController(context.findActivity().window, view).isAppearanceLightStatusBars = !darkTheme
    }

    val controller = rememberNavController()

    Scaffold(
        bottomBar = { LiveBottomBar(controller) },
    ) {
        val modifier = Modifier.padding(it)
        LiveGraph(controller, modifier)
    }
}
