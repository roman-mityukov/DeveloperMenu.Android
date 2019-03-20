package com.actonica.devmenu.logger

import org.apache.logging.log4j.Level

sealed class LoggerModel {
    data class LoggerState(val logLevel: Level, val isLogInFileEnabled: Boolean) :
        LoggerModel() {
        override fun toString(): String {
            return "Logger Log4j2" +
                    "\nlogLevel = ${this.logLevel}" +
                    "\nisLogInFileEnabled = ${this.isLogInFileEnabled}"
        }
    }

    class DeletingComplete : LoggerModel()
    class DeletingFailed : LoggerModel()
}