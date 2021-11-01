package com.exercise.trendyrepos.ui.dashboard.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exercise.trendyrepos.data.dto.Repo
import com.exercise.trendyrepos.databinding.ItemRepoBinding

class RepositoryAdapter : RecyclerView.Adapter<RepositoryItemViewHolder>() {
    private var list: MutableList<Repo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {

        val itemBinding =
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepositoryItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Repo>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }
}