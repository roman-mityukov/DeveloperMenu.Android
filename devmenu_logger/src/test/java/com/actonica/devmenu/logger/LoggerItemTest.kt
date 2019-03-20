package com.actonica.devmenu.logger

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import kotlinx.android.synthetic.main.dm_logger_view.view.*
import org.apache.logging.log4j.Level
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication
import java.io.File
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
class LoggerItemTest {

    private lateinit var loggerItem: LoggerItem
    private lateinit var view: View
    private lateinit var loggerStateHandler: LoggerStateHandler
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        this.loggerItem = LoggerItem(
            applicationContext = this.context,
            logFileName = "mylog.log"
        )
        this.view =
            this.loggerItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
        this.loggerStateHandler = LoggerStateHandlerProvider.provideLoggerStateHandler(
            PreferenceManager.getDefaultSharedPreferences(this.context)
        )
    }

    @Test
    fun testDefaultValues() {
        assertEquals(this.view.checkBox.visibility, View.VISIBLE)
        assertEquals(this.view.spinnerLogLevel.visibility, View.VISIBLE)
        assertEquals(this.view.buttonClear.visibility, View.VISIBLE)
        assertEquals(this.view.buttonLogger.visibility, View.VISIBLE)
    }

    @Test
    fun setSpinnerSelection_changeLogLevel() {
        this.view.spinnerLogLevel.setSelection(0)
        assertEquals(this.loggerStateHandler.logLevel, Level.ALL)
    }

    @Test
    fun setCheckBox_changeIsLoggingInFileEnabled() {
        this.view.checkBox.isChecked = true
        assertTrue(this.loggerStateHandler.isLogInFileEnabled)
    }

    @Test
    fun clickButtonClear_logFilesAreDeleted() {
        val fileNames = arrayListOf(
            "mylog1.log",
            "mylog2.log",
            "mylog3.log",
            "mylog4.log",
            "mylog5.log",
            "someData.txt"
        )

        fileNames.forEach { fileName ->
            val logFile =
                File(
                    ApplicationProvider.getApplicationContext<Context>().getExternalFilesDir(null),
                    fileName
                )

            FileOutputStream(logFile).use { stream ->
                stream.write("someString".toByteArray())
            }
        }

        this.view.buttonClear.performClick()
        Thread.sleep(500)
        val fileList =
            File(
                "${ApplicationProvider.getApplicationContext<Context>().getExternalFilesDir(
                    null
                )?.absolutePath}${File.separator}"
            )
        assertTrue(fileList.listFiles().size == 1)
    }

    @Test
    fun clickButtonLog_intentToLoggerActivity() {
        this.view.buttonLogger.performClick()
        val startedIntent = ShadowApplication().nextStartedActivity
        assertEquals(startedIntent.component?.className, LoggerActivity::class.java.canonicalName)
    }
}