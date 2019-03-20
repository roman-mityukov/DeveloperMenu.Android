package com.actonica.devmenu.logger

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext

internal class LoggerFileHandler(private val applicationContext: Context) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()

    fun deleteLogs(logFileName: String, success: () -> Unit, failure: () -> Unit) = this.launch {
        try {
            val fileList =
                File("${applicationContext.getExternalFilesDir(null)?.absolutePath}${File.separator}")

            val allLogFileList =
                fileList.listFiles { _, name ->
                    name.contains(
                        logFileName.removeSuffix(".log"),
                        false
                    ) && name.contains("log")
                }

            allLogFileList.forEach { file: File ->
                file.delete()
            }

            withContext(Dispatchers.Main) {
                success()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                failure()
            }
        }
    }
}