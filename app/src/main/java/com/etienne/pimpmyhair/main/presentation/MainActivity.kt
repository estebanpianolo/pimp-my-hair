package com.etienne.pimpmyhair.main.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.etienne.libraries.archi.view.RelaunchingActivity
import com.etienne.pimpmyhair.App
import com.etienne.pimpmyhair.AppInjector
import com.etienne.pimpmyhair.R
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher
import javax.inject.Inject


class MainActivity : RelaunchingActivity(), PhotoLibraryLauncher {

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
            .createMainComponent(findViewById(android.R.id.content), this, contentResolver)
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

    override fun showPhotoLibrary(requestCode: Int) {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        coordinator.activityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if(!coordinator.backPressed()) {
            super.onBackPressed()
        }

    }
}
