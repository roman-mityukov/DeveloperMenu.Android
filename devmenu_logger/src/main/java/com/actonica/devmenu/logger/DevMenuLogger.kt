package com.actonica.devmenu.logger

import android.content.Context
import android.preference.PreferenceManager
import io.rm.log4j2.android.Log4j2Android

class DevMenuLogger {
    companion object {
        fun init(applicationContext: Context, configXmlResource: Int) {
            Log4j2Android.init(applicationContext, configXmlResource)

            val loggerStateHandler = LoggerStateHandlerProvider.provideLoggerStateHandler(
                PreferenceManager.getDefaultSharedPreferences(
                    applicationContext
                )
            )
            loggerStateHandler.init()
        }
    }
}