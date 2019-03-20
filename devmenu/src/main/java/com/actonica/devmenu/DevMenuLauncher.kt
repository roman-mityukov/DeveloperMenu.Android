package com.actonica.devmenu

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector

class DevMenuLauncher {
    companion object {
        private lateinit var applicationContext: Context
        private var shakeDetector: ShakeDetector? = null

        /**
         * Инициализирует меню (если слегка потрясти устройство).
         */
        fun startShakeDetection(applicationContext: Context, devMenu: DevMenu) {
            this.applicationContext = applicationContext
            DevMenuProvider.setMenu(devMenu)

            val sensorManager: SensorManager =
                applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager

            if (this.shakeDetector == null) {
                this.shakeDetector = ShakeDetector(ShakeDetectorListener(this::launch))
                this.shakeDetector?.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT)
            }

            this.shakeDetector?.start(sensorManager)
        }

        fun stopShakeDetection() {
            this.shakeDetector?.stop()
        }

        /**
         * Инициализирует меню (например при клике по кнопке)
         */
        fun start(applicationContext: Context, devMenu: DevMenu) {
            this.shakeDetector?.stop()

            this.applicationContext = applicationContext
            DevMenuProvider.setMenu(devMenu)
            launch()
        }

        private fun launch() {
            /*Если меню было открыто с помощью shake, то отписываемся от получения данных с сенсоров*/
            this.shakeDetector?.stop()

            val intent = Intent(this.applicationContext, DevMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.applicationContext.startActivity(intent)
        }
    }

    private class ShakeDetectorListener(private val handler: () -> Unit) : ShakeDetector.Listener {
        override fun hearShake() {
            handler()
        }
    }
}