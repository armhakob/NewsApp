package com.example.newsapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.viewmodels.NewsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        val imgHot = findViewById<ImageView>(R.id.imgHotTopic)
        val txtHotTitle = findViewById<TextView>(R.id.txtHotTitle)
        val recycler = findViewById<RecyclerView>(R.id.recyclerLatestNews)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        val editSearch = findViewById<EditText>(R.id.editSearch)

        adapter = NewsAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            viewModel.fetchHotStory()
            viewModel.fetchLatest()
            swipeRefresh.isRefreshing = false
        }

        viewModel.hotStory.observe(this) { story ->
            txtHotTitle.text = story?.title ?: "No hot topic"
            val imgUrl = story?.multimedia?.firstOrNull()?.url
            val fullUrl = if (!imgUrl.isNullOrEmpty()) {
                if (imgUrl.startsWith("http")) imgUrl else "https://www.nytimes.com/$imgUrl"
            } else null

            Glide.with(this)
                .load(fullUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imgHot)
        }

        viewModel.latestNews.observe(this) { articles ->
            adapter.updateData(articles)
        }

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
        })

        // Initial fetch
        viewModel.fetchHotStory()
        viewModel.fetchLatest()
    }
}
