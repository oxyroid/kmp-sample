package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    val title: String,
    val url: String,
    val urlToImage: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)