package com.actonica.devmenu.deviceinfo

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.actonica.devmenu.contract.DefaultDevMenuItem
import kotlinx.android.synthetic.main.dm_device_info_view.view.*

/**
 * Показывают информацию о устройстве
 * - manufacturer
 * - model
 * - Android version
 * - API version
 * - Screen resolution (in pixels)
 * - Screen density
 */
class DeviceInfoItem(applicationContext: Context) : DefaultDevMenuItem() {
    override var model: DeviceInfoModel? = null
    private val mdpi: Float = 1.0f
    private val hdpi: Float = 1.5f
    private val xhdpi: Float = 2.0f
    private val xxhdpi: Float = 3.0f
    private val xxxhdpi: Float = 4.0f

    init {
        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        /*
        mdpi (Baseline):	160 dpi	1×
        hdpi:	            240 dpi	1.5×
        xhdpi:	            320 dpi	2×
        xxhdpi:	            480 dpi	3×
        xxxhdpi:	        640 dpi	4×
        */
        val mapDensity = mutableMapOf<Float, String>()
        mapDensity[this.mdpi] = "mdpi"
        mapDensity[this.hdpi] = "hdpi"
        mapDensity[this.xhdpi] = "xhdpi"
        mapDensity[this.xxhdpi] = "xxhdpi"
        mapDensity[this.xxxhdpi] = "xxxhdpi"

        val screenDensity =
            "${displayMetrics.density} ${mapDensity[displayMetrics.density]} ${displayMetrics.densityDpi}"
        val screenResolution = "${displayMetrics.heightPixels}x${displayMetrics.widthPixels}"

        this.model = DeviceInfoModel(
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE,
            apiVersion = Build.VERSION.SDK_INT.toString(),
            screenDensity = screenDensity,
            screenResolution = screenResolution
        )
    }

    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {

        val context = viewGroup.context

        val view = layoutInflater.inflate(R.layout.dm_device_info_view, viewGroup, false)
        view.title.text = context.getString(R.string.dmTitleDeviceInfo)
        view.manufacturer.text = context.getString(R.string.dmManufacturer)
        view.manufacturerValue.text = this.model?.manufacturer
        view.model.text = context.getString(R.string.dmModel)
        view.modelValue.text = this.model?.model
        view.androidVersion.text = context.getString(R.string.dmAndroidVersion)
        view.androidVersionValue.text = this.model?.androidVersion
        view.apiVersion.text = context.getString(R.string.dmApiVersion)
        view.apiVersionValue.text = this.model?.apiVersion
        view.screenResolution.text = context.getString(R.string.dmScreenResolution)
        view.screenResolutionValue.text = this.model?.screenResolution
        view.screenDensity.text = context.getString(R.string.dmScreenDensity)
        view.screenDensityValue.text = this.model?.screenDensity
        return view
    }
}

data class DeviceInfoModel(
    val manufacturer: String,
    val model: String,
    val apiVersion: String,
    val androidVersion: String,
    val screenResolution: String,
    val screenDensity: String
) {
    override fun toString(): String {
        return "DeviceInfo" +
                "\nmanufacturer = ${this.manufacturer}" +
                "\nmodel = ${this.model}" +
                "\napiVersion = ${this.apiVersion}" +
                "\nandroidVersion = ${this.androidVersion}" +
                "\nscreenResolution = ${this.screenResolution}" +
                "\nscreenDensity = ${this.screenDensity}"
    }
}