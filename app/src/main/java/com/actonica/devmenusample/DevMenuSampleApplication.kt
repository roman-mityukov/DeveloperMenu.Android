package com.actonica.devmenusample

import android.app.Activity
import androidx.multidex.MultiDexApplication
import com.actonica.devmenu.DevMenu
import com.actonica.devmenu.DevMenuLauncher
import com.actonica.devmenu.logger.DevMenuLogger
import com.actonica.devmenusample.di.ApplicationComponent
import com.actonica.devmenusample.di.DaggerApplicationComponent
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class DevMenuSampleApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    private var applicationComponent: ApplicationComponent? = null

    @Inject
    lateinit var devMenu: DevMenu

    override fun onCreate() {
        super.onCreate()

        this.applicationComponent = DaggerApplicationComponent.builder().application(this).build()
        this.applicationComponent!!.inject(this)

        if (BuildConfig.DEBUG) {
            DevMenuLauncher.startShakeDetection(applicationContext = this, devMenu = this.devMenu)
        }

        if (BuildConfig.DEBUG) {
            DevMenuLogger.init(
                applicationContext = this.applicationContext,
                configXmlResource = R.raw.log4j2_config_debug
            )
        } else {
            DevMenuLogger.init(
                applicationContext = this.applicationContext,
                configXmlResource = R.raw.log4j2_config_release
            )
        }

        val crashlyticsKit: Crashlytics = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(true).build())
            .build()

        Fabric.with(this, crashlyticsKit)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return this.activityDispatchingAndroidInjector
    }
}