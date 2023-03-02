package com.ht117.livescore

import com.ht117.data.dataModule
import com.ht117.livescore.ui.screen.match.MatchViewModel
import com.ht117.livescore.ui.screen.match.list.ListMatchViewModel
import com.ht117.livescore.ui.screen.team.TeamViewModel
import com.ht117.livescore.ui.screen.team.detail.TeamDetailViewModel
import com.ht117.livescore.ui.screen.watching.WatchingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MatchViewModel(get()) }
    viewModel { TeamViewModel(get()) }
    viewModel { parameters -> TeamDetailViewModel(parameters.get(), get()) }
    viewModel { WatchingViewModel(get()) }
    viewModel { ListMatchViewModel(get()) }
}

val allModules = listOf(appModule, dataModule)
