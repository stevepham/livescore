package com.ht117.data.source.remote

import com.ht117.data.getRequest
import com.ht117.data.handleError
import com.ht117.data.model.Team
import com.ht117.data.response.TeamResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.flow

class TeamRemote(private val client: HttpClient) {

    fun getAllTeams() = flow {
        getRequest<TeamResponse, List<Team>>(client, TEAM_URL) {
            it.teams
        }
    }.handleError()

    companion object {
        private const val TEAM_URL = "${Remote.Host}/teams"
    }
}
