package com.example.newsapp.data.model

data class TopStoriesResponse(
    val results: List<TopStory>
)

data class TopStory(
    val title: String,
    val url: String,
    val multimedia: List<TopMultimedia>?
)

data class TopMultimedia(
    val url: String
)