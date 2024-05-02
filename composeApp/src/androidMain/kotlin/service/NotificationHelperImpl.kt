package service

import android.content.Context
import android.widget.Toast

class NotificationHelperImpl(private val context: Context) : NotificationHelper {
    override fun notify(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}