package com.ht117.livescore.ui.screen.team.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.UiState
import com.ht117.data.repo.IMatchRepo
import com.ht117.data.response.MatchResponseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn


class TeamDetailViewModel(
    private val teamID: String,
    private val matchRepo: IMatchRepo): ViewModel() {

    val uiState: SharedFlow<UiState<MatchResponseInfo>>
        get() = matchRepo.getMatchesOfTeam(teamID)
            .flowOn(Dispatchers.IO)
            .shareIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
}
