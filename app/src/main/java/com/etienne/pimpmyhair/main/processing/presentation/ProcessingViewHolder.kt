package com.etienne.pimpmyhair.main.processing.presentation

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.etienne.libraries.archi.nucleus.NucleusInteractor
import com.etienne.libraries.archi.view.Dispatcher
import com.etienne.libraries.archi.view.ModelWatcherConfigurator
import com.etienne.libraries.archi.view.NucleusViewHolder
import com.etienne.libraries.archi.view.dispatch
import com.etienne.libraries.pratik.rx.relay
import com.etienne.pimpmyhair.R
import com.etienne.pimpmyhair.main.processing.domain.PickPhoto
import com.etienne.pimpmyhair.main.processing.domain.ProcessingState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class ProcessingViewHolder(rootView: ViewGroup, private val interactor: NucleusInteractor<ProcessingState>) :
    NucleusViewHolder<ConstraintLayout, ProcessingState>(rootView, R.layout.view_processing, interactor) {

    override val modelWatchConfigurator: ModelWatcherConfigurator<ProcessingState> = {
        watch {
        }
    }

    override val dispatcher: Dispatcher = dispatch(
        Observable.just(PickPhoto)
    )
}
