package com.ht117.livescore.ui.screen.match.list

import com.ht117.data.model.Match
import com.ht117.data.repo.IMatchRepo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ListMatchViewModelTest {
    private var mainThread = newSingleThreadContext("UI Thread")

    @MockK
    lateinit var repo: IMatchRepo
    private lateinit var viewModel: ListMatchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(mainThread)
        viewModel = ListMatchViewModel(repo)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
        mainThread.close()
    }

    @Test
    fun addToFavorite_test() {
        val match = mockk<Match>()
        every { repo.addFavoriteMatch(any()) } just runs

        viewModel.addToFavorite(match)

        verify { repo.addFavoriteMatch(match) }
    }
}
