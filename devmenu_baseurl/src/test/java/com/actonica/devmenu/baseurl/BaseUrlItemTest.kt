package com.actonica.devmenu.baseurl

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.actonica.devmenu.contract.BaseUrlContract
import kotlinx.android.synthetic.main.dm_baseurl_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BaseUrlItemTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
    private val baseUrls = arrayListOf("http://google.com/", "http://yandex.ru/")
    private lateinit var baseUrlItem: BaseUrlItem
    private lateinit var view: View

    @Before
    fun setUp() {
        this.baseUrlItem = BaseUrlItem(
            baseUrls = this.baseUrls,
            applicationContext = this.context
        )

        this.view =
            this.baseUrlItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(
            this.view.title.text,
            this.context.resources.getString(R.string.dmTitleBaseUrl)
        )

        assertEquals(this.view.baseUrlManualLabel.visibility, View.VISIBLE)
        assertEquals(
            this.view.baseUrlManualLabel.text,
            this.context.resources.getString(R.string.dmBaseUrlManualInput)
        )

        assertEquals(this.view.baseUrlManual.visibility, View.VISIBLE)

        assertEquals(this.view.baseUrlPresetLabel.visibility, View.VISIBLE)
        assertEquals(
            this.view.baseUrlPresetLabel.text,
            this.context.resources.getString(R.string.dmBaseUrlSelection)
        )

        assertEquals(this.view.baseUrlPreset.visibility, View.VISIBLE)
    }

    @Test
    fun nullBaseUrlList_invisibleBaseUrlPreset() {
        this.baseUrlItem = BaseUrlItem(
            baseUrls = null,
            applicationContext = this.context
        )
        this.view =
            baseUrlItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
        assertEquals(this.view.baseUrlPreset.visibility, View.GONE)
    }

    @Test
    fun setBaseUrlPreset_valuesFromSharedPreferencesIsValid() {
        this.sharedPreferences.edit()
            .putString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, this.baseUrls[1])

        this.view.baseUrlPreset.setSelection(1)

        val baseUrlPresetFromSharedPreferences =
            this.sharedPreferences.getString(BaseUrlContract.KEY_PRESET_BASE_URL.value, null)
        val baseUrlManualFromSharedPreferences =
            this.sharedPreferences.getString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, null)

        assertEquals(baseUrls[0], baseUrlPresetFromSharedPreferences)
        assertNull(baseUrlManualFromSharedPreferences)
    }

    @Test
    fun setBaseUrlManual_valuesFromSharedPreferencesIsValid() {

        this.sharedPreferences.edit()
            .putString(BaseUrlContract.KEY_PRESET_BASE_URL.value, this.baseUrls[1])

        this.view.baseUrlManual.setText(this.baseUrls[0])
        this.view.baseUrlManual.onEditorAction(EditorInfo.IME_ACTION_DONE)

        val baseUrlPresetFromSharedPreferences =
            this.sharedPreferences.getString(BaseUrlContract.KEY_PRESET_BASE_URL.value, null)
        val baseUrlManualFromSharedPreferences =
            this.sharedPreferences.getString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, null)

        assertEquals(baseUrls[0], baseUrlManualFromSharedPreferences)
        assertNull(baseUrlPresetFromSharedPreferences)
    }
}