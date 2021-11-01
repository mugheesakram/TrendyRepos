package com.exercise.trendyrepos.ui.dashboard.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exercise.trendyrepos.data.dto.Repo
import com.exercise.trendyrepos.databinding.ItemRepoBinding
import com.exercise.trendyrepos.utils.extension.toGone
import com.exercise.trendyrepos.utils.extension.toVisible

class RepositoryItemViewHolder(private val itemBinding: ItemRepoBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    var isExpanded = false
    fun bind(item: Repo) {
        Glide.with(itemBinding.ivProfile)
            .load(item.owner?.avatarUrl)
            .into(itemBinding.ivProfile)
        itemBinding.tvOwnerName.text = item.owner?.login
        itemBinding.tvRepoName.text = item.name
        itemBinding.tvLanguage.text = item.language
        itemBinding.tvStarCount.text = item.stargazersCount.toString()
        itemBinding.tvDescription.text = item.description
        itemBinding.root.setOnClickListener {
            isExpanded = if (isExpanded) {
                itemBinding.tvDescription.toGone()
                itemBinding.llFooter.toGone()
                false

            } else {
                itemBinding.tvDescription.toVisible()
                itemBinding.llFooter.toVisible()
                true
            }
        }
    }
}