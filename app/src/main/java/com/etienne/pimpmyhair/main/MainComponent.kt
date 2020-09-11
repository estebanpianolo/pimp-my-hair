package com.etienne.pimpmyhair.main

import android.view.ViewGroup
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.empty.EmptyViewComponent
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import com.etienne.pimpmyhair.main.presentation.MainActivity
import com.etienne.pimpmyhair.main.presentation.MainCoordinator
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

@MainActivityScope
@Subcomponent(modules = [MainComponent.Module::class])
interface MainComponent {
    fun inject(activity: MainActivity)

    @dagger.Module(
        subcomponents = [
            EmptyViewComponent::class
        ]
    )
    class Module(private val parent: ViewGroup) {

        @Provides
        internal fun provideCoordinator(
            component: MainComponent,
            resultHistoryInteractor: ResultHistoryInteractor,
            emptyViewBuilder: EmptyViewComponent.Builder
        ): MainCoordinator =
            MainCoordinator(parent, component, resultHistoryInteractor, emptyViewBuilder)

        @Provides
        internal fun provideApplicationState(
            coordinator: MainCoordinator
        ): ApplicationState =
            coordinator
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope
