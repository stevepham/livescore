package com.ht117.livescore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ht117.data.model.Match
import com.ht117.livescore.R
import com.ht117.livescore.databinding.ItemMatchBinding
import com.ht117.livescore.ext.formatDate

class MatchDetailAdapter(
    max: Int = Int.MAX_VALUE,
    private val listener: ((Match) -> Unit)? = null,
    private val extraListener: ((Match) -> Boolean)? = null
) : BaseAdapter<Match, MatchDetailAdapter.Holder>(mutableListOf(), max) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(items[position])
    }

    inner class Holder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Match) {
            binding.run {
                tvHome.text = if (item.home == item.winner) {
                    root.context.getString(R.string.win_team, item.home)
                } else {
                    item.home
                }
                tvAway.text = if (item.away == item.winner) {
                    root.context.getString(R.string.win_team, item.away)
                } else {
                    item.away
                }
                tvDate.text = item.date.formatDate()

                root.setOnClickListener {
                    listener?.invoke(item)
                }

                root.setOnLongClickListener {
                    if (item.highlights.isNullOrEmpty()) {
                        extraListener?.invoke(item) ?: false
                    } else {
                        false
                    }
                }
            }
        }
    }
}
