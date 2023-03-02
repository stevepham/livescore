package com.ht117.data.source.local

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.ht117.data.entity.MatchEntity
import com.ht117.data.model.Match
import kotlinx.coroutines.flow.map

class MatchLocal(private val dao: MatchDAO) {

    fun getAllMatches() = dao.getAll().map {entities ->
        entities.map { Match(it.date, it.description, it.home, it.away, null, null) }
    }

    fun addFavoriteMatch(match: Match) = try {
        dao.addFavoriteMatch(MatchEntity(match.date, match.description, match.home, match.away))
    } catch (se: SQLiteConstraintException) {
        se.printStackTrace()
    } catch (exp: Exception) {
        exp.printStackTrace()
    }

    fun removeFavoriteMatch(match: Match) = try {
        dao.removeFavoriteMatch(MatchEntity(match.date, match.description, match.home, match.away))
    } catch (se: SQLiteConstraintException) {
        se.printStackTrace()
    } catch (exp: Exception) {
        exp.printStackTrace()
    }
}
