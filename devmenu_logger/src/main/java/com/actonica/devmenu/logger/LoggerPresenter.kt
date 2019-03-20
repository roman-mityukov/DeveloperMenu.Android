package com.actonica.devmenu.logger

import org.apache.logging.log4j.Level

internal interface LoggerPresenterInput {
    fun deleteLogs(logFileName: String)
    fun setLogLevel(level: Level)
    fun setIsLoggingInFileEnabled(isLogInFileEnabled: Boolean)
    fun onStart()
    fun attachOutput(output: LoggerPresenterOutput)
    fun detachOutput()
}

internal interface LoggerPresenterOutput {
    fun onChange(model: LoggerModel)
}

internal class LoggerPresenter(
    private var output: LoggerPresenterOutput?,
    private val loggerStateHandler: LoggerStateHandler,
    private val loggerFileHandler: LoggerFileHandler
) : LoggerPresenterInput {

    override fun attachOutput(output: LoggerPresenterOutput) {
        this.output = output
    }

    override fun detachOutput() {
        this.output = null
    }

    override fun deleteLogs(logFileName: String) {
        this.loggerFileHandler.deleteLogs(
            logFileName,
            this::onDeletingComplete,
            this::onDeletingFailed
        )
    }

    override fun onStart() {
        updateWithLoggerStateModel()
    }

    override fun setLogLevel(level: Level) {
        this.loggerStateHandler.logLevel = level
        updateWithLoggerStateModel()
    }

    override fun setIsLoggingInFileEnabled(isLogInFileEnabled: Boolean) {
        this.loggerStateHandler.isLogInFileEnabled = isLogInFileEnabled
        updateWithLoggerStateModel()
    }

    private fun updateWithLoggerStateModel() {
        this.output?.onChange(
            LoggerModel.LoggerState(
                logLevel = this.loggerStateHandler.logLevel,
                isLogInFileEnabled = this.loggerStateHandler.isLogInFileEnabled
            )
        )
    }

    private fun onDeletingComplete() {
        this.output?.onChange(LoggerModel.DeletingComplete())
    }

    private fun onDeletingFailed() {
        this.output?.onChange(LoggerModel.DeletingComplete())
    }
}