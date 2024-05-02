import database.AppDatabase

expect class Factory {
    fun createAppDatabase(): AppDatabase

    companion object {
        val instance: Factory
    }
}