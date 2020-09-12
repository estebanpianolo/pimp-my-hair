package com.etienne.pimpmyhair.main.result.domain

import com.etienne.libraries.archi.nucleus.NucleusInteractorImpl
import com.etienne.libraries.archi.nucleus.NucleusReducerConfigurator
import com.etienne.pimpmyhair.domain.Result

class ResultViewInteractor(private val result: Result) :
    NucleusInteractorImpl<ResultViewState>(ResultViewState(result)) {
    override val reducerConfigurator: NucleusReducerConfigurator<ResultViewState> = {
    }
}

data class ResultViewState(val result: Result)
