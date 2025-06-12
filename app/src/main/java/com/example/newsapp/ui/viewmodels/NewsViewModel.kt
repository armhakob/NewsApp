package com.example.newsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.RetrofitInstance
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.TopStory
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val apiKey = "xPrwW5lZFT7cx9F0ljJKFvngygdNKGwf"

    private val _hotStory = MutableLiveData<TopStory?>()

    val hotStory: LiveData<TopStory?> = _hotStory

    private val _latestNews = MutableLiveData<List<Article>>()
    val latestNews: LiveData<List<Article>> = _latestNews

    fun fetchHotStory() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getTopStories(apiKey)
                _hotStory.value = result.results.firstOrNull()
            } catch (e: Exception) {
                _hotStory.value = null
            }
        }
    }
    fun fetchLatest(query: String = "technology") {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.searchArticles(query, apiKey)
                val articles = result.response?.docs

                if (articles.isNullOrEmpty()) {
                    println("API call succeeded but no articles found.")
                } else {
                    println("Articles received: ${articles.size}")
                }

                _latestNews.value = articles ?: emptyList()
            } catch (e: Exception) {
                println("API call failed: ${e.message}")
                _latestNews.value = emptyList()
            }
        }
    }


//    fun fetchLatest(query: String = "technology") {
//        viewModelScope.launch {
//            try {
//                val result = RetrofitInstance.api.searchArticles(query, apiKey)
//                Log.d("NewsViewModel", "API Success: ${result.response?.docs?.size} articles")
//                _latestNews.value = result.response?.docs ?: emptyList()
//            } catch (e: Exception) {
//                Log.e("NewsViewModel", "API Error", e)
//                _latestNews.value = emptyList()
//            }
//        }
//    }

}