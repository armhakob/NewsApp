package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.Article

class NewsAdapter(private var fullList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var filteredList = fullList.toMutableList()

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val image: ImageView = itemView.findViewById(R.id.imgNews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = filteredList[position]
        holder.title.text = article.headline?.main
        val imageUrl = article.multimedia?.firstOrNull()?.url
        if (imageUrl != null) {
            Glide.with(holder.itemView)
                .load("https://www.nytimes.com/$imageUrl")
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder)
        }
    }

    override fun getItemCount(): Int = filteredList.size

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

    fun updateData(newArticles: List<Article>) {
        fullList = newArticles
        filteredList = newArticles.toMutableList()
        notifyDataSetChanged()
    }
}
