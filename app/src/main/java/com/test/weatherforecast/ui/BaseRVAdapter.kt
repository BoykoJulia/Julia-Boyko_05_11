package com.test.weatherforecast.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVAdapter<E : Any, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var items: ArrayList<E>? = null

    open fun setItems(items: List<E>) {
        val diffCallback = BaseDiffUtil(
            this.items ?: emptyList(),
            items,
            ::payload,
            ::areItemTheSame,
            ::areContentTheSame
        )
        val result = DiffUtil.calculateDiff(diffCallback)

        result.dispatchUpdatesTo(this)

        this.items?.clear()

        addItems(items)
    }

    open fun getItem(position: Int) = items?.getOrNull(position)

    open fun getItems() = items

    open fun areItemTheSame(oldItem: E, newItem: E) = false

    open fun areContentTheSame(oldItem: E, newItem: E) = false

    open fun payload(oldItem: E, newItem: E): Any? = null

    open fun clear() {
        items?.clear()
        notifyDataSetChanged()
    }

    fun isEmpty() = items?.isEmpty()

    fun isNotEmpty() = items?.isNotEmpty()

    override fun getItemCount() = items?.size ?: 0

    protected fun inflateView(@LayoutRes layoutId: Int, viewGroup: ViewGroup): View {
        return LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
    }

    open fun addItems(items: List<E>?) {
        items ?: return

        if (this.items == null) this.items = ArrayList()

        this.items?.addAll(items)
    }

    open fun changeItems(newItems: ArrayList<E>?) {
        items = newItems

        notifyDataSetChanged()
    }
}