package com.ht117.livescore.ui.screen.watching

import com.ht117.data.model.Match
import com.ht117.data.repo.IMatchRepo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class WatchingViewModelTest {

    private var mainThread = newSingleThreadContext("UI Thread")

    @MockK
    lateinit var repo: IMatchRepo
    private lateinit var viewModel: WatchingViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(mainThread)
        viewModel = WatchingViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThread.close()
        unmockkAll()
    }

    @Test
    fun removeMatch_test() {
        val match = mockk<Match>()
        val slotMatch = slot<Match>()
        every { repo.removeFavoriteMatch(capture(slotMatch)) } just runs

        viewModel.removeMatch(match)

        verify { repo.removeFavoriteMatch(any()) }
        assert(match == slotMatch.captured)
    }
}
