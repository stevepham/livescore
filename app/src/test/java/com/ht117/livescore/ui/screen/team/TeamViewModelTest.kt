package com.ht117.livescore.ui.screen.team

import app.cash.turbine.test
import com.ht117.data.model.State
import com.ht117.data.model.Team
import com.ht117.data.repo.ITeamRepo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TeamViewModelTest {

    private var mainThread = newSingleThreadContext("UI Thread")
    @MockK
    lateinit var repo: ITeamRepo
    private lateinit var viewModel: TeamViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(mainThread)
        viewModel = TeamViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun teamViewModel_test() = runTest {
        val teams = mockk<List<Team>>()
        val mockFlow = flowOf(State.Loading, State.Result(teams))

        every { repo.getAllTeams() } returns mockFlow

        viewModel.state.test {
            assert(awaitItem() is State.Loading)
            assert(awaitItem() is State.Result)
        }
    }
}
