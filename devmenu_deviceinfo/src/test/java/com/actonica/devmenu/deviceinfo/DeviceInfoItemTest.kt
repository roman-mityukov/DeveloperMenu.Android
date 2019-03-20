package com.actonica.devmenu.deviceinfo

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import kotlinx.android.synthetic.main.dm_device_info_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DeviceInfoItemTest {

    private lateinit var deviceInfoItem: DeviceInfoItem
    private lateinit var view: View
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        this.deviceInfoItem = DeviceInfoItem(this.context)
        this.deviceInfoItem.model = DeviceInfoModel(
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE,
            apiVersion = Build.VERSION.SDK_INT.toString(),
            screenResolution = "470x320",
            screenDensity = "1.0 mdpi 160"
        )
        this.view = this.deviceInfoItem.getView(
            LayoutInflater.from(this.context),
            FrameLayout(this.context)
        )
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(
            this.view.title.text,
            this.context.resources.getString(R.string.dmTitleDeviceInfo)
        )

        assertEquals(this.view.manufacturer.visibility, View.VISIBLE)
        assertEquals(
            this.view.manufacturer.text,
            this.context.resources.getString(R.string.dmManufacturer)
        )

        assertEquals(this.view.manufacturerValue.visibility, View.VISIBLE)
        assertEquals(this.view.manufacturerValue.text, this.deviceInfoItem.model?.manufacturer)

        assertEquals(this.view.model.visibility, View.VISIBLE)
        assertEquals(this.view.model.text, this.context.resources.getString(R.string.dmModel))

        assertEquals(this.view.modelValue.visibility, View.VISIBLE)
        assertEquals(this.view.modelValue.text, this.deviceInfoItem.model?.model)

        assertEquals(this.view.androidVersion.visibility, View.VISIBLE)
        assertEquals(
            this.view.androidVersion.text,
            this.context.resources.getString(R.string.dmAndroidVersion)
        )

        assertEquals(this.view.androidVersionValue.visibility, View.VISIBLE)
        assertEquals(this.view.androidVersionValue.text, this.deviceInfoItem.model?.androidVersion)

        assertEquals(this.view.apiVersion.visibility, View.VISIBLE)
        assertEquals(
            this.view.apiVersion.text,
            this.context.resources.getString(R.string.dmApiVersion)
        )

        assertEquals(this.view.apiVersionValue.visibility, View.VISIBLE)
        assertEquals(this.view.apiVersionValue.text, this.deviceInfoItem.model?.apiVersion)

        assertEquals(this.view.screenResolution.visibility, View.VISIBLE)
        assertEquals(
            this.view.screenResolution.text,
            this.context.resources.getString(R.string.dmScreenResolution)
        )

        assertEquals(this.view.screenResolutionValue.visibility, View.VISIBLE)
        assertEquals(
            this.view.screenResolutionValue.text,
            this.deviceInfoItem.model?.screenResolution
        )

        assertEquals(this.view.screenDensity.visibility, View.VISIBLE)
        assertEquals(
            this.view.screenDensity.text,
            this.context.resources.getString(R.string.dmScreenDensity)
        )

        assertEquals(this.view.screenDensityValue.visibility, View.VISIBLE)
        assertEquals(this.view.screenDensityValue.text, this.deviceInfoItem.model?.screenDensity)
    }
}