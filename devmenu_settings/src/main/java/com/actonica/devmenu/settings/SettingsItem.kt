package com.actonica.devmenu.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.actonica.devmenu.contract.DefaultDevMenuItem
import kotlinx.android.synthetic.main.dm_settings_view.view.*

/**
 * Provide navigation to settings
 * - developer options
 * - application settings
 * - location settings
 * - all settings
 */
class SettingsItem : DefaultDevMenuItem() {
    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {

        val context = viewGroup.context

        val view = layoutInflater.inflate(R.layout.dm_settings_view, viewGroup, false)

        view.buttonDevelopers.text =
                context.getString(R.string.dmLabelDevelopers)
        view.buttonDevelopers.setOnClickListener {
            this.startActivity(context, Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        }

        view.buttonApplication.text =
                context.getString(R.string.dmLabelApplication)
        view.buttonApplication.setOnClickListener {
            this.startActivity(
                context,
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
            )
        }

        view.buttonLocation.text = context.getString(R.string.dmLabelLocation)
        view.buttonLocation.setOnClickListener {
            this.startActivity(
                context,
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            )
        }

        view.buttonSettings.text = context.getString(R.string.dmLabelSettings)
        view.buttonSettings.setOnClickListener {
            this.startActivity(
                context,
                Intent(Settings.ACTION_SETTINGS)
            )
        }

        return view
    }

    private fun startActivity(context: Context, intent: Intent) {
        try {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}