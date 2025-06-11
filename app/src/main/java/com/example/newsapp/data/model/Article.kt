package com.example.newsapp.data.model

data class ArticleResponse(
    val response: ArticleDocs?
)

data class ArticleDocs(
    val docs: List<Article>
)

data class Article(
    val headline: Headline?,
    val multimedia: List<Multimedia>?,
    val web_url: String
)

data class Headline(
    val main: String?
)

data class Multimedia(
    val url: String
)