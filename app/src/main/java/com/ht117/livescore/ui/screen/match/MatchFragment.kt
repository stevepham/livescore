package com.ht117.livescore.ui.screen.match

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.ht117.data.model.State
import com.ht117.data.response.MatchResponseInfo
import com.ht117.livescore.R
import com.ht117.livescore.adapter.MatchAdapter
import com.ht117.livescore.databinding.FragmentMatchBinding
import com.ht117.livescore.ext.viewBinding
import com.ht117.livescore.ui.screen.base.BaseFragment
import com.ht117.livescore.ui.screen.base.IView
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchFragment: BaseFragment(R.layout.fragment_match), IView<MatchResponseInfo> {

    private var adapter: MatchAdapter? = null
    private val binding by viewBinding(FragmentMatchBinding::bind)
    private val viewModel: MatchViewModel by viewModel()

    override fun handleData() {
        super.handleData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.distinctUntilChanged().collect {
                    render(it)
                }
            }
        }
    }

    override fun render(state: State<MatchResponseInfo>) {
        binding.run {
            when (state) {
                State.Loading -> {
                    tabHeader.isGone = true
                    pager.isGone = true
                    loader.isVisible = true
                }
                is State.Failed -> {
                    loader.isGone = true
                }
                is State.Result -> {
                    tabHeader.isVisible = true
                    pager.isVisible = true
                    loader.isGone = true

                    adapter = MatchAdapter(this@MatchFragment, state.data)
                    pager.adapter = adapter
                    TabLayoutMediator(tabHeader, pager) { tab, pos ->
                        tab.text = if (pos == 0) {
                            getString(R.string.prev_match)
                        } else {
                            getString(R.string.upcoming_match)
                        }
                    }.attach()
                }
            }
        }
    }

    override fun onStop() {
        binding.pager.adapter = null
        super.onStop()
    }
}
