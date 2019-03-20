package com.actonica.devmenu.baseurl

sealed class BaseUrlModel {
    data class BaseUrlState(val baseUrlManual: String?, val baseUrlPreset: String?) :
        BaseUrlModel() {
        override fun toString(): String {
            return "BaseUrl\nbaseUrlManual = ${this.baseUrlManual}\nbaseUrlPreset = ${this.baseUrlPreset}"
        }
    }

    class InvalidUrlModel : BaseUrlModel()
}