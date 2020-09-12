package com.etienne.pimpmyhair.main.processing

import android.content.ContentResolver
import android.view.ViewGroup
import com.etienne.libraries.network.NetworkConnector
import com.etienne.pimpmyhair.ComputationScheduler
import com.etienne.pimpmyhair.IOScheduler
import com.etienne.pimpmyhair.MainScheduler
import com.etienne.pimpmyhair.domain.ResultHistoryInteractor
import com.etienne.pimpmyhair.main.presentation.ApplicationState
import com.etienne.pimpmyhair.main.processing.data.ProcessingApi
import com.etienne.pimpmyhair.main.processing.data.ProcessingRepositoryImpl
import com.etienne.pimpmyhair.main.processing.domain.PhotoLibraryLauncher
import com.etienne.pimpmyhair.main.processing.domain.ProcessingRepository
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
            resultHistoryInteractor: ResultHistoryInteractor,
            contentResolver: ContentResolver,
            processingRepository: ProcessingRepository,
            @ComputationScheduler computationScheduler: Scheduler,
            @IOScheduler ioScheduler: Scheduler,
            @MainScheduler mainScheduler: Scheduler
        ): ProcessingViewInteractor =
            ProcessingViewInteractor(
                applicationState,
                photoLibraryLauncher,
                resultHistoryInteractor,
                contentResolver,
                processingRepository,
                computationScheduler,
                ioScheduler,
                mainScheduler
            )

        @Provides
        @ProcessingViewScope
        internal fun provideViewHolder(interactor: ProcessingViewInteractor): ProcessingViewHolder =
            ProcessingViewHolder(container, interactor)

        @Provides
        @ProcessingViewScope
        internal fun provideProcessingRepository(networkConnector: NetworkConnector): ProcessingRepository =
            ProcessingRepositoryImpl(networkConnector.create(ProcessingApi::class.java))
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProcessingViewScope
