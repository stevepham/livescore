package com.ht117.livescore.ui.screen.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.UiState
import com.ht117.data.model.Team
import com.ht117.data.repo.ITeamRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class TeamViewModel(private val teamRepo: ITeamRepo) : ViewModel() {
    val uiState: SharedFlow<UiState<List<Team>>>
        get() = teamRepo.getAllTeams()
            .flowOn(Dispatchers.IO)
            .shareIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )

}
