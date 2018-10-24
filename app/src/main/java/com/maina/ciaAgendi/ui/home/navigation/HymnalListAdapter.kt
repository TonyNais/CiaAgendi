/*
 * Copyright (c) 2018. Tony Maina.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maina.ciaAgendi.ui.home.navigation

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.data.model.Hymnal
import com.maina.ciaAgendi.utils.inflateView
import com.maina.ciaAgendi.utils.random
import com.maina.ciaAgendi.utils.tint
import com.maina.ciaAgendi.utils.toColor
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hymnal_item.*

class HymnalListAdapter constructor(private val callback: (Hymnal) -> Unit) : RecyclerView.Adapter<HymnalListAdapter.Holder>() {

    var hymnals = arrayListOf<Hymnal>()
        set(value) {
            val callback = HymnalDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            field.clear()
            field.addAll(value)

            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder = Holder.inflate(parent)

    override fun getItemCount(): Int = hymnals.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val hymnal = hymnals[position]

        holder.bind(hymnal)
        holder.itemView.setOnClickListener { callback.invoke(hymnal) }
    }

    fun itemChanged(hymnal: Hymnal) {
        val item = hymnals.find { it.code == hymnal.code } ?: return
        notifyItemChanged(hymnals.indexOf(item))
    }

    class HymnalDiffCallBack(private val oldList: List<Hymnal>,
                             private val newList: List<Hymnal>) : DiffUtil.Callback() {

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldItem = oldList[oldPosition]
            val newItem = newList[newPosition]

            return oldItem.code == newItem.code && oldItem.available == newItem.available
        }

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldItem = oldList[oldPosition]
            val newItem = newList[newPosition]

            return oldItem.code == newItem.code && oldItem.available == newItem.available
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }

    class Holder constructor(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(hymnal: Hymnal) {
            name.text = hymnal.name
            language.text = hymnal.language

            val color = COLORS.random()?.toColor() ?: COLORS[0].toColor()
            icon.background.tint(color)

            contentView.alpha = if (hymnal.available) 1f else 0.3f
        }

        companion object {

            /**
             * Adventist Identity colors
             *
             * https://identity.adventist.org/global-elements/color/
             */
            private val COLORS = arrayListOf("#4b207f", "#5e3929", "#7f264a", "#2f557f", "#e36520", "#448d21", "#3e8391")

            fun inflate(parent: ViewGroup):
                    Holder = Holder(inflateView(R.layout.hymnal_item, parent, false))
        }

    }
}