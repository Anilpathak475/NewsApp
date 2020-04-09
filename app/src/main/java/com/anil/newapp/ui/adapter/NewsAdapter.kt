package com.anil.newapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anil.newapp.R
import com.anil.newapp.persistance.entitiy.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter : PagedListAdapter<Article, NewsAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    var itemSelected: (selectedArticle: Article) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(vh: ArticleViewHolder, position: Int) {
        getItem(position)?.let {
            vh.itemView.textViewArticleTitle.text = it.title
            vh.itemView.textViewAuther.text = it.author
            Glide.with(vh.itemView)
                .load(it.urlToImage)
                .placeholder(R.drawable.loading)
                .into(vh.itemView.imageViewArticle)
            vh.itemView.setOnClickListener { _ -> itemSelected.invoke(it) }
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

val DIFF_CALLBACK: DiffUtil.ItemCallback<Article> =
    object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }
    }
