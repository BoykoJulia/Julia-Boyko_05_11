package com.test.weatherforecast.ui

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<E : Any>(
    private val oldItems: List<E>,
    private val newItems: List<E>,
    private val payload: (oldItem: E, newItem: E) -> Any?,
    private val areItemsTheSame: (oldItem: E, newItem: E) -> Boolean,
    private val areContentTheSame: (oldItem: E, newItem: E) -> Boolean) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return payload(oldItems[oldItemPosition], newItems[newItemPosition]) ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }

}