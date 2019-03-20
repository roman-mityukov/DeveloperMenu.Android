package com.actonica.devmenu

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.actonica.devmenu.contract.DevMenuItem

class DevMenu(val items: List<DevMenuItem>) : LifecycleObserver {

    class Builder {

        private val items = arrayListOf<DevMenuItem>()

        /**
         * Добавляет элемент для отображения в меню
         */
        fun add(item: DevMenuItem): Builder {
            this.items.add(item)
            return this
        }

        /**
         * Creates DevMenu instance with given items
         */
        fun build(): DevMenu {
            return DevMenu(this.items)
        }
    }

    /**
     * Возвращает данные из элементов меню
     */
    fun getData(): String {
        return this.items
            .mapNotNull { it.getData() }
            .fold("Developer Menu") { sum, element -> "$sum\n********************\n$element" }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        this.items.forEach {
            it.onCreate()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        this.items.forEach {
            it.onDestroy()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        this.items.forEach {
            it.onStart()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        this.items.forEach {
            it.onStop()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        this.items.forEach {
            it.onResume()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        this.items.forEach {
            it.onPause()
        }
    }
}