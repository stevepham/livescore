package com.ht117.livescore.ui.screen.match.detail

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.ht117.data.model.Match
import com.ht117.livescore.R
import com.ht117.livescore.databinding.FragmentMatchDetailBinding
import com.ht117.livescore.ext.formatDate
import com.ht117.livescore.ext.viewBinding
import com.ht117.livescore.ui.screen.base.BaseFragment

class MatchDetailFragment: BaseFragment(R.layout.fragment_match_detail) {

    private val match: Match? by lazy {
        arguments?.getParcelable(EXTRA_MATCH)
    }
    private var player: ExoPlayer? = null
    private val binding by viewBinding(FragmentMatchDetailBinding::bind)

    override fun setupToolbar() {
        super.setupToolbar()
        binding.run {
            toolbar.ivBack.setOnClickListener {
                navigateBack()
            }

            toolbar.tvTitle.text = getString(R.string.match_detail)
        }
    }

    override fun setupView() {
        super.setupView()
        binding.run {
            tvHome.text = match?.home
            tvAway.text = match?.away
            tvDate.text = match?.date?.formatDate()
            tvDesc.text = getString(R.string.match_info, match?.description)

            val mediaItem = MediaItem.fromUri(match?.highlights.orEmpty())
            player = ExoPlayer.Builder(requireContext()).build()
            player?.setMediaItem(mediaItem)
            player?.prepare()

            video.player = player
        }
    }

    override fun onPause() {
        super.onPause()
        binding.video.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.video.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.run {
            stop()
            release()
            setVideoSurfaceHolder(null)
        }
        player = null
    }

    companion object {
        const val EXTRA_MATCH = "extra_match"
    }
}
