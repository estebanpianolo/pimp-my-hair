package com.etienne.pimpmyhair

import android.view.ViewGroup
import com.etienne.pimpmyhair.main.MainComponent

class AppInjectorImpl(override val appComponent: AppComponent) : AppInjector {

    //region HomeLoyaltyScope Component
    private var mainComponent: MainComponent? = null

    override fun createMainComponent(parent: ViewGroup): MainComponent =
        appComponent.plus(
            MainComponent.Module(parent)
        ).apply {
            mainComponent = this
        }

    override fun releaseMainComponent() {
        mainComponent = null
    }
    //endRegion

}

interface AppInjector {

    abstract fun createMainComponent(parent: ViewGroup): MainComponent
    fun releaseMainComponent()

    val appComponent: AppComponent
}
