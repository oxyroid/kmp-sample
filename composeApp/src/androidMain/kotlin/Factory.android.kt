import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import database.AppDatabase
import org.example.project.AppApplication
import service.NotificationHelper
import service.NotificationHelperImpl

actual class Factory(
    private val context: Context
) {
    actual companion object {
        actual val instance: Factory by lazy {
            Factory(AppApplication.application)
        }
    }

    actual fun createAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val applicationContext = context.applicationContext
        val file = applicationContext.getDatabasePath("my_room.db")
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = file.absolutePath
        )
    }

    actual fun createNotificationHelper(): NotificationHelper = NotificationHelperImpl(context)
}
