package com.ht117.data.repo

import com.ht117.data.model.Match
import com.ht117.data.model.UiState
import com.ht117.data.response.MatchResponseInfo
import com.ht117.data.source.local.MatchLocal
import com.ht117.data.source.remote.MatchRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transform

interface IMatchRepo {
    fun getAllMatches(): Flow<UiState<MatchResponseInfo>>

    fun getMatchesOfTeam(teamId: String): Flow<UiState<MatchResponseInfo>>

    fun getFavoritesMatches(): Flow<UiState<List<Match>>>

    fun addFavoriteMatch(match: Match)

    fun removeFavoriteMatch(match: Match)
}

class MatchRepoImpl(private val matchRemote: MatchRemote,
                    private val matchLocal: MatchLocal): IMatchRepo {

    override fun getAllMatches() = matchRemote.getAllMatches()
        .combine(matchLocal.getAllMatches()) { allMatches, favoritesMatches ->
            if (allMatches is UiState.Result) {
                val upComming = allMatches.data.upcoming.map {
                    it.copy(isFavorite = it in favoritesMatches)
                }
                UiState.Result(MatchResponseInfo(allMatches.data.previous, upComming))
            } else {
                allMatches
            }
        }

    override fun getMatchesOfTeam(teamId: String) = matchRemote.getMatchesOfTeam(teamId)

    override fun getFavoritesMatches(): Flow<UiState<List<Match>>> {
        return matchLocal.getAllMatches().transform {
            emit(UiState.Result(it))
        }
    }

    override fun addFavoriteMatch(match: Match) =
        matchLocal.addFavoriteMatch(match)

    override fun removeFavoriteMatch(match: Match) =
        matchLocal.removeFavoriteMatch(match)
}
