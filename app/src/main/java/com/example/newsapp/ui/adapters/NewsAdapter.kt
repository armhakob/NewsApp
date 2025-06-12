package com.example.newsapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.Article

class NewsAdapter(
    private var fullList: List<Article>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var filteredList = fullList.toMutableList()
    private var onItemClick: ((Article) -> Unit)? = null

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val image: ImageView = itemView.findViewById(R.id.ivNewsImage)
        val description: TextView? = itemView.findViewById(R.id.tvDescription) // optional
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = filteredList[position]

        holder.title.text = article.headline?.main ?: "No title"
//        holder.description?.text = article.snippet ?: "" // Optional if you have this field

        val imgUrl = article.multimedia?.url
        val fullUrl = if (!imgUrl.isNullOrEmpty()) {
            if (imgUrl.startsWith("http")) imgUrl else "https://www.nytimes.com/$imgUrl"
        } else null

        Glide.with(holder.itemView.context)
            .load(fullUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(article)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        fullList = newArticles
        filteredList = newArticles.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isBlank()) {
            fullList.toMutableList()
        } else {
            fullList.filter {
                it.headline?.main?.contains(query, ignoreCase = true) == true
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClick = listener
    }
}
