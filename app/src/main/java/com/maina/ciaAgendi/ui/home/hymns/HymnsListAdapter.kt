package com.maina.ciaAgendi.ui.home.hymns

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.data.model.Hymn
import com.maina.ciaAgendi.utils.inflateView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hymn_item.*

class HymnsListAdapter constructor(private val callback: (Hymn) -> Unit) : RecyclerView.Adapter<HymnsListAdapter.HymnHolder>() {

    var hymns: ArrayList<Hymn> = arrayListOf()
        set(value) {
            val callback = HymnsDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            field.clear()
            field.addAll(value)

            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HymnHolder = HymnHolder.inflate(parent)

    override fun getItemCount(): Int = hymns.size

    override fun onBindViewHolder(holder: HymnHolder, position: Int) {
        val hymn = hymns[position]

        holder.bind(hymn)

        holder.itemView.setOnClickListener {
            callback.invoke(hymn)
        }
    }

    class HymnsDiffCallBack(private val oldList: List<Hymn>,
                            private val newList: List<Hymn>) : DiffUtil.Callback() {

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldItem = oldList[oldPosition]
            val newItem = newList[newPosition]

            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldItem = oldList[oldPosition]
            val newItem = newList[newPosition]

            return oldItem.id == newItem.id
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }

    class HymnHolder constructor(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(hymn: Hymn) {
            name.text = hymn.name
        }

        companion object {
            fun inflate(parent: ViewGroup):
                    HymnHolder = HymnHolder(inflateView(R.layout.hymn_item, parent, false))
        }

    }
}