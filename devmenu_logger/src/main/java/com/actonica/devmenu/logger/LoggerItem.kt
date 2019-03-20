package com.actonica.devmenu.logger

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.actonica.devmenu.contract.DefaultDevMenuItem
import kotlinx.android.synthetic.main.dm_logger_view.view.*
import org.apache.logging.log4j.Level

class LoggerItem(private val applicationContext: Context, private val logFileName: String) :
    DefaultDevMenuItem(), LoggerPresenterOutput {

    companion object {
        const val INTENT_EXTRA_LOG_FILE_NAME: String = "logPath"
    }

    private lateinit var view: View
    private lateinit var presenter: LoggerPresenterInput
    private lateinit var arrayAdapter: ArrayAdapter<Level>
    override var model: LoggerModel.LoggerState? = null
    private var isModelUpdate: Boolean = false

    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        this.view = layoutInflater.inflate(R.layout.dm_logger_view, viewGroup, false)

        this.presenter = LoggerPresenter(
            output = this,
            loggerStateHandler = LoggerStateHandlerProvider.provideLoggerStateHandler(
                PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
            ),
            loggerFileHandler = LoggerFileHandler(this.applicationContext)
        )

        this.view.buttonLogger.setOnClickListener {
            val loggerIntent = Intent(viewGroup.context, LoggerActivity::class.java)
            loggerIntent.putExtra(INTENT_EXTRA_LOG_FILE_NAME, this.logFileName)
            loggerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            viewGroup.context.startActivity(loggerIntent)
        }

        this.view.buttonClear.setOnClickListener {
            this.presenter.deleteLogs(logFileName)
        }

        this.view.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            this.presenter.setIsLoggingInFileEnabled(isLogInFileEnabled = isChecked)
        }

        this.arrayAdapter =
            ArrayAdapter(
                viewGroup.context,
                android.R.layout.simple_spinner_dropdown_item,
                arrayListOf(Level.ALL, Level.DEBUG, Level.ERROR, Level.INFO, Level.WARN)
            )
        this.view.spinnerLogLevel.adapter = arrayAdapter
        this.view.spinnerLogLevel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (this@LoggerItem.isModelUpdate) {
                        this@LoggerItem.isModelUpdate = false
                        return
                    }

                    arrayAdapter.getItem(position)?.let {
                        presenter.setLogLevel(it)
                    }
                }
            }

        this.presenter.onStart()

        return view
    }

    override fun onChange(model: LoggerModel) {
        when (model) {
            is LoggerModel.LoggerState -> {
                this.model = model
                this.isModelUpdate = true
                this.view.checkBox.isChecked = model.isLogInFileEnabled
                this.view.spinnerLogLevel.setSelection(arrayAdapter.getPosition(model.logLevel))
            }
            is LoggerModel.DeletingComplete -> {
                Toast.makeText(
                    this.applicationContext,
                    R.string.dmToastMessageDeletingComplete,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is LoggerModel.DeletingFailed -> {
                Toast.makeText(
                    this.applicationContext,
                    R.string.dmToastMessageDeletingFailed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (this::presenter.isInitialized) this.presenter.attachOutput(output = this)
    }

    override fun onStop() {
        super.onStop()
        if (this::presenter.isInitialized) this.presenter.detachOutput()
    }
}