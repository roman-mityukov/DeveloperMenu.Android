package com.actonica.devmenu.contract

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Basic interface for menu module
 */
interface DevMenuItem {
    val model: Any?

    /**
     * Returns an instance of the View class, which is displayed in the list of menu modules
     */
    fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View

    /**
     *  Returns summary data from menu items
     */
    fun getData(): String?

    /*
     * Activity lifecycle methods
     */
    fun onCreate()

    fun onDestroy()

    fun onStart()

    fun onStop()

    fun onResume()

    fun onPause()
}

abstract class DefaultDevMenuItem : DevMenuItem {

    override val model: Any? = null

    override fun getData(): String? {
        return this.model?.toString()
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }
}