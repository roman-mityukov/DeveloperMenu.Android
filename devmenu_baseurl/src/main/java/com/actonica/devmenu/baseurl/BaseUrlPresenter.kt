package com.actonica.devmenu.baseurl

internal interface BaseUrlPresenterInput {
    fun changeBaseUrlPreset(url: String?)
    fun changeBaseUrlManual(url: String?)
    fun attachOutput(output: BaseUrlPresenterOutput)
    fun detachOutput()
    fun onStart()
}

internal interface BaseUrlPresenterOutput {
    fun onChange(model: BaseUrlModel)
}

internal class BaseUrlPresenter(
    private var output: BaseUrlPresenterOutput?,
    private val baseUrlStorage: BaseUrlStorage
) : BaseUrlPresenterInput {
    override fun attachOutput(output: BaseUrlPresenterOutput) {
        this.output = output
    }

    override fun detachOutput() {
        this.output = null
    }

    override fun changeBaseUrlManual(url: String?) {
        try {
            this.baseUrlStorage.baseUrlManual = url
        } catch (e: InvalidUrlException) {
            this.output?.onChange(model = BaseUrlModel.InvalidUrlModel())
        }
        changeOutputWithDefaultModel()
    }

    override fun changeBaseUrlPreset(url: String?) {
        try {
            this.baseUrlStorage.baseUrlPreset = url
        } catch (e: InvalidUrlException) {
            this.output?.onChange(model = BaseUrlModel.InvalidUrlModel())
        }
        changeOutputWithDefaultModel()
    }

    override fun onStart() {
        changeOutputWithDefaultModel()
    }

    private fun changeOutputWithDefaultModel() {
        this.output?.onChange(
            BaseUrlModel.BaseUrlState(
                baseUrlManual = this.baseUrlStorage.baseUrlManual,
                baseUrlPreset = this.baseUrlStorage.baseUrlPreset
            )
        )
    }
}