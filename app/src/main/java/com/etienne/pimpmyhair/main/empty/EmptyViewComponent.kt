package com.etienne.pimpmyhair.main.empty

import android.view.ViewGroup
import com.etienne.pimpmyhair.main.empty.domain.EmptyViewInteractor
import com.etienne.pimpmyhair.main.empty.presentation.EmptyViewCoordinator
import com.etienne.pimpmyhair.main.empty.presentation.EmptyViewHolder
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

@EmptyViewScope
@Subcomponent(modules = [EmptyViewComponent.Module::class])
interface EmptyViewComponent {

    fun coordinator(): EmptyViewCoordinator
    fun viewHolder(): EmptyViewHolder
    fun interactor(): EmptyViewInteractor

    @Subcomponent.Builder
    interface Builder {
        fun module(module: Module): Builder
        fun build(): EmptyViewComponent
    }

    @dagger.Module
    class Module(private val container: ViewGroup) {

        @Provides
        internal fun provideCoordinator(
            component: EmptyViewComponent,
        ): EmptyViewCoordinator = EmptyViewCoordinator(component)

        @Provides
        internal fun provideInteractor(applicationState: ApplicationState): EmptyViewInteractor =
            EmptyViewInteractor(applicationState)

        @Provides
        internal fun provideViewHolder(interactor: EmptyViewInteractor): EmptyViewHolder =
            EmptyViewHolder(container, interactor)
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EmptyViewScope
