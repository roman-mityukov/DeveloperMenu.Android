package com.actonica.devmenu.logger

import android.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoggerStateHandlerProviderTest {

    private lateinit var loggerStateHandler: LoggerStateHandler

    @Before
    fun setUp() {
        this.loggerStateHandler =
            LoggerStateHandlerProvider.provideLoggerStateHandler(
                PreferenceManager.getDefaultSharedPreferences(
                    ApplicationProvider.getApplicationContext()
                )
            )
    }

    @Test
    fun loggerStateHandlerInstanceIsAlwaysSame() {
        val newLoggerStateHandler =
            LoggerStateHandlerProvider.provideLoggerStateHandler(
                PreferenceManager.getDefaultSharedPreferences(
                    ApplicationProvider.getApplicationContext()
                )
            )
        assertSame(this.loggerStateHandler, newLoggerStateHandler)
    }
}