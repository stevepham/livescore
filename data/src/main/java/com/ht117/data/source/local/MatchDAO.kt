package com.ht117.data.source.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.ht117.data.entity.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDAO {
    @Query("select * from matchentity")
    fun getAll(): Flow<List<MatchEntity>>

    @Insert
    fun addFavoriteMatch(vararg matches: MatchEntity)

    @Delete
    fun removeFavoriteMatch(match: MatchEntity)
}

@Database(entities = [MatchEntity::class], version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun matchDao(): MatchDAO
}
