package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.dao.ArticleDao
import database.entity.Article

@Database(
    entities = [Article::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
