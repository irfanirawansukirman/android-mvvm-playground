package com.irfanirawansukirman.mvvmplayground

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopHeadlinesResponse(
    @SerialName("articles")
    val articles: List<Article>?,
    @SerialName("status")
    val status: String?,
    @SerialName("totalResults")
    val totalResults: Int?
) {

    data class UINews(val id: String, val title: String)
    fun getTitles(): List<UINews> {
        val data = mutableListOf<UINews>().apply {
            articles?.forEachIndexed { i, item ->
                val data = UINews(
                    id = item.source?.id.orDefault("$i"),
                    title = item.title.orDefault("some title")
                )
                add(data)
            }
        }

        return data
    }
}

@Serializable
data class Article(
    @SerialName("author")
    val author: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("source")
    val source: Source?,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("urlToImage")
    val urlToImage: String?
)

@Serializable
data class Source(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)