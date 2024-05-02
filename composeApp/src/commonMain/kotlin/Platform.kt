interface Platform {
    val name: String
    val isFullScreenAllowed: Boolean
}

expect fun getPlatform(): Platform