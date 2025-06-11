package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.RetrofitInstance
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.TopStory
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val apiKey = "8Y2yOlbcs4ufHXtCTF6fMaaU8WsMbtAe"

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
                _latestNews.value = result.response?.docs ?: emptyList()
            } catch (e: Exception) {
                _latestNews.value = emptyList()
            }
        }
    }
}