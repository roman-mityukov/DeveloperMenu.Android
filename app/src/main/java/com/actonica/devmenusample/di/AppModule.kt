package com.actonica.devmenusample.di

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.actonica.devmenu.DevMenu
import com.actonica.devmenu.baseurl.BaseUrlItem
import com.actonica.devmenu.buildinfo.BuildInfoItem
import com.actonica.devmenu.contract.BaseUrlContract
import com.actonica.devmenu.deviceinfo.DeviceInfoItem
import com.actonica.devmenu.http.HttpItem
import com.actonica.devmenu.location.LocationItem
import com.actonica.devmenu.logcat.LogcatItem
import com.actonica.devmenu.logger.LoggerItem
import com.actonica.devmenu.settings.SettingsItem
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideBaseUrls(): List<String> {
        return listOf("https://google.com/", "https://yandex.com/")
    }

    @Provides
    @Named(DependencyNames.LOG_FILE_NAME)
    fun provideLogPath(): String {
        return "com.actonica.devmenusample.log"
    }

    @Provides
    @Singleton
    fun provideDevMenu(
        applicationContext: Context,
        baseUrls: List<String>,
        @Named(DependencyNames.LOG_FILE_NAME) logPath: String
    ): DevMenu {
        return DevMenu.Builder()
            .add(BuildInfoItem(applicationContext))
            .add(DeviceInfoItem(applicationContext))
            .add(BaseUrlItem(baseUrls, applicationContext))
            .add(HttpItem())
            .add(
                LogcatItem(
                    logcatMaxNumberOfTracesToShow = 1000,
                    logcatFilter = applicationContext.packageName
                )
            )
            .add(LoggerItem(applicationContext, logPath))
            .add(LocationItem(applicationContext))
            .add(SettingsItem())
            .build()
    }

    @Provides
    @Named(DependencyNames.BASE_URL)
    fun provideBaseUrl(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val manualBaseUrl =
            sharedPreferences.getString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, null)
        val presetBaseUrl =
            sharedPreferences.getString(BaseUrlContract.KEY_PRESET_BASE_URL.value, null)

        return when {
            !manualBaseUrl.isNullOrBlank() -> manualBaseUrl
            !presetBaseUrl.isNullOrBlank() -> presetBaseUrl
            else -> null
        }
    }
}