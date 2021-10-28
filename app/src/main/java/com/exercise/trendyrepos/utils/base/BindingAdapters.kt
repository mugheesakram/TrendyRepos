package com.exercise.trendyrepos.utils.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object BindingAdapters {
    @BindingAdapter(value = ["setAdapter"])
    @JvmStatic
    fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }
}