package com.ht117.livescore.ui.screen.watching

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ht117.data.model.Match
import com.ht117.data.model.State
import com.ht117.livescore.R
import com.ht117.livescore.adapter.MatchDetailAdapter
import com.ht117.livescore.databinding.FragmentWatchingMatchBinding
import com.ht117.livescore.ext.getTime
import com.ht117.livescore.ext.viewBinding
import com.ht117.livescore.receiver.MatchNotifyReceiver
import com.ht117.livescore.ui.screen.base.BaseFragment
import com.ht117.livescore.ui.screen.base.IView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WatchingMatchFragment: BaseFragment(R.layout.fragment_watching_match), IView<List<Match>> {

    private var adapter: MatchDetailAdapter? = null
    private val viewModel: WatchingViewModel by viewModel()
    private val binding by viewBinding(FragmentWatchingMatchBinding::bind)

    override fun setupView() {
        super.setupView()
        adapter = MatchDetailAdapter(listener = {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.create_notify_match)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    val alarmManager =
                        requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    val intent = Intent(requireContext(), MatchNotifyReceiver::class.java)
                    intent.putExtra("match", it)

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    try {
                        alarmManager?.setExact(
                            AlarmManager.RTC_WAKEUP,
                            it.date.getTime(),
                            pendingIntent
                        )
                    } catch (exp: SecurityException) {
                    }
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }.create()
                .show()
        }, extraListener = {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.remove_match_from_watching)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    viewModel.removeMatch(it)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }.create()
                .show()
            true
        })
        binding.run {
            rvMatches.adapter = adapter
        }
    }

    override fun handleData() {
        super.handleData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.distinctUntilChanged().collectLatest {
                    render(it)
                }
            }
        }
    }

    override fun render(state: State<List<Match>>) {
        binding.run {
            when (state) {
                State.Loading -> {
                    rvMatches.isGone = true
                    loader.isVisible = true
                    tvMessage.isGone = true
                }
                is State.Failed -> {
                    rvMatches.isGone = true
                    loader.isVisible = true
                    tvMessage.isVisible = true
                    tvMessage.text = processError(state.err)
                }
                is State.Result -> {
                    rvMatches.isVisible = true
                    loader.isGone = true
                    if (state.data.isNullOrEmpty()) {
                        rvMatches.isGone = true
                        tvMessage.isVisible = true
                        tvMessage.text = getString(R.string.you_have_no_watching_match)
                    } else {
                        tvMessage.isGone = true
                        rvMatches.isVisible = true
                        adapter?.dispatchNewItems(state.data)
                    }
                }
            }
        }
    }

    override fun onStop() {
        adapter = null
        super.onStop()
    }
}
