package com.etienne.pimpmyhair.main.result.presentation

import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.pimpmyhair.main.empty.EmptyViewComponent
import com.etienne.pimpmyhair.main.result.ResultViewComponent

class ResultViewCoordinator(component: ResultViewComponent) : Coordinator<ResultViewComponent>(component){
    override fun start() {
        component.viewHolder().addOnRootView()
    }

    override fun onRelease() {
        component.viewHolder().removeFromRootView()
        component.interactor().clear()
    }
}
