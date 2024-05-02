class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val isFullScreenAllowed: Boolean = true
}

actual fun getPlatform(): Platform = JVMPlatform()