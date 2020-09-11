package com.etienne.pimpmyhair.main.empty.presentation

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.etienne.libraries.archi.nucleus.NucleusInteractor
import com.etienne.libraries.archi.view.Dispatcher
import com.etienne.libraries.archi.view.NucleusViewHolder
import com.etienne.libraries.archi.view.dispatch
import com.etienne.pimpmyhair.R
import com.etienne.pimpmyhair.main.empty.domain.StartProcessButtonTaped
import com.jakewharton.rxbinding4.view.clicks

class EmptyViewHolder(rootView: ViewGroup, interactor: NucleusInteractor<Unit>) :
    NucleusViewHolder<ConstraintLayout, Unit>(rootView, R.layout.view_empty, interactor) {

    override val dispatcher: Dispatcher = dispatch(
        findViewById<View>(R.id.button).clicks().map { StartProcessButtonTaped }
    )
}
