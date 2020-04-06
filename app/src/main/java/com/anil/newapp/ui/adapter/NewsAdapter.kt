package com.anil.newapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anil.newapp.R
import com.anil.newapp.model.Article
import kotlinx.android.synthetic.main.item_news.view.*
import kotlin.properties.Delegates

class NewsAdapter(
    val onSelected: (category: Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.CartViewHolder>() {
    var articals by Delegates.observable(emptyList<Article>()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(vh: CartViewHolder, position: Int) {
        val article = articals[position]
        vh.itemView.textViewNewsTitle.text = article.title
        vh.itemView.setOnClickListener { onSelected(article) }
    }

    override fun getItemCount() = articals.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}