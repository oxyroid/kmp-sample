import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.AppDatabase
import java.io.File

actual class Factory {
    actual fun createAppDatabase(): AppDatabase {
        val file = File(System.getProperty("java.io.tmpdir"), "my_room.db")
        return Room.databaseBuilder<AppDatabase>(
            name = file.absolutePath
        )
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .fallbackToDestructiveMigration(true)
            .build()
    }

    actual companion object {
        actual val instance: Factory by lazy { Factory() }
    }
}