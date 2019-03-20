package com.actonica.devmenu.logger

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import org.apache.logging.log4j.Level
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoggerStateHandlerTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loggerStateHandler: LoggerStateHandler

    @Before
    fun setUp() {
        this.sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext())
        this.sharedPreferences.edit()
            .putBoolean(LoggerStateHandler.keyIsLogInFileEnabled, true)
            .putString(LoggerStateHandler.keyLogLevel, Level.ALL.name())
            .commit()

        this.loggerStateHandler = LoggerStateHandler(this.sharedPreferences)
    }

    @Test
    fun init_loggerStateHandlerLogLevelEqualsAll() {
        assertEquals(Level.ALL, this.loggerStateHandler.logLevel)
    }

    @Test
    fun init_loggerStateHandlerIsLogInFileEnabledEqualsTrue() {
        assertTrue(this.loggerStateHandler.isLogInFileEnabled)
    }

    @Test
    fun saveLogLevel_itEqualsLogLevelFromSharedPreference() {
        this.loggerStateHandler.logLevel = Level.DEBUG
        assertEquals(this.loggerStateHandler.logLevel, Level.DEBUG)
    }

    @Test
    fun saveLogLevel_itEqualsIsLogInFileEnabledFromSharedPreference() {
        this.loggerStateHandler.isLogInFileEnabled = false
        assertFalse(this.loggerStateHandler.isLogInFileEnabled)
    }
}