package com.ht117.data.source.remote

import com.ht117.data.getRequest
import com.ht117.data.handleError
import com.ht117.data.response.MatchResponse
import com.ht117.data.response.MatchResponseInfo
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.flow

class MatchRemote(private val client: HttpClient) {
    fun getAllMatches() = flow {
        getRequest<MatchResponse, MatchResponseInfo>(client, MATCH_URL) {
            it.matches
        }
    }.handleError()

    fun getMatchesOfTeam(teamId: String) = flow {
        getRequest<MatchResponse, MatchResponseInfo>(client, "${MATCH_OF_TEAM_URL}/$teamId/matches") {
            it.matches
        }
    }.handleError()

    companion object {
        private const val MATCH_URL = "${Remote.Host}/teams/matches"
        private const val MATCH_OF_TEAM_URL = "${Remote.Host}/teams"
    }
}

