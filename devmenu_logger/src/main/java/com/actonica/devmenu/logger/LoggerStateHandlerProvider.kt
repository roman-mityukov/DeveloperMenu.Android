package com.actonica.devmenu.logger

import android.content.SharedPreferences

internal class LoggerStateHandlerProvider {
    companion object {
        private var loggerStateHandler: LoggerStateHandler? = null

        fun provideLoggerStateHandler(sharedPreferences: SharedPreferences): LoggerStateHandler {
            if (this.loggerStateHandler == null) {
                this.loggerStateHandler = LoggerStateHandler(sharedPreferences)
            }

            return this.loggerStateHandler!!
        }
    }
}