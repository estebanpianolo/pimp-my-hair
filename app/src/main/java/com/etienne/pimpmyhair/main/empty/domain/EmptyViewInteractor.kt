package com.etienne.pimpmyhair.main.empty.domain

import com.etienne.libraries.archi.nucleus.Action
import com.etienne.libraries.archi.nucleus.NucleusInteractorImpl
import com.etienne.libraries.archi.nucleus.NucleusReducerConfigurator
import com.etienne.libraries.archi.nucleus.simpleCommand
import com.etienne.pimpmyhair.main.presentation.ApplicationState

class EmptyViewInteractor(private val applicationState: ApplicationState) :
    NucleusInteractorImpl<Unit>(Unit) {
    override val reducerConfigurator: NucleusReducerConfigurator<Unit> = {
        StartProcessButtonTaped::class changesCommand {
            simpleCommand {
                applicationState.showProcessingScreen()
            }
        }
    }
}

object StartProcessButtonTaped : Action
