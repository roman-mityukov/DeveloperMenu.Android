package com.actonica.devmenu.baseurl

import android.content.SharedPreferences
import android.util.Patterns
import com.actonica.devmenu.contract.BaseUrlContract

internal class BaseUrlStorage(private val sharedPreferences: SharedPreferences) {
    var baseUrlManual: String?
        get() {
            return this.sharedPreferences.getString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, null)
        }
        set(value) {
            val editor = this.sharedPreferences.edit()

            if (value != null) {
                if (validate(value)) {
                    editor.putString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, value)
                    editor.putString(BaseUrlContract.KEY_PRESET_BASE_URL.value, null)
                } else {
                    throw InvalidUrlException()
                }
            } else {
                editor.putString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, value)
            }

            editor.apply()
        }

    var baseUrlPreset: String?
        get() {
            return this.sharedPreferences.getString(BaseUrlContract.KEY_PRESET_BASE_URL.value, null)
        }
        set(value) {
            val editor = this.sharedPreferences.edit()

            if (value != null) {
                if (validate(value)) {
                    editor.putString(BaseUrlContract.KEY_PRESET_BASE_URL.value, value)
                    editor.putString(BaseUrlContract.KEY_MANUAL_BASE_URL.value, null)
                } else {
                    throw InvalidUrlException()
                }
            } else {
                editor.putString(BaseUrlContract.KEY_PRESET_BASE_URL.value, value)
            }

            editor.apply()
        }

    private fun validate(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches() && url.last() == '/'
    }
}

internal class InvalidUrlException : Exception()