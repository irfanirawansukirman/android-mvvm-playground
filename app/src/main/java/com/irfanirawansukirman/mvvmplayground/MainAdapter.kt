package com.irfanirawansukirman.mvvmplayground

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.irfanirawansukirman.mvvmplayground.databinding.MainItemBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainItemHolder>() {

    private val oldNews = mutableListOf<TopHeadlinesResponse.UINews>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemHolder {
        return MainItemHolder(
            MainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainItemHolder, position: Int) {
        holder.bind(oldNews[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return oldNews.size
    }

    fun submitData(newNews: List<TopHeadlinesResponse.UINews>) {
        val newsDiffCallback = NewsDiffCallback(oldNews, newNews)
        val diffResult = DiffUtil.calculateDiff(newsDiffCallback)
        oldNews.apply {
            clear()
            addAll(newNews)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    class MainItemHolder(private val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TopHeadlinesResponse.UINews) {
            binding.tvTitle.text = item.title
        }
    }

    class NewsDiffCallback(
        private val oldNews: List<TopHeadlinesResponse.UINews>,
        private val newNews: List<TopHeadlinesResponse.UINews>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldNews.size
        }

        override fun getNewListSize(): Int {
            return newNews.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNews[oldItemPosition].id == newNews[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNews[oldItemPosition] == newNews[newItemPosition]
        }
    }
}