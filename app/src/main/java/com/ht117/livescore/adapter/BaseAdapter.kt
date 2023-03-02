package com.ht117.livescore.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, V : RecyclerView.ViewHolder>(
    protected val items: MutableList<D>,
    private val max: Int = Integer.MAX_VALUE
) : RecyclerView.Adapter<V>() {

    override fun getItemCount() = Math.min(items.size, max)

    fun dispatchNewItems(newItems: List<D>) {
        val differ = Differ(items, newItems)
        val result = DiffUtil.calculateDiff(differ)
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }
}

class Differ<D>(
    private val oldItems: List<D>,
    private val newItems: List<D>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldItems[oldPos] == newItems[newPos]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] === newItems[newItemPosition]
    }
}
