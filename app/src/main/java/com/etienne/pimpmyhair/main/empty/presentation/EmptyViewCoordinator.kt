package com.etienne.pimpmyhair.main.empty.presentation

import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.pimpmyhair.main.empty.EmptyViewComponent

class EmptyViewCoordinator(component: EmptyViewComponent) : Coordinator<EmptyViewComponent>(component){
    override fun start() {
        component.viewHolder().addOnRootView()
    }

    override fun onRelease() {
        component.viewHolder().removeFromRootView()
        component.interactor().clear()
    }
}
