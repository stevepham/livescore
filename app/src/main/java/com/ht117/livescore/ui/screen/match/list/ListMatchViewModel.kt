package com.ht117.livescore.ui.screen.match.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.Match
import com.ht117.data.repo.IMatchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListMatchViewModel(private val matchRepo: IMatchRepo): ViewModel() {

    fun addToFavorite(match: Match) = viewModelScope.launch(Dispatchers.IO) {
        matchRepo.addFavoriteMatch(match)
    }
}
