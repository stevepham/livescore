package com.ht117.data.response

import com.ht117.data.model.Match
import com.ht117.data.model.Team
import kotlinx.serialization.Serializable

@Serializable
data class TeamResponse(
    val teams: List<Team>
)

@Serializable
data class MatchResponseInfo(
    val previous: List<Match>,
    val upcoming: List<Match>
)

@Serializable
data class MatchResponse(
    val matches: MatchResponseInfo
)
