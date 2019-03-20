package com.actonica.devmenu.logger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.dm_logger_activity.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

internal class LoggerActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + this.job + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

    private lateinit var lastLogFile: File

    private lateinit var allLogFileList: Array<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dm_logger_activity)

        this.title = this.resources.getString(R.string.dmTitleLogger)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.job = Job()

        val logFileName: String = this.intent.getStringExtra(LoggerItem.INTENT_EXTRA_LOG_FILE_NAME)

        val fileList =
            File("${this.applicationContext.getExternalFilesDir(null)?.absolutePath}${File.separator}")

        this.allLogFileList =
                fileList.listFiles { _, name ->
                    name.contains(
                        logFileName.removeSuffix(".log"),
                        false
                    ) && name.contains("log")
                }

        this.lastLogFile =
                File("${this.applicationContext.getExternalFilesDir(null)?.absolutePath}${File.separator}$logFileName")

        if (this.lastLogFile.exists()) {
            this.launch {
                val stringFromFile =
                    async(Dispatchers.IO) { this@LoggerActivity.lastLogFile.readText(Charsets.UTF_8) }

                this@LoggerActivity.showProgress()
                delay(timeMillis = 100)
                this@LoggerActivity.logView.text = stringFromFile.await()
                this@LoggerActivity.hideProgress()
            }
        } else {
            Toast.makeText(this, R.string.dmErrorEmptyLastLogFile, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.job.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.dev_menu_logger, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
            }
            R.id.dmShare -> {
                if (this.allLogFileList.count() > 0) {
                    val uriList = this.allLogFileList.map {
                        FileProvider.getUriForFile(
                            this,
                            this.resources.getString(R.string.dmFileProviderAuthority),
                            it
                        )
                    } as ArrayList<Uri>

                    val sendIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND_MULTIPLE
                        this.type = "text/plain"
                        this.putExtra(Intent.EXTRA_SUBJECT, "Logs")
                        this.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
                        this.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }

                    startActivity(
                        Intent.createChooser(
                            sendIntent,
                            this.resources.getText(R.string.dmTitleShare)
                        )
                    )
                } else {
                    Toast.makeText(this, R.string.dmErrorNoLogFiles, Toast.LENGTH_LONG).show()
                }
            }
            R.id.dmClear -> {
                this.lastLogFile.writeText("", Charsets.UTF_8)
                this.logView.text = ""
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgress() {
        this.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        this.progressBar.visibility = View.GONE
    }
}