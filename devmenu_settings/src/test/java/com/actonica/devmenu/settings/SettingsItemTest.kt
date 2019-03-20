package com.actonica.devmenu.settings

import android.content.Context
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import kotlinx.android.synthetic.main.dm_settings_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication

@RunWith(RobolectricTestRunner::class)
class SettingsItemTest {

    private lateinit var settingsItem: SettingsItem
    private lateinit var view: View
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        this.settingsItem = SettingsItem()
        this.view =
            this.settingsItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(
            this.view.title.text,
            this.context.resources.getString(R.string.dmTitleSettings)
        )

        assertEquals(this.view.buttonApplication.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonApplication.text,
            this.context.resources.getString(R.string.dmLabelApplication)
        )

        assertEquals(this.view.buttonDevelopers.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonDevelopers.text,
            this.context.resources.getString(R.string.dmLabelDevelopers)
        )

        assertEquals(this.view.buttonLocation.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonLocation.text,
            this.context.resources.getString(R.string.dmLabelLocation)
        )

        assertEquals(this.view.buttonSettings.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonSettings.text,
            this.context.resources.getString(R.string.dmLabelSettings)
        )
    }

    @Test
    fun clickButtonApplication_intentToApplicationSettings() {
        this.view.buttonApplication.performClick()
        assertEquals(
            ShadowApplication().nextStartedActivity.action,
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        )
    }

    @Test
    fun clickButtonDevelopers_intentToDevelopersSettings() {
        this.view.buttonDevelopers.performClick()
        assertEquals(
            ShadowApplication().nextStartedActivity.action,
            Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
        )
    }

    @Test
    fun clickButtonLocation_intentToLocationSettings() {
        this.view.buttonLocation.performClick()
        assertEquals(
            ShadowApplication().nextStartedActivity.action,
            Settings.ACTION_LOCATION_SOURCE_SETTINGS
        )
    }

    @Test
    fun clickButtonSettings_intentToSettings() {
        this.view.buttonSettings.performClick()
        assertEquals(
            ShadowApplication().nextStartedActivity.action,
            Settings.ACTION_SETTINGS
        )
    }
}