package com.etienne.pimpmyhair.main.processing

import android.content.ContentResolver
import android.view.ViewGroup
import com.etienne.pimpmyhair.ComputationScheduler
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher
import com.etienne.pimpmyhair.main.processing.domain.ProcessingViewInteractor
import com.etienne.pimpmyhair.main.processing.presentation.ProcessingViewCoordinator
import com.etienne.pimpmyhair.main.processing.presentation.ProcessingViewHolder
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Scope

@ProcessingViewScope
@Subcomponent(modules = [ProcessingViewComponent.Module::class])
interface ProcessingViewComponent {

    fun coordinator(): ProcessingViewCoordinator
    fun viewHolder(): ProcessingViewHolder
    fun interactor(): ProcessingViewInteractor

    @Subcomponent.Builder
    interface Builder {
        fun module(module: Module): Builder
        fun build(): ProcessingViewComponent
    }

    @dagger.Module
    class Module(private val container: ViewGroup) {

        @Provides
        @ProcessingViewScope
        internal fun provideCoordinator(
            component: ProcessingViewComponent,
        ): ProcessingViewCoordinator = ProcessingViewCoordinator(component)

        @Provides
        @ProcessingViewScope
        internal fun provideInteractor(
            applicationState: ApplicationState,
            photoLibraryLauncher: PhotoLibraryLauncher,
            contentResolver: ContentResolver,
            @ComputationScheduler computationScheduler: Scheduler,
        ): ProcessingViewInteractor =
            ProcessingViewInteractor(
                applicationState,
                photoLibraryLauncher,
                contentResolver,
                computationScheduler
            )

        @Provides
        @ProcessingViewScope
        internal fun provideViewHolder(interactor: ProcessingViewInteractor): ProcessingViewHolder =
            ProcessingViewHolder(container, interactor)
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProcessingViewScope
