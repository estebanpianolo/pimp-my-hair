package com.etienne.pimpmyhair

import com.etienne.pimpmyhair.main.MainComponent

class AppInjectorImpl(override val appComponent: AppComponent) : AppInjector {

    //region HomeLoyaltyScope Component
    private var mainComponent: MainComponent? = null

    override fun createMainComponent(): MainComponent =
        appComponent.plus(
            MainComponent.Module()
        ).apply {
            mainComponent = this
        }

    override fun releaseMainComponent() {
        mainComponent = null
    }
    //endRegion

}

interface AppInjector {

    abstract fun createMainComponent(): MainComponent
    fun releaseMainComponent()

    val appComponent: AppComponent
}
