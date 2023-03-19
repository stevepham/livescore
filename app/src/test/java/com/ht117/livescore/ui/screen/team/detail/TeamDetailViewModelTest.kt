package com.ht117.livescore.ui.screen.team.detail

import app.cash.turbine.test
import com.ht117.data.model.State
import com.ht117.data.repo.IMatchRepo
import com.ht117.data.response.MatchResponseInfo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class TeamDetailViewModelTest {

    @MockK
    lateinit var repo: IMatchRepo
    private lateinit var viewModel: TeamDetailViewModel
    private val ID = "ID"

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = TeamDetailViewModel(ID, repo)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun teamViewModel_success() = runTest {
        val response = mockk<MatchResponseInfo>()
        val mockFlow = flowOf(State.Loading, State.Result(response))
        every { repo.getMatchesOfTeam(ID) } returns mockFlow

        viewModel.state.test {
            assert(awaitItem() is State.Loading)
            assert(awaitItem() is State.Result)
        }
    }
}
