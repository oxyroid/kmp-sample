package api

import androidx.annotation.Keep
import database.entity.Article
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NewsDto(
    val articles: List<ArticleDto> = emptyList(),
    val status: String? = null,
    val totalResults: Int = 0
) {
    @Serializable
    @Keep
    data class ArticleDto(
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val source: SourceDto?,
        val title: String?,
        val url: String?,
        val urlToImage: String?
    ) {
        @Serializable
        @Keep
        data class SourceDto(
            val id: String?,
            val name: String?
        )
    }
}

fun NewsDto.ArticleDto.toArticle(): Article = Article(
    title = title.orEmpty(),
    url = url.orEmpty(),
    urlToImage = urlToImage.toString(),
    author = author.orEmpty(),
    content = content.orEmpty(),
    description = description.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)