package com.actonica.devmenu.logcat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.actonica.devmenu.contract.DefaultDevMenuItem
import com.github.pedrovgs.lynx.LynxActivity
import com.github.pedrovgs.lynx.LynxConfig
import kotlinx.android.synthetic.main.dm_logcat_view.view.*

/**
 * Показывает логи из Logcat. Используется <a href="https://github.com/pedrovgs/Lynx">Lynx</a>
 */
class LogcatItem(
    private val logcatMaxNumberOfTracesToShow: Int,
    private val logcatFilter: String
) : DefaultDevMenuItem() {

    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val view = layoutInflater.inflate(R.layout.dm_logcat_view, viewGroup, false)

        val context = viewGroup.context

        view.buttonLogcat.setOnClickListener {
            val lynxConfig = LynxConfig()
            lynxConfig.maxNumberOfTracesToShow = this.logcatMaxNumberOfTracesToShow
            lynxConfig.filter = this.logcatFilter

            val lynxIntent = LynxActivity.getIntent(context, lynxConfig)
            lynxIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(lynxIntent)
        }

        return view
    }
}