package com.actonica.devmenu

internal class DevMenuProvider {
    companion object {

        private var devMenu: DevMenu? = null

        fun setMenu(devMenu: DevMenu) {
            this.devMenu = devMenu
        }

        fun getMenu(): DevMenu {
            return this.devMenu ?: throw DevMenuProviderException()
        }
    }
}

internal class DevMenuProviderException : Exception("DevMenu instance must be initialized")