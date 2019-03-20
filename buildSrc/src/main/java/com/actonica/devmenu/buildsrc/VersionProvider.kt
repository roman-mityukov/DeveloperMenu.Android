package com.actonica.devmenu.buildsrc

import org.apache.commons.io.IOUtils
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class VersionProvider {
    companion object {
        fun getVersion(): Int {
            val post: HttpURLConnection =
                URL(System.getenv("DEVELOPER_MENU_GET_VERSION_URL")).openConnection() as HttpURLConnection
            post.requestMethod = "GET"
            post.setRequestProperty("Content-Type", "application/json")
            post.setRequestProperty("User-Agent", "Mozilla/4.0")
            post.setRequestProperty("Token", System.getenv("DEVELOPER_MENU_GET_VERSION_TOKEN"))
            val postRC: Int = post.responseCode
            println(postRC)
            if (postRC == HttpURLConnection.HTTP_OK) {
                val versionCode: String =
                    IOUtils.toString(post.inputStream, StandardCharsets.UTF_8.name())

                println(versionCode)
                return versionCode.toInt()
            } else {
                val error: String = IOUtils.toString(post.errorStream, StandardCharsets.UTF_8.name())
                println(error)
                throw ServiceUnavailableError()
            }
        }
    }
}

class ServiceUnavailableError : Error("getVersion unavailable")