package com.ht117.livescore.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ht117.data.response.MatchResponseInfo
import com.ht117.livescore.ui.screen.match.list.ListMatchFragment

class MatchAdapter(fragment: Fragment,
                   private val data: MatchResponseInfo): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            ListMatchFragment.newInstance(data.previous)
        } else {
            ListMatchFragment.newInstance(data.upcoming)
        }
    }
}
