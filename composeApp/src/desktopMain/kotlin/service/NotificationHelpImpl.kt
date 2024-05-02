package service

class NotificationHelpImpl : NotificationHelper {
    private val title = "KMP Notification"
    override fun notify(msg: String) {
        val os = System.getProperty("os.name")
        val process = when {
            os.contains("mac", true) -> {
                arrayOf(
                    "osascript",
                    "-e",
                    "display notification \"$msg\" with title \"$title\""
                )
            }

            os.contains("linux", true) -> {
                arrayOf(
                    "zenity",
                    "--notification",
                    "--text=$title\\n$msg"
                )
            }

            else -> emptyArray()
        }

        ProcessBuilder(*process)
            .inheritIO()
            .start()
    }
}