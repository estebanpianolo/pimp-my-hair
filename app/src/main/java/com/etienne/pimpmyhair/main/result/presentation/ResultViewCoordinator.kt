package com.etienne.pimpmyhair.main.result.presentation

import com.etienne.libraries.archi.coordinator.BackPressedListener
import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.pimpmyhair.main.result.ResultViewComponent
import com.etienne.pimpmyhair.main.result.domain.BackPressed

class ResultViewCoordinator(component: ResultViewComponent) : Coordinator<ResultViewComponent>(component) , BackPressedListener{
    override fun start() {
        component.viewHolder().addOnRootView()
    }

    override fun onRelease() {
        component.viewHolder().removeFromRootView()
        component.interactor().clear()
    }

    override fun onBackPressed(): Boolean {
        component.interactor().actions.onNext(BackPressed)
        return true
    }
}
