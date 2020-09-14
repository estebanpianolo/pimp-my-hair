package com.etienne.pimpmyhair.main.presentation

import android.view.ViewGroup
import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.libraries.pratik.compat.Optional
import com.etienne.pimpmyhair.domain.Result
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.MainComponent
import com.etienne.pimpmyhair.main.empty.EmptyViewComponent
import com.etienne.pimpmyhair.main.empty.presentation.EmptyViewCoordinator
import com.etienne.pimpmyhair.main.history.HistoryViewComponent
import com.etienne.pimpmyhair.main.processing.ProcessingViewComponent
import com.etienne.pimpmyhair.main.processing.presentation.ProcessingViewCoordinator
import com.etienne.pimpmyhair.main.result.ResultViewComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class MainCoordinator(
    private val parent: ViewGroup,
    component: MainComponent,
    private val resultHistoryInteractor: ResultHistoryInteractor,
    private val emptyViewBuilder: EmptyViewComponent.Builder,
    private val processingViewBuilder: ProcessingViewComponent.Builder,
    private val resultViewBuilder: ResultViewComponent.Builder,
    private val historyViewBuilder: HistoryViewComponent.Builder,
) : Coordinator<MainComponent>(component), ApplicationState {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        historyViewBuilder.module(HistoryViewComponent.Module(parent))
            .build()
            .coordinator()
            .apply {
                start()
                this@MainCoordinator.attachCoordinator(this)
            }
        registerForHistoryUpdates()
    }

    override fun onRelease() {
        disposables.clear()
    }

    private fun showHistoryScreen() {
        detachCoordinator(EmptyViewCoordinator::class)
    }

    private fun hideHistory() {
        emptyViewBuilder.module(EmptyViewComponent.Module(parent))
            .build()
            .coordinator()
            .apply {
                start()
                this@MainCoordinator.attachCoordinator(this)
            }
    }

    override fun showProcessingScreen() {
        processingViewBuilder.module(ProcessingViewComponent.Module(parent))
            .build()
            .coordinator()
            .apply {
                start()
                this@MainCoordinator.attachCoordinator(this)
            }

    }

    override fun hideProcessingScreen() {
        detachCoordinator(ProcessingViewCoordinator::class)
    }

    override fun showResultScreen(result: Result) {
        hideProcessingScreen()

        resultViewBuilder.module(ResultViewComponent.Module(parent, result))
            .build()
            .coordinator()
            .apply {
                start()
                this@MainCoordinator.attachCoordinator(this)
            }
    }

    override fun showResultAt(position: Int) {
        resultHistoryInteractor.state
            .take(1)
            .map { Optional.ofNullable(it.history.getOrNull(position)) }
            .subscribe {
                it.ifPresent { showResultScreen(it) }
            }.addTo(disposables)
    }

    private fun registerForHistoryUpdates() {
        resultHistoryInteractor.state.map { it.history.count() > 0 }
            .subscribe {
                if (it) {
                    showHistoryScreen()
                } else {
                    hideHistory()
                }
            }
            .addTo(disposables)
    }
}

interface ApplicationState {
    fun showProcessingScreen()
    fun hideProcessingScreen()
    fun showResultScreen(result: Result)
    fun showResultAt(position: Int)
}
