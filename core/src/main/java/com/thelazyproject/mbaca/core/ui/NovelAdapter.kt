package com.thelazyproject.mbaca.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thelazyproject.mbaca.core.databinding.ItemLoadingBinding
import com.thelazyproject.mbaca.core.databinding.ItemNovelBinding
import com.thelazyproject.mbaca.core.domain.model.Novel

class NovelAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var novels = ArrayList<Novel>()
    private var isLoadingMore = false
    var onItemClick: ((Novel) -> Unit)? = null

    companion object {
        private const val VIEW_TYPE_NOVEL = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    fun setData(newNovels: List<Novel>) {
        val diffCallback = NovelDiffCallback(novels, newNovels)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        novels.clear()
        novels.addAll(newNovels)
        diffResult.dispatchUpdatesTo(this)
    }

    fun showLoading() {
        if (!isLoadingMore) {
            isLoadingMore = true
            notifyItemInserted(novels.size)
        }
    }

    fun hideLoading() {
        if (isLoadingMore) {
            isLoadingMore = false
            notifyItemRemoved(novels.size)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < novels.size) VIEW_TYPE_NOVEL else VIEW_TYPE_LOADING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NOVEL) {
            val binding = ItemNovelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NovelViewHolder(binding)
        } else {
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NovelViewHolder) {
            holder.bind(novels[position])
        }
    }

    override fun getItemCount(): Int = novels.size + if (isLoadingMore) 1 else 0

    inner class NovelViewHolder(private val binding: ItemNovelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(novel: Novel) {
            binding.apply {
                tvTitle.text = novel.title
                tvAuthor.text = novel.author
                tvCategory.text = novel.category
                tvRating.text = novel.rating.toString()

                Glide.with(itemView.context)
                    .load(novel.image)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(ivCover)

                root.setOnClickListener {
                    onItemClick?.invoke(novel)
                }
            }
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    class NovelDiffCallback(private val oldList: List<Novel>, private val newList: List<Novel>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

