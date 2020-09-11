package com.etienne.pimpmyhair.main.presentation

import android.view.ViewGroup
import com.etienne.libraries.archi.coordinator.Coordinator
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.MainComponent
import com.etienne.pimpmyhair.main.empty.EmptyViewComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class MainCoordinator(
    private val parent: ViewGroup,
    component: MainComponent,
    private val resultHistoryInteractor: ResultHistoryInteractor,
    private val emptyViewBuilder: EmptyViewComponent.Builder,
) : Coordinator<MainComponent>(component), ApplicationState {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        registerForHistoryUpdates()
    }

    override fun onRelease() {
        disposables.clear()
    }

    private fun showHistoryScreen() {
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
}
