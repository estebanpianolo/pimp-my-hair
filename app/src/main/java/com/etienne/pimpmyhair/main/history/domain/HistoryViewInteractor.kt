package com.etienne.pimpmyhair.main.history.domain

import android.graphics.Bitmap
import com.etienne.libraries.archi.nucleus.Action
import com.etienne.libraries.archi.nucleus.NucleusInteractorImpl
import com.etienne.libraries.archi.nucleus.NucleusReducerConfigurator
import com.etienne.libraries.archi.nucleus.simpleCommand
import com.etienne.libraries.pratik.rx.relay
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class HistoryViewInteractor(
    private val applicationState: ApplicationState,
    resultHistoryInteractor: ResultHistoryInteractor
) : NucleusInteractorImpl<HistoryListState>(HistoryListState(listOf())) {

    private val compositeDisposable = CompositeDisposable()

    override val reducerConfigurator: NucleusReducerConfigurator<HistoryListState> = {
        UpdateHistoryList::class changesState { HistoryListState(it.historyList) }
        StartNewProcess::class changesCommand {
            simpleCommand { applicationState.showProcessingScreen() }
        }
        ItemClicked::class changesCommand  {
            simpleCommand { applicationState.showResultAt(it.position) }
        }
    }

    init {
        resultHistoryInteractor.state
            .map { state -> UpdateHistoryList(state.history.map { it.originalImage }) }
            .relay(actions)
            .addTo(compositeDisposable)
    }

    override fun clear() {
        super.clear()
        compositeDisposable.clear()
    }
}

data class UpdateHistoryList(val historyList: List<Bitmap>) : Action
data class ItemClicked(val position: Int) : Action
object StartNewProcess : Action

data class HistoryListState(val historyList: List<Bitmap>)
