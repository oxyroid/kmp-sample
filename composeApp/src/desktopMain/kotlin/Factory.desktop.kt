import androidx.room.Room
import androidx.room.RoomDatabase
import database.AppDatabase
import service.NotificationHelpImpl
import service.NotificationHelper
import java.io.File

actual class Factory {
    actual fun createAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val file = File(System.getProperty("java.io.tmpdir"), "my_room.db")
        return Room.databaseBuilder<AppDatabase>(
            name = file.absolutePath
        )
    }

    actual companion object {
        actual val instance: Factory by lazy { Factory() }
    }

    actual fun createNotificationHelper(): NotificationHelper = NotificationHelpImpl()
}
