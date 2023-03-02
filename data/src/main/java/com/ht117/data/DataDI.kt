package com.ht117.data

import androidx.room.Room
import com.ht117.data.repo.IMatchRepo
import com.ht117.data.repo.ITeamRepo
import com.ht117.data.repo.MatchRepoImpl
import com.ht117.data.repo.TeamRepoImpl
import com.ht117.data.source.local.AppDB
import com.ht117.data.source.local.MatchLocal
import com.ht117.data.source.remote.MatchRemote
import com.ht117.data.source.remote.Remote
import com.ht117.data.source.remote.TeamRemote
import org.koin.dsl.module

val dataModule = module {
    single { Remote.getClient() }
    single { TeamRemote(get()) }
    single { MatchRemote(get()) }

    single<ITeamRepo> { TeamRepoImpl(get()) }
    single<IMatchRepo> { MatchRepoImpl(get(), get()) }

    single {
        Room.databaseBuilder(get(), AppDB::class.java, "livescore")
            .build()
            .matchDao()
    }
    single { MatchLocal(get()) }
}
