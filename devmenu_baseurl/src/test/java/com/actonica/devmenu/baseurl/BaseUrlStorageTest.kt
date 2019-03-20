package com.actonica.devmenu.baseurl

import android.content.Context
import android.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import com.actonica.devmenu.contract.BaseUrlContract
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BaseUrlStorageTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var baseUrlStorage: BaseUrlStorage
    private val validUrl = "http://google.com/"
    private val invalidUrl = "http://google.com"

    @Before
    fun setUp() {
        this.baseUrlStorage =
            BaseUrlStorage(PreferenceManager.getDefaultSharedPreferences(this.context))
    }

    @Test
    fun testDefaultValues() {
        assertNull(this.baseUrlStorage.baseUrlPreset)
        assertNull(this.baseUrlStorage.baseUrlManual)
    }

    @Test
    fun saveValidUrlToBaseUrlManual_baseUrlManualEqualsValidUrl() {
        this.baseUrlStorage.baseUrlManual = this.validUrl
        assertEquals(this.baseUrlStorage.baseUrlManual, this.validUrl)
    }

    @Test
    fun saveValidUrlToBaseUrlManual_baseUrlManualEqualsUrlFromSharedPreferencesByContract() {
        this.baseUrlStorage.baseUrlManual = this.validUrl
        val urlFromSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
            .getString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, "")
        assertEquals(this.baseUrlStorage.baseUrlManual, urlFromSharedPreferences)
    }

    @Test
    fun saveValidUrlToBaseUrlManual_baseUrlPresetIsNull() {
        this.baseUrlStorage.baseUrlPreset = this.validUrl
        this.baseUrlStorage.baseUrlManual = this.validUrl
        assertNull(this.baseUrlStorage.baseUrlPreset)
    }

    @Test
    fun saveInvalidUrlToBaseUrlManual_InvalidUrlException() {
        var exception: Exception? = null
        try {
            this.baseUrlStorage.baseUrlManual = this.invalidUrl
        } catch (e: Exception) {
            exception = e
        }

        assertEquals(exception is InvalidUrlException, true)
    }

    @Test
    fun saveNullToBaseUrlManual_BaseUrlPresetDidNotChanged() {
        this.baseUrlStorage.baseUrlManual = this.validUrl
        this.baseUrlStorage.baseUrlPreset = null

        assertEquals(this.baseUrlStorage.baseUrlManual, this.validUrl)
    }

    @Test
    fun saveValidUrlToBaseUrlPreset_baseUrlPresetEqualsValidUrl() {
        this.baseUrlStorage.baseUrlPreset = this.validUrl
        assertEquals(this.baseUrlStorage.baseUrlPreset, this.validUrl)
    }

    @Test
    fun saveValidUrlToBaseUrlPreset_baseUrlPresetEqualsUrlFromSharedPreferencesByContract() {
        this.baseUrlStorage.baseUrlPreset = this.validUrl
        val urlFromSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
            .getString(BaseUrlContract.KEY_PRESET_BASE_URL.value, "")
        assertEquals(this.baseUrlStorage.baseUrlPreset, urlFromSharedPreferences)
    }

    @Test
    fun saveValidUrlToBaseUrlPreset_baseUrlManualIsNull() {
        this.baseUrlStorage.baseUrlManual = this.validUrl
        this.baseUrlStorage.baseUrlPreset = this.validUrl
        assertNull(this.baseUrlStorage.baseUrlManual)
    }

    @Test
    fun saveInvalidUrlToBaseUrlPreset_InvalidUrlException() {
        var exception: Exception? = null
        try {
            this.baseUrlStorage.baseUrlPreset = this.invalidUrl
        } catch (e: Exception) {
            exception = e
        }

        assertEquals(exception is InvalidUrlException, true)
    }

    @Test
    fun saveNullToBaseUrlPreset_BaseUrlManualDidNotChanged() {
        this.baseUrlStorage.baseUrlPreset = this.validUrl
        this.baseUrlStorage.baseUrlManual = null

        assertEquals(this.baseUrlStorage.baseUrlPreset, this.validUrl)
    }
}