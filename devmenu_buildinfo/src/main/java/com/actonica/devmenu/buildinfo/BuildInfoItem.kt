package com.actonica.devmenu.buildinfo

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.actonica.devmenu.contract.DefaultDevMenuItem
import kotlinx.android.synthetic.main.dm_build_info_view.view.*

/**
 * Shows information about an application
 * - versionCode
 * - versionName
 * - application package
 * - requested requestedPermissions
 * - granted requestedPermissions
 */
class BuildInfoItem(applicationContext: Context) : DefaultDevMenuItem() {

    private val packageInfo: PackageInfo =
        applicationContext.packageManager.getPackageInfo(
            applicationContext.packageName,
            PackageManager.GET_PERMISSIONS
        )
    private val permissionPrefix: String = "android.permission."
    override var model: BuildInfoModel = BuildInfoModel(
        versionCode = this.packageInfo.versionCode.toString(),
        versionName = this.packageInfo.versionName,
        packageName = this.packageInfo.packageName,
        requestedPermissions = this.packageInfo.requestedPermissions?.map {
            it.removePrefix(this.permissionPrefix)
        }?.reduce { a, b -> "$a\n$b" },
        grantedPermissions = this.packageInfo.requestedPermissions?.mapNotNull {
            val index = packageInfo.requestedPermissions.indexOf(it)

            if ((this.packageInfo.requestedPermissionsFlags[index] and PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                it
            } else {
                null
            }
        }?.map {
            it.removePrefix(this.permissionPrefix)
        }?.reduce { a, b -> "$a\n$b" }
    )

    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val view = layoutInflater.inflate(R.layout.dm_build_info_view, viewGroup, false)

        val context = viewGroup.context

        view.versionCode.text = context.getString(R.string.dmLabelVersionCode)
        view.versionCodeValue.text = this.model.versionCode

        view.versionName.text = context.getString(R.string.dmLabelVersionName)
        view.versionNameValue.text = this.model.versionName

        view.appPackage.text = context.getString(R.string.dmLabelAppPackage)
        view.appPackageValue.text = this.model.packageName

        view.requestedPermissions.text = context.getString(R.string.dmLabelRequestedPermissions)
        view.requestedPermissionsValue.text = this.model.requestedPermissions

        view.grantedPermissions.text = context.getString(R.string.dmLabelGrantedPermissions)
        view.grantedPermissionsValue.text = this.model.grantedPermissions

        return view
    }
}

data class BuildInfoModel(
    val versionCode: String,
    val versionName: String,
    val packageName: String,
    val requestedPermissions: String?,
    val grantedPermissions: String?
) {
    override fun toString(): String {
        return "BuildInfo" +
                "\nversionCode = ${this.versionCode}" +
                "\nversionName = ${this.versionName}" +
                "\npackageName = ${this.packageName}" +
                "\nrequestedPermissions = ${this.requestedPermissions}" +
                "\ngrantedPermissions = ${this.grantedPermissions}"
    }
}