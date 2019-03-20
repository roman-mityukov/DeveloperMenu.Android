package com.actonica.devmenu.logger

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
class LoggerFileHandlerTest {

    private lateinit var loggerFileHandler: LoggerFileHandler
    private val logFileName: String = "mylog"

    @Before
    fun setUp() {
        this.loggerFileHandler = LoggerFileHandler(ApplicationProvider.getApplicationContext())

        val fileList = arrayListOf(
            "mylog1.log",
            "mylog2.log",
            "mylog3.log",
            "mylog4.log",
            "mylog5.log",
            "someData.txt"
        )

        fileList.forEach { fileName ->
            val logFile =
                File(
                    ApplicationProvider.getApplicationContext<Context>().getExternalFilesDir(null),
                    fileName
                )

            FileOutputStream(logFile).use { stream ->
                stream.write("someString".toByteArray())
            }
        }
    }

    /* https://github.com/robolectric/robolectric/issues/3418
    @Test
    fun testSetUp() {
        val fileList =
            File("${ApplicationProvider.getApplicationContext<Context>().getExternalFilesDir(null)?.absolutePath}${File.separator}")
        assertTrue(fileList.listFiles().isNotEmpty())
    }*/

    @Test
    fun delete_noFilesInExternalStorageWithLogExtension() {
        this.loggerFileHandler.deleteLogs(
            logFileName = this.logFileName,
            success = {
                val fileList =
                    File(
                        "${ApplicationProvider.getApplicationContext<Context>().getExternalFilesDir(
                            null
                        )?.absolutePath}${File.separator}"
                    )
                assertTrue(fileList.listFiles().size == 1)
            },
            failure = {}
        )
    }
}