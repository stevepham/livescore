package com.ht117.livescore.ui.screen.watching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.Match
import com.ht117.data.model.State
import com.ht117.data.repo.IMatchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class WatchingViewModel(private val matchRepo: IMatchRepo): ViewModel() {

    val state: SharedFlow<State<List<Match>>>
        get() = matchRepo.getFavoritesMatches()
            .flowOn(Dispatchers.IO)
            .shareIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                replay = 1
            )

    fun removeMatch(match: Match) = viewModelScope.launch(Dispatchers.IO) {
        matchRepo.removeFavoriteMatch(match)
    }
}
