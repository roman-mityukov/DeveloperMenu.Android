package com.actonica.devmenusample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.actonica.devmenu.DevMenu
import com.actonica.devmenu.DevMenuLauncher
import com.actonica.devmenusample.di.DependencyNames
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class DevMenuSampleActivity : DaggerAppCompatActivity(), CoroutineScope {

    private val logger = LoggerFactory.getLogger("DevMenuSampleActivity")

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    @field:[Inject Named(DependencyNames.BASE_URL)]
    @JvmField
    var baseUrl: String? = null

    @Inject
    lateinit var devMenu: DevMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.job = Job()

        testHttpJob()

        this.logger.info("info onCreate")
        this.logger.debug("debug onCreate")
        this.logger.warn("warn onCreate")
        this.logger.error("error onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        this.job.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.devMenu) {
            DevMenuLauncher.start(this.applicationContext, this.devMenu)
        }
        return true
    }

    private fun testHttpJob() = launch {
        this@DevMenuSampleActivity.baseUrl?.let { baseUrl: String ->
            val client =
                OkHttpClient.Builder()
                    .addInterceptor(ChuckInterceptor(this@DevMenuSampleActivity))
                    .build()
            val request = Request.Builder().url(baseUrl).build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
        }
    }
}