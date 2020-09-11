package com.etienne.pimpmyhair.domain

import com.etienne.libraries.archi.nucleus.Action
import com.etienne.libraries.archi.nucleus.NucleusInteractorImpl
import com.etienne.libraries.archi.nucleus.NucleusReducerConfigurator

class ResultHistoryInteractor(repository: ResultHistoryRepository) :
    NucleusInteractorImpl<ResultHistoryState>(
        repository.getHistory().map { ResultHistoryState(history = it) }) {

    override val reducerConfigurator: NucleusReducerConfigurator<ResultHistoryState> = {
        AddResult::class changesState {
            copy(history = history + it.result)
        }
    }
}

data class ResultHistoryState(val history: List<Result>)

data class AddResult(val result: Result) : Action
