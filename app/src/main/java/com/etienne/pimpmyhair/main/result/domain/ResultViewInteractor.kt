package com.etienne.pimpmyhair.main.result.domain

import com.etienne.libraries.archi.nucleus.Action
import com.etienne.libraries.archi.nucleus.NucleusInteractorImpl
import com.etienne.libraries.archi.nucleus.NucleusReducerConfigurator
import com.etienne.libraries.archi.nucleus.simpleCommand
import com.etienne.pimpmyhair.domain.Result
import com.etienne.pimpmyhair.main.presentation.ApplicationState

class ResultViewInteractor(
    result: Result,
    private val applicationState: ApplicationState
) :
    NucleusInteractorImpl<ResultViewState>(ResultViewState(result)) {
    override val reducerConfigurator: NucleusReducerConfigurator<ResultViewState> = {
        BackPressed::class changesCommand {
            simpleCommand { applicationState.hideResultScreen() }
        }
    }
}

object BackPressed : Action

data class ResultViewState(val result: Result)
