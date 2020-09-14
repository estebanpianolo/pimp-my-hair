package com.etienne.pimpmyhair.main.history.presentation

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etienne.pimpmyhair.R
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.itemview_history.view.*

class HistoryViewAdapter() :
    RecyclerView.Adapter<HistoryViewAdapter.ViewHolder>() {

    var list = listOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var onClickListener: ((Int) -> Unit) = {}

    val onItemClick: Observable<Int> =
        Observable.create { emitter ->
            onClickListener = {
                if (!emitter.isDisposed) {
                    emitter.onNext(it)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

    }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.itemview_history, parent, false
        )
    ) {

        init {
            itemView.setOnClickListener { onClickListener(layoutPosition) }
        }

        fun bind(item: Item) {
            itemView.itemview_image.setImageBitmap(item.image)
        }

    }

    override fun getItemCount() = list.size

    data class Item(val image: Bitmap)
}
