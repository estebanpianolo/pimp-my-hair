package com.etienne.pimpmyhair.main.result.presentation

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.etienne.libraries.archi.nucleus.NucleusInteractor
import com.etienne.libraries.archi.view.Dispatcher
import com.etienne.libraries.archi.view.ModelWatcherConfigurator
import com.etienne.libraries.archi.view.NucleusViewHolder
import com.etienne.libraries.archi.view.dispatch
import com.etienne.pimpmyhair.R
import com.etienne.pimpmyhair.main.result.domain.ResultViewState
import kotlinx.android.synthetic.main.view_result.*
import kotlinx.android.synthetic.main.view_result.view.*


class ResultViewHolder(rootView: ViewGroup, interactor: NucleusInteractor<ResultViewState>) :
    NucleusViewHolder<ConstraintLayout, ResultViewState>(rootView, R.layout.view_result, interactor) {

    override val modelWatchConfigurator: ModelWatcherConfigurator<ResultViewState> = {
        watch {
            contentView.originalImage.setImageBitmap(it.result.originalImage)
            contentView.processedImage.setImageBitmap(it.result.processedImage)
        }
    }

    override val dispatcher: Dispatcher = dispatch(

    )
}
