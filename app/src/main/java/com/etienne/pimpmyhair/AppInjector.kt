package com.etienne.pimpmyhair

import android.content.ContentResolver
import android.view.ViewGroup
import com.etienne.pimpmyhair.main.MainComponent
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher

class AppInjectorImpl(override val appComponent: AppComponent) : AppInjector {

    //region HomeLoyaltyScope Component
    private var mainComponent: MainComponent? = null

    override fun createMainComponent(
        parent: ViewGroup,
        photoLibraryLauncher: PhotoLibraryLauncher,
        contentResolver: ContentResolver
    ): MainComponent =
        appComponent.plus(MainComponent.Module(parent, photoLibraryLauncher, contentResolver))
            .apply {
                mainComponent = this
            }

    override fun releaseMainComponent() {
        mainComponent = null
    }
    //endRegion

}

interface AppInjector {

    fun createMainComponent(
        parent: ViewGroup,
        photoLibraryLauncher: PhotoLibraryLauncher,
        contentResolver: ContentResolver
    ): MainComponent

    fun releaseMainComponent()

    val appComponent: AppComponent
}
