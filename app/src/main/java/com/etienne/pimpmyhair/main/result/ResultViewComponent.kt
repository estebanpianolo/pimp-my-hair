package com.etienne.pimpmyhair.main.result

import android.view.ViewGroup
import com.etienne.pimpmyhair.domain.Result
import com.etienne.pimpmyhair.main.result.domain.ResultViewInteractor
import com.etienne.pimpmyhair.main.result.presentation.ResultViewCoordinator
import com.etienne.pimpmyhair.main.result.presentation.ResultViewHolder
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

@EmptyViewScope
@Subcomponent(modules = [ResultViewComponent.Module::class])
interface ResultViewComponent {

    fun coordinator(): ResultViewCoordinator
    fun viewHolder(): ResultViewHolder
    fun interactor(): ResultViewInteractor

    @Subcomponent.Builder
    interface Builder {
        fun module(module: Module): Builder
        fun build(): ResultViewComponent
    }

    @dagger.Module
    class Module(private val container: ViewGroup, private val result: Result) {

        @Provides
        @EmptyViewScope
        internal fun provideCoordinator(
            component: ResultViewComponent,
        ): ResultViewCoordinator = ResultViewCoordinator(component)

        @Provides
        @EmptyViewScope
        internal fun provideInteractor(): ResultViewInteractor =
            ResultViewInteractor(result)

        @Provides
        @EmptyViewScope
        internal fun provideViewHolder(interactor: ResultViewInteractor): ResultViewHolder =
            ResultViewHolder(container, interactor)
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class EmptyViewScope
