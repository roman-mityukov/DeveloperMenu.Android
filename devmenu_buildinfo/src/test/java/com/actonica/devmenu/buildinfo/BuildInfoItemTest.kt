package com.actonica.devmenu.buildinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import kotlinx.android.synthetic.main.dm_build_info_view.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BuildInfoItemTest {

    private lateinit var buildInfoItem: BuildInfoItem
    private lateinit var view: View
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        this.buildInfoItem = BuildInfoItem(this.context)
        this.buildInfoItem.model = BuildInfoModel(
            versionCode = "1.0",
            versionName = "name",
            packageName = "com.actonica.devmenu.buildinfo",
            requestedPermissions = "",
            grantedPermissions = ""
        )
        this.view = this.buildInfoItem.getView(
            LayoutInflater.from(this.context),
            FrameLayout(this.context)
        )
    }

    @Test
    fun testDefaultViewState() {
        assertEquals(this.view.title.visibility, View.VISIBLE)
        assertEquals(
            this.view.title.text,
            this.context.resources.getString(R.string.dmTitleBuildInfo)
        )

        assertEquals(this.view.versionCode.visibility, View.VISIBLE)
        assertEquals(
            this.view.versionCode.text,
            this.context.resources.getString(R.string.dmLabelVersionCode)
        )

        assertEquals(this.view.versionCodeValue.visibility, View.VISIBLE)
        assertEquals(this.view.versionCodeValue.text, this.buildInfoItem.model.versionCode)

        assertEquals(this.view.versionName.visibility, View.VISIBLE)
        assertEquals(
            this.view.versionName.text,
            this.context.resources.getString(R.string.dmLabelVersionName)
        )

        assertEquals(this.view.versionNameValue.visibility, View.VISIBLE)
        assertEquals(this.view.versionNameValue.text, this.buildInfoItem.model.versionName)

        assertEquals(this.view.appPackage.visibility, View.VISIBLE)
        assertEquals(
            this.view.appPackage.text,
            this.context.resources.getString(R.string.dmLabelAppPackage)
        )

        assertEquals(this.view.appPackageValue.visibility, View.VISIBLE)
        assertEquals(this.view.appPackageValue.text, this.buildInfoItem.model.packageName)

        assertEquals(this.view.requestedPermissions.visibility, View.VISIBLE)
        assertEquals(
            this.view.requestedPermissions.text,
            this.context.resources.getString(R.string.dmLabelRequestedPermissions)
        )

        assertEquals(this.view.requestedPermissionsValue.visibility, View.VISIBLE)
        assertEquals(
            this.view.requestedPermissionsValue.text,
            this.buildInfoItem.model.requestedPermissions
        )

        assertEquals(this.view.grantedPermissions.visibility, View.VISIBLE)
        assertEquals(
            this.view.grantedPermissions.text,
            this.context.resources.getString(R.string.dmLabelGrantedPermissions)
        )

        assertEquals(this.view.grantedPermissionsValue.visibility, View.VISIBLE)
        assertEquals(
            this.view.grantedPermissionsValue.text,
            this.buildInfoItem.model.grantedPermissions
        )
    }
}