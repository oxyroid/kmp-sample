import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.AppDatabase
import service.NotificationHelper

expect class Factory {
    fun createAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

    fun createNotificationHelper(): NotificationHelper

    companion object {
        val instance: Factory
    }
}

val AppDatabase: AppDatabase by lazy {
    Factory.instance
        .createAppDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .fallbackToDestructiveMigration(true)
        .build()
}

val NotificationHelper: NotificationHelper by lazy {
    Factory.instance
        .createNotificationHelper()
}