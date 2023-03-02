package com.ht117.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["date", "home", "away"])
data class MatchEntity(
    val date: String,
    val description: String,
    val home: String,
    val away: String
)
