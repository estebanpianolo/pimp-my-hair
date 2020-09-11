package com.etienne.pimpmyhair.main.empty.domain

import com.etienne.libraries.archi.nucleus.*
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

class EmptyViewInteractor(private val applicationState: ApplicationState) : NucleusInteractorImpl<Unit>(Unit) {
    override val reducerConfigurator: NucleusReducerConfigurator<Unit> = {
        StartProcessButtonTaped::class changesCommand  {
            simpleCommand {
                applicationState.showProcessingScreen()
            }
        }
    }
}

object StartProcessButtonTaped : Action
