package com.etienne.pimpmyhair.main.presentation

import android.os.Bundle
import com.etienne.libraries.archi.view.RelaunchingActivity
import com.etienne.pimpmyhair.App
import com.etienne.pimpmyhair.AppInjector
import com.etienne.pimpmyhair.R
import javax.inject.Inject


class MainActivity : RelaunchingActivity() {

    companion object {
        var isInitialized = false
    }

    init {
        isInitialized = true
    }

    @Inject
    lateinit var appInjector: AppInjector

    @Inject
    lateinit var coordinator: MainCoordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent
            .provideAppInjector()
            .createMainComponent(findViewById(android.R.id.content))
            .inject(this)

        coordinator.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            coordinator.onRelease()
            appInjector.releaseMainComponent()
        }
    }
}
