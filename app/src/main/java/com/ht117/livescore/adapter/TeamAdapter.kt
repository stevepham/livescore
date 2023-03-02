package com.ht117.livescore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ht117.data.model.Team
import com.ht117.livescore.databinding.ItemTeamBinding

class TeamAdapter(private val listener: ((Team) -> Unit)? = null) :
    BaseAdapter<Team, TeamAdapter.Holder>(mutableListOf()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(items[position])
    }

    inner class Holder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Team) {
            binding.run {
                tvName.text = item.name
                ivLogo.load(item.logo)

                root.setOnClickListener {
                    listener?.invoke(item)
                }
            }
        }
    }
}
