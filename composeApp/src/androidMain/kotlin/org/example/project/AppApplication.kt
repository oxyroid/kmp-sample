package org.example.project

import android.app.Application

class AppApplication : Application() {
    init {
        application = this
    }

    companion object {
        lateinit var application: AppApplication
    }
}