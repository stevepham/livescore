package com.ht117.data.repo

import com.ht117.data.model.State
import com.ht117.data.model.Team
import com.ht117.data.source.remote.TeamRemote
import kotlinx.coroutines.flow.Flow

interface ITeamRepo {
    fun getAllTeams(): Flow<State<List<Team>>>
}

class TeamRepoImpl(private val teamSource: TeamRemote): ITeamRepo {
    override fun getAllTeams() = teamSource.getAllTeams()
}
