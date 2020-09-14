package com.etienne.pimpmyhair.main.history

import android.view.ViewGroup
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.history.domain.HistoryViewInteractor
import com.etienne.pimpmyhair.main.history.presentation.HistoryViewCoordinator
import com.etienne.pimpmyhair.main.history.presentation.HistoryViewHolder
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

@EmptyViewScope
@Subcomponent(modules = [HistoryViewComponent.Module::class])
interface HistoryViewComponent {

    fun coordinator(): HistoryViewCoordinator
    fun viewHolder(): HistoryViewHolder
    fun interactor(): HistoryViewInteractor

    @Subcomponent.Builder
    interface Builder {
        fun module(module: Module): Builder
        fun build(): HistoryViewComponent
    }

    @dagger.Module
    class Module(private val container: ViewGroup) {

        @Provides
        @EmptyViewScope
        internal fun provideCoordinator(
            component: HistoryViewComponent,
        ): HistoryViewCoordinator = HistoryViewCoordinator(component)

        @Provides
        @EmptyViewScope
        internal fun provideInteractor(
            applicationState: ApplicationState,
            resultHistoryInteractor: ResultHistoryInteractor
        ): HistoryViewInteractor =
            HistoryViewInteractor(applicationState, resultHistoryInteractor)

        @Provides
        @EmptyViewScope
        internal fun provideViewHolder(interactor: HistoryViewInteractor): HistoryViewHolder =
            HistoryViewHolder(container, interactor)
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EmptyViewScope
