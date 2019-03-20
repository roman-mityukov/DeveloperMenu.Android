package com.actonica.devmenu.http

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.readystatesoftware.chuck.internal.ui.MainActivity
import kotlinx.android.synthetic.main.dm_http_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication

@RunWith(RobolectricTestRunner::class)
class HttpItemTest {

    private lateinit var httpItem: HttpItem
    private lateinit var view: View
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        this.httpItem = HttpItem()
        this.view =
            this.httpItem.getView(LayoutInflater.from(this.context), FrameLayout(this.context))
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(this.view.title.text, this.context.resources.getString(R.string.dmTitleHttp))

        assertEquals(this.view.buttonHttp.visibility, View.VISIBLE)
        assertEquals(
            this.view.buttonHttp.text,
            this.context.resources.getString(R.string.dmButtonHttp)
        )
    }

    @Test
    fun clickButtonHttp_intentToChuckActivity() {
        this.view.buttonHttp.performClick()
        assertEquals(
            ShadowApplication().nextStartedActivity.component?.className,
            MainActivity::class.java.canonicalName
        )
    }
}