package com.etienne.pimpmyhair.main

import android.content.ContentResolver
import android.view.ViewGroup
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.empty.EmptyViewComponent
import com.etienne.pimpmyhair.main.history.HistoryViewComponent
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import com.etienne.pimpmyhair.main.presentation.MainActivity
import com.etienne.pimpmyhair.main.presentation.MainCoordinator
import com.etienne.pimpmyhair.main.processing.ProcessingViewComponent
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher
import com.etienne.pimpmyhair.main.result.ResultViewComponent
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

@MainActivityScope
@Subcomponent(modules = [MainComponent.Module::class])
interface MainComponent {
    fun inject(activity: MainActivity)

    @dagger.Module(
        subcomponents = [
            EmptyViewComponent::class,
            ProcessingViewComponent::class,
            ResultViewComponent::class,
            HistoryViewComponent::class
        ]
    )
    class Module(
        private val parent: ViewGroup,
        private val photoLibraryLauncher: PhotoLibraryLauncher,
        private val contentResolver: ContentResolver
    ) {

        @Provides
        @MainActivityScope
        internal fun provideCoordinator(
            component: MainComponent,
            resultHistoryInteractor: ResultHistoryInteractor,
            emptyViewBuilder: EmptyViewComponent.Builder,
            processingViewBuilder: ProcessingViewComponent.Builder,
            resultViewBuilder: ResultViewComponent.Builder,
            historyViewBuilder: HistoryViewComponent.Builder
        ): MainCoordinator =
            MainCoordinator(
                parent,
                component,
                resultHistoryInteractor,
                emptyViewBuilder,
                processingViewBuilder,
                resultViewBuilder,
                historyViewBuilder
            )

        @Provides
        @MainActivityScope
        internal fun provideApplicationState(
            coordinator: MainCoordinator
        ): ApplicationState =
            coordinator

        @Provides
        @MainActivityScope
        internal fun providePhotoLibraryLauncher(): PhotoLibraryLauncher = photoLibraryLauncher

        @Provides
        @MainActivityScope
        internal fun provideContentResolver(): ContentResolver = contentResolver
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope
