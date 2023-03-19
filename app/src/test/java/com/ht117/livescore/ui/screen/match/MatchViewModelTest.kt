package com.ht117.livescore.ui.screen.match

import app.cash.turbine.test
import com.ht117.data.model.State
import com.ht117.data.repo.IMatchRepo
import com.ht117.data.response.MatchResponseInfo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MatchViewModelTest {

    private var mainThread = newSingleThreadContext("UI Thread")
    @MockK
    lateinit var repo: IMatchRepo
    private lateinit var viewModel: MatchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(mainThread)

        viewModel = MatchViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThread.close()
    }

    @Test
    fun matchViewModel_test() = runTest {
        val mockResult = mockk<MatchResponseInfo>()
        val mockRemote = flowOf(State.Loading, State.Result(mockResult))

        every { repo.getAllMatches() } returns mockRemote

        viewModel.state.test {
            assert(awaitItem() is State.Loading)
            assert(awaitItem() is State.Result)
        }
    }
}
