package com.actonica.devmenu.location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import kotlinx.android.synthetic.main.location_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class LocationItemTest {

    private lateinit var locationItem: LocationItem
    private lateinit var view: View
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        this.locationItem = LocationItem(this.context)
        this.view =
            this.locationItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(
            this.view.title.text,
            this.context.resources.getString(R.string.dmTitleLocation)
        )

        assertEquals(this.view.latitude.visibility, View.VISIBLE)
        assertEquals(
            this.view.latitude.text,
            this.context.resources.getString(R.string.dmLabelLatitude)
        )

        assertEquals(this.view.latitudeValue.visibility, View.VISIBLE)

        assertEquals(this.view.longitude.visibility, View.VISIBLE)
        assertEquals(
            this.view.longitude.text,
            this.context.resources.getString(R.string.dmLabelLongitude)
        )

        assertEquals(this.view.longitudeValue.visibility, View.VISIBLE)

        assertEquals(this.view.time.visibility, View.VISIBLE)
        assertEquals(this.view.time.text, this.context.resources.getString(R.string.dmLabelTime))

        assertEquals(this.view.timeValue.visibility, View.VISIBLE)

        assertEquals(this.view.provider.visibility, View.VISIBLE)
        assertEquals(
            this.view.provider.text,
            this.context.resources.getString(R.string.dmLabelProvider)
        )

        assertEquals(this.view.providerValue.visibility, View.VISIBLE)

        assertEquals(this.view.accuracy.visibility, View.VISIBLE)
        assertEquals(
            this.view.accuracy.text,
            this.context.resources.getString(R.string.dmLabelAccuracy)
        )

        assertEquals(this.view.accuracyValue.visibility, View.VISIBLE)

        assertEquals(this.view.buttonShowOnMap.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonShowOnMap.text,
            this.context.resources.getString(R.string.dmButtonShowOnMap)
        )
    }

    @Test
    fun clickButtonShowOnMap_permissionsGranted_intentToLocationActivity() {
        this.locationItem.model = LocationModel.State(
            latitude = 53.5,
            longitude = 83.5,
            time = "time",
            provider = "provider",
            accuracy = "1"
        )

        this.view.buttonShowOnMap.performClick()

        assertEquals(
            ShadowApplication().nextStartedActivity.component?.className,
            LocationActivity::class.java.canonicalName
        )
    }

    @Test
    fun clickButtonShowOnMap_permissionsNotGranted_toastNoCoordinates() {
        this.locationItem.model = LocationModel.State(
            latitude = null,
            longitude = null,
            time = "",
            provider = "",
            accuracy = ""
        )

        this.view.buttonShowOnMap.performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(), "No coordinates")
    }
}