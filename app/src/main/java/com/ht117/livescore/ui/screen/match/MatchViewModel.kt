package com.ht117.livescore.ui.screen.match

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

class MatchViewModel(private val matchRepo: IMatchRepo): ViewModel() {

    val uiState: SharedFlow<UiState<MatchResponseInfo>>
        get() = matchRepo.getAllMatches()
            .flowOn(Dispatchers.IO)
            .shareIn(
                viewModelScope,
                started = SharingStarted.Eagerly
            )
}
