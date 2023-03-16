package com.ht117.livescore.ui.screen.team

import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ht117.data.model.UiState
import com.ht117.data.model.Team
import com.ht117.livescore.R
import com.ht117.livescore.adapter.TeamAdapter
import com.ht117.livescore.databinding.FragmentTeamBinding
import com.ht117.livescore.ext.viewBinding
import com.ht117.livescore.ui.screen.base.BaseFragment
import com.ht117.livescore.ui.screen.base.IView
import com.ht117.livescore.ui.screen.team.detail.TeamDetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamFragment: BaseFragment(R.layout.fragment_team), IView<List<Team>> {

    private val binding by viewBinding(FragmentTeamBinding::bind)
    private val viewModel: TeamViewModel by viewModel()
    private var adapter: TeamAdapter? = null

    override fun setupView() {
        super.setupView()
        adapter = TeamAdapter {
            val bundle = bundleOf(TeamDetailFragment.EXTRA_TEAM to it)
            navigate(R.id.team_to_detail, bundle)
        }

        binding.rvTeam.adapter = adapter
    }

    override fun handleData() {
        super.handleData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.distinctUntilChanged().collectLatest {
                    render(it)
                }
            }
        }
    }

    override fun render(uiState: UiState<List<Team>>) {
        binding.run {
            when (uiState) {
                UiState.Loading -> {
                    rvTeam.isGone = true
                    loader.isVisible = true
                }
                is UiState.Failed -> {
                    loader.isGone = true
                    tvError.isVisible = true
                    tvError.text = processError(uiState.err)
                }
                is UiState.Result -> {
                    rvTeam.isVisible = true
                    loader.isGone = true
                    adapter?.dispatchNewItems(uiState.data)
                }
                else -> {}
            }
        }
    }

    override fun onStop() {
        adapter = null
        super.onStop()
    }
}
