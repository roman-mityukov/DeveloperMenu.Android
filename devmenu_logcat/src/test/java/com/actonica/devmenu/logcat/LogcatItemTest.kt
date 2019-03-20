package com.actonica.devmenu.logcat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.github.pedrovgs.lynx.LynxActivity
import kotlinx.android.synthetic.main.dm_logcat_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication

@RunWith(RobolectricTestRunner::class)
class LogcatItemTest {

    private lateinit var logcatItem: LogcatItem
    private lateinit var view: View
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        this.logcatItem = LogcatItem(logcatFilter = "", logcatMaxNumberOfTracesToShow = 100)
        this.view =
            this.logcatItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(
            this.view.title.text,
            this.context.resources.getString(R.string.dmTitleLogcat)
        )

        assertEquals(this.view.buttonLogcat.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonLogcat.text,
            this.context.resources.getString(R.string.dmButtonLogcat)
        )
    }

    @Test
    fun clickButtonLogcat_intentToLynx() {
        this.view.buttonLogcat.performClick()

        assertEquals(
            ShadowApplication().nextStartedActivity.component?.className,
            LynxActivity::class.java.canonicalName
        )
    }
}