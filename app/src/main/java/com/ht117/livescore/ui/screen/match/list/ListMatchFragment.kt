package com.ht117.livescore.ui.screen.match.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.view.View.OnClickListener
import androidx.core.os.bundleOf
import com.ht117.data.model.Match
import com.ht117.livescore.R
import com.ht117.livescore.adapter.MatchDetailAdapter
import com.ht117.livescore.databinding.FragmentListMatchBinding
import com.ht117.livescore.ext.viewBinding
import com.ht117.livescore.ui.screen.base.BaseFragment
import com.ht117.livescore.ui.screen.match.detail.MatchDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMatchFragment: BaseFragment(R.layout.fragment_list_match) {

    private val matches: List<Match>? by lazy {
        arguments?.getParcelableArrayList(EXTRA_MATCHES)
    }
    private var adapter: MatchDetailAdapter? = null
    private val viewModel: ListMatchViewModel by viewModel()
    private val binding by viewBinding(FragmentListMatchBinding::bind)

    override fun setupView() {
        super.setupView()
        adapter = MatchDetailAdapter(listener = {
            if (!it.highlights.isNullOrEmpty()) {
                val bundle = bundleOf(MatchDetailFragment.EXTRA_MATCH to it)
                navigate(R.id.move_to_match_detail, bundle)
            }
        }, extraListener = {
            if (!it.isFavorite) {
                val builder = AlertDialog.Builder(requireContext())
                val dialog = builder.setMessage(R.string.add_match_to_favorite)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        viewModel.addToFavorite(it)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }.create()
                dialog.show()
            }
            true
        })

        binding.rvMatches.adapter = adapter
        adapter?.dispatchNewItems(matches.orEmpty())
    }

    override fun onStop() {
        adapter = null
        super.onStop()
    }

    companion object {
        private const val EXTRA_MATCHES = "extra_matches"

        fun newInstance(matches: List<Match>) = ListMatchFragment().apply {
            arguments = bundleOf(EXTRA_MATCHES to matches)
        }
    }
}
