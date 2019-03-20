package com.actonica.devmenu.baseurl

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.actonica.devmenu.contract.DefaultDevMenuItem
import kotlinx.android.synthetic.main.dm_baseurl_view.view.*

/**
 * Used to edit the http client's baseUrl
 * @param baseUrls - base urls list
 */
class BaseUrlItem(
    private val baseUrls: List<String>?,
    private val applicationContext: Context
) : DefaultDevMenuItem(), BaseUrlPresenterOutput {

    override var model: BaseUrlModel? = null
    private var isModelUpdate: Boolean = false
    private lateinit var view: View
    private lateinit var presenter: BaseUrlPresenterInput
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun getView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {

        this.view = layoutInflater.inflate(R.layout.dm_baseurl_view, viewGroup, false)

        this.presenter = BaseUrlPresenter(
            output = this, baseUrlStorage = BaseUrlStorage(
                PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
            )
        )

        if (!this.baseUrls.isNullOrEmpty()) {
            val baseUrlsWithEmptyFirstElement = arrayListOf<String>()
            baseUrlsWithEmptyFirstElement.addAll(this.baseUrls)
            baseUrlsWithEmptyFirstElement.filter { it.isNotBlank() }
            baseUrlsWithEmptyFirstElement.add(0, "")

            this.arrayAdapter =
                ArrayAdapter(
                    viewGroup.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    baseUrlsWithEmptyFirstElement
                )
            this.view.baseUrlPreset.adapter = arrayAdapter

            this.view.baseUrlPreset.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        this@BaseUrlItem.presenter.changeBaseUrlPreset(null)
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        v: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (this@BaseUrlItem.isModelUpdate) {
                            this@BaseUrlItem.isModelUpdate = false
                            return
                        }

                        if (position == 0) {
                            this@BaseUrlItem.presenter.changeBaseUrlPreset(null)
                        } else {
                            this@BaseUrlItem.presenter.changeBaseUrlPreset(
                                this@BaseUrlItem.arrayAdapter.getItem(
                                    position
                                )
                            )
                        }
                    }
                }
        } else {
            this.view.baseUrlPreset.visibility = View.GONE
            this.view.baseUrlPresetLabel.visibility = View.GONE
        }

        this.view.baseUrlManual.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = this@BaseUrlItem.view.baseUrlManual.text?.toString()

                if (text.isNullOrEmpty()) {
                    this@BaseUrlItem.presenter.changeBaseUrlManual(null)
                } else {
                    this@BaseUrlItem.presenter.changeBaseUrlManual(text)
                }

                val inputMethodManager =
                    viewGroup.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewGroup.windowToken, 0)
            }
            true
        }

        this.presenter.onStart()

        return view
    }

    override fun onStart() {
        super.onStart()
        if (this::presenter.isInitialized) this.presenter.attachOutput(output = this)
    }

    override fun onStop() {
        super.onStop()
        if (this::presenter.isInitialized) this.presenter.detachOutput()
    }

    override fun onChange(model: BaseUrlModel) {
        when (model) {
            is BaseUrlModel.BaseUrlState -> {
                this.model = model

                if (!this.baseUrls.isNullOrEmpty() && this::arrayAdapter.isInitialized) {
                    this.isModelUpdate = true
                    if (model.baseUrlPreset != null) {
                        this.view.baseUrlPreset.setSelection(this.arrayAdapter.getPosition(model.baseUrlPreset))
                    } else {
                        this.view.baseUrlPreset.setSelection(0)
                    }
                }

                this.view.baseUrlManual.setText(model.baseUrlManual)
            }
            is BaseUrlModel.InvalidUrlModel -> {
                Toast.makeText(
                    this.applicationContext,
                    R.string.dmErrorInvalidUrl,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}