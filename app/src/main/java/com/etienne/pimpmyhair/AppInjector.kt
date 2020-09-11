package com.etienne.pimpmyhair

class AppInjectorImpl(override val appComponent: AppComponent) : AppInjector

interface AppInjector {
    val appComponent: AppComponent
}
