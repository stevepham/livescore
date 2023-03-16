package com.ht117.livescore.ui.screen.team.detail

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.ht117.data.model.UiState
import com.ht117.data.model.Team
import com.ht117.data.response.MatchResponseInfo
import com.ht117.livescore.R
import com.ht117.livescore.adapter.MatchAdapter
import com.ht117.livescore.databinding.FragmentTeamDetailBinding
import com.ht117.livescore.ext.viewBinding
import com.ht117.livescore.ui.screen.base.BaseFragment
import com.ht117.livescore.ui.screen.base.IView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TeamDetailFragment: BaseFragment(R.layout.fragment_team_detail), IView<MatchResponseInfo> {

    private val team: Team? by lazy {
        arguments?.getParcelable(EXTRA_TEAM)
    }
    private var adapter: MatchAdapter? = null
    private val viewModel: TeamDetailViewModel by viewModel { parametersOf(team?.id.orEmpty()) }
    private val binding by viewBinding(FragmentTeamDetailBinding::bind)

    override fun setupView() {
        super.setupView()
        binding.run {
            toolbar.tvTitle.text = team?.name
            toolbar.ivBack.setOnClickListener {
                navigateBack()
            }
        }
    }

    override fun handleData() {
        super.handleData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect {
                    render(it)
                }
            }
        }
    }

    override fun render(uiState: UiState<MatchResponseInfo>) {
        binding.run {
            when (uiState) {
                UiState.Loading -> {
                    loader.isVisible = true
                    tabHeader.isGone = true
                    pager.isGone = true
                }
                is UiState.Failed -> {
                    loader.isGone = true
                }
                is UiState.Result -> {
                    tabHeader.isVisible = true
                    pager.isVisible = true
                    loader.isGone = true

                    adapter = MatchAdapter(this@TeamDetailFragment, uiState.data)
                    pager.adapter = adapter
                    TabLayoutMediator(tabHeader, pager) { tab, pos ->
                        tab.text = if (pos == 0) {
                            getString(R.string.prev_match)
                        } else {
                            getString(R.string.upcoming_match)
                        }
                    }.attach()
                }
                else -> {}
            }
        }
    }

    override fun onStop() {
        binding.pager.adapter = null
        super.onStop()
    }

    companion object {
        const val EXTRA_TEAM = "extra_team"
    }
}
