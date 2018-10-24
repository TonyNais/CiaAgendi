package com.maina.ciaAgendi.ui.home.hymns.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.data.model.Hymn
import com.maina.ciaAgendi.ui.home.hymns.HymnsListAdapter
import com.maina.ciaAgendi.utils.vertical
import kotlinx.android.synthetic.main.fragment_list.*

class SearchResultsPagerAdapter constructor(fragmentManager: FragmentManager,
                                            private val results: List<Hymn>,
                                            private val callback: (Hymn) -> Unit) : FragmentStatePagerAdapter(fragmentManager) {

    private val languages: List<String> = results.map { it.language }.distinct()

    override fun getItem(position: Int): Fragment {
        return SearchResultsFragment.newInstance(results.filter { it.language == languages[position] },
                callback)
    }

    override fun getCount(): Int = languages.size

    override fun getPageTitle(position: Int): CharSequence? = languages[position]
}

class SearchResultsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val results: ArrayList<Hymn> = arguments?.getParcelableArrayList(RESULTS) ?: return

        val hymnsListAdapter = HymnsListAdapter { callback?.invoke(it) }
        recyclerView.apply {
            vertical()
            adapter = hymnsListAdapter
        }

        hymnsListAdapter.hymns = results

    }

    companion object {

        private const val RESULTS = "RESULTS"
        private var callback: ((Hymn) -> Unit)? = null

        fun newInstance(results: List<Hymn>, callback: (Hymn) -> Unit): SearchResultsFragment {
            val args = Bundle().apply {
                putParcelableArrayList(RESULTS, ArrayList(results))
            }

            this.callback = callback

            return SearchResultsFragment().apply { arguments = args }
        }
    }
}