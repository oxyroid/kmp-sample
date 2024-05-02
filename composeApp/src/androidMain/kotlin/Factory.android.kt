import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.AppDatabase
import org.example.project.AppApplication

actual class Factory(private val context: Context) {
    actual fun createAppDatabase(): AppDatabase {
        val applicationContext = context.applicationContext
        val file = applicationContext.getDatabasePath("my_room.db")
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = file.absolutePath
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    actual companion object {
        actual val instance: Factory by lazy {
            Factory(AppApplication.application)
        }
    }
}