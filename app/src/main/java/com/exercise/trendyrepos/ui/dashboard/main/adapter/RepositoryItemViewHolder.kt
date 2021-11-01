package com.exercise.trendyrepos.ui.dashboard.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.exercise.trendyrepos.data.dto.Repo
import com.exercise.trendyrepos.databinding.ItemRepoBinding

class RepositoryItemViewHolder(private val itemBinding: ItemRepoBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: Repo) {
        itemBinding.tvRepoName.text = item.name
        itemBinding.tvDescription.text = item.description
    }
}