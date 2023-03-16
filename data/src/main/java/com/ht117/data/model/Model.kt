package com.ht117.data.model

import android.os.Parcelable
import com.ht117.data.AppErr
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Team(
    val id: String,
    val name: String,
    val logo: String
): Parcelable

@Serializable
@Parcelize
data class Match(
    val date: String,
    val description: String,
    val home: String,
    val away: String,
    val winner: String? = null,
    val highlights: String? = null,
    @Transient
    val isFavorite: Boolean = false
): Parcelable

sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Result<T>(val data: T): UiState<T>()
    data class Failed(val err: AppErr) : UiState<Nothing>()
}
