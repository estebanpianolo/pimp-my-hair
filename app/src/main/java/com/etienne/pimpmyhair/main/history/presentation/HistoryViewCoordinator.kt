package com.etienne.pimpmyhair.main.history.presentation

import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.pimpmyhair.main.history.HistoryViewComponent

class HistoryViewCoordinator(component: HistoryViewComponent) : Coordinator<HistoryViewComponent>(component){
    override fun start() {
        component.viewHolder().addOnRootView()
    }

    override fun onRelease() {
        component.viewHolder().removeFromRootView()
        component.interactor().clear()
    }
}
