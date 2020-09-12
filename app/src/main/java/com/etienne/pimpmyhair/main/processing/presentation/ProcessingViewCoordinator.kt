package com.etienne.pimpmyhair.main.processing.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.etienne.libraries.archi.coordinator.ActivityResultHandler
import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.pimpmyhair.main.processing.ProcessingViewComponent

class ProcessingViewCoordinator(component: ProcessingViewComponent) :
    Coordinator<ProcessingViewComponent>(component), ActivityResultHandler {
    override fun start() {
        component.viewHolder().addOnRootView()
    }

    override fun onRelease() {
        component.viewHolder().removeFromRootView()
        component.interactor().clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        component.interactor().onActivityResult(requestCode, resultCode, data)


}
