package com.actonica.devmenu.logger

import android.annotation.SuppressLint
import android.content.SharedPreferences
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.Logger
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.appender.RollingFileAppender
import org.apache.logging.log4j.core.filter.ThresholdFilter

internal class LoggerStateHandler(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val keyIsLogInFileEnabled: String = "dmIsLogInFileEnabled"
        const val keyLogLevel: String = "dmKeyLogLevel"
    }

    fun init() {
        val level = try {
            Level.valueOf(this.levelName)
        } catch (e: Exception) {
            Level.ALL
        }

        this.logLevel = level

        updateRollingFileAppenders()
    }

    private val levelName: String?
        get() {
            return this.sharedPreferences.getString(
                keyLogLevel,
                null
            )
        }

    var isLogInFileEnabled: Boolean
        get() {
            return this.sharedPreferences.getBoolean(
                keyIsLogInFileEnabled,
                false
            )
        }
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences.edit()
                .putBoolean(
                    keyIsLogInFileEnabled,
                    value
                )
                .commit()
            updateRollingFileAppenders()
        }

    var logLevel: Level
        get() {
            return try {
                Level.valueOf(this.levelName)
            } catch (e: Exception) {
                Level.ALL
            }
        }
        set(value) {
            sharedPreferences.edit()
                .putString(
                    keyLogLevel,
                    value.name()
                )
                .apply()

            val loggerContext = LogManager.getContext(false) as LoggerContext
            val config = loggerContext.configuration
            val loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)
            loggerConfig.level = value
            loggerContext.updateLoggers()
        }

    private fun updateRollingFileAppenders() {
        val rootLogger: Logger = LogManager.getRootLogger() as Logger
        val rollingFileAppenders =
            rootLogger.appenders.filter { it.value is RollingFileAppender }
        rollingFileAppenders.forEach {
            val rollingFileAppender: RollingFileAppender? = it.value as? RollingFileAppender

            rollingFileAppender?.let {
                rollingFileAppender.removeFilter(rollingFileAppender.filter)

                val filter: ThresholdFilter = if (this@LoggerStateHandler.isLogInFileEnabled) {
                    ThresholdFilter.createFilter(
                        Level.ALL,
                        Filter.Result.ACCEPT,
                        Filter.Result.DENY
                    )
                } else {
                    ThresholdFilter.createFilter(
                        Level.ALL,
                        Filter.Result.DENY,
                        Filter.Result.DENY
                    )
                }

                rollingFileAppender.addFilter(filter)
            }
        }
    }
}