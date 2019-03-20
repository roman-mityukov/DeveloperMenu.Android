package com.actonica.devmenu.http

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.actonica.devmenu.contract.DefaultDevMenuItem
import com.readystatesoftware.chuck.Chuck
import kotlinx.android.synthetic.main.dm_http_view.view.*

/**
 * Показывает статистику по http запросам
 */
class HttpItem : DefaultDevMenuItem() {
    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {

        val view = layoutInflater.inflate(R.layout.dm_http_view, viewGroup, false)

        val context = viewGroup.context

        view.buttonHttp.setOnClickListener {
            val chuckIntent = Chuck.getLaunchIntent(context)
            context.startActivity(chuckIntent)
        }

        return view
    }
}