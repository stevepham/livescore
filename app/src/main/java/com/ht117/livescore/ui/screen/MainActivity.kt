package com.ht117.livescore.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ht117.livescore.R
import com.ht117.livescore.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)

        val controller = findNavController(R.id.nav_host)
        binding?.botNav?.setupWithNavController(controller)

        controller.addOnDestinationChangedListener { _, dest, _ ->
            binding?.botNav?.isVisible = dest.id in listOf(R.id.team, R.id.match, R.id.watching)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
