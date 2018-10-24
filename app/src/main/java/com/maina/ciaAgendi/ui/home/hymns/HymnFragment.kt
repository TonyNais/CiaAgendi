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

package com.maina.ciaAgendi.ui.home.hymns

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.data.model.Hymn
import com.maina.ciaAgendi.ui.home.hymns.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_hymn.*

class HymnFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hymn, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_hymns, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.action_search -> {
                SearchActivity.launch(activity!!, RC_SEARCH)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hymn: Hymn? = arguments?.getParcelable(HYMN)

        hymnText.text = Html.fromHtml(hymn?.body)
    }

    companion object {

        private const val HYMN = "HYMN"

        const val RC_SEARCH = 123

        fun newInstance(hymn: Hymn): HymnFragment {

            val bundle = Bundle().apply {
                putParcelable(HYMN, hymn)
            }

            return HymnFragment().apply { arguments = bundle }
        }
    }
}