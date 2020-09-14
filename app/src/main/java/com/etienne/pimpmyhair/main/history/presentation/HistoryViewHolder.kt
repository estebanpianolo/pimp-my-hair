package com.etienne.pimpmyhair.main.history.presentation

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.etienne.libraries.archi.nucleus.NucleusInteractor
import com.etienne.libraries.archi.view.Dispatcher
import com.etienne.libraries.archi.view.ModelWatcherConfigurator
import com.etienne.libraries.archi.view.NucleusViewHolder
import com.etienne.libraries.archi.view.dispatch
import com.etienne.pimpmyhair.R
import com.etienne.pimpmyhair.main.history.domain.HistoryListState
import com.etienne.pimpmyhair.main.history.domain.ItemClicked
import com.etienne.pimpmyhair.main.history.domain.StartNewProcess
import com.jakewharton.rxbinding4.appcompat.itemClicks
import kotlinx.android.synthetic.main.view_history.view.*

class HistoryViewHolder(rootView: ViewGroup, interactor: NucleusInteractor<HistoryListState>) :
    NucleusViewHolder<ConstraintLayout, HistoryListState>(
        rootView,
        R.layout.view_history,
        interactor
    ) {

    private val viewAdapter = HistoryViewAdapter()

    override val modelWatchConfigurator: ModelWatcherConfigurator<HistoryListState> = {
        HistoryListState::historyList { list ->
            viewAdapter.list = list.map { HistoryViewAdapter.Item(it) }
        }
    }
    override val dispatcher: Dispatcher = dispatch(
        contentView.toolbar.itemClicks()
            .filter { it.itemId == R.id.action_start_process }
            .map { StartNewProcess },
        viewAdapter.onItemClick.map { ItemClicked(it) }
    )

    override fun buildContentView() {
        super.buildContentView()
        val numberOfColumns =
            getNumberOfColumns(contentView.resources.getDimension(R.dimen.history_image_size))
        contentView.history_list.apply {
            layoutManager = GridLayoutManager(
                context,
                numberOfColumns
            )
            adapter = viewAdapter
            setHasFixedSize(true)
        }
        contentView.toolbar.inflateMenu(R.menu.menu_history_view)
    }

    private fun getNumberOfColumns(columnWidth: Float): Int {
        return context.resources.displayMetrics.run {
            (widthPixels / columnWidth).toInt()
        }
    }
}
