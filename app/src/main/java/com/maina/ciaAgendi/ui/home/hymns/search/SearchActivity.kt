package com.maina.ciaAgendi.ui.home.hymns.search

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import com.maina.ciaAgendi.R
import com.maina.ciaAgendi.data.model.Hymn
import com.maina.ciaAgendi.di.ViewModelFactory
import com.maina.ciaAgendi.ui.base.BaseThemedActivity
import com.maina.ciaAgendi.utils.getViewModel
import com.maina.ciaAgendi.utils.hide
import com.maina.ciaAgendi.utils.show
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseThemedActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchViewModel

    private lateinit var pagerAdapter: SearchResultsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.search(query)
                return true
            }
        })

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.searchResults.observe(this, Observer {
            when {
                it == null || it.isEmpty() -> {
                    viewPager.hide()
                    tabLayout.hide()
                }
                else -> showResults(it)
            }
        })

        viewModel.search("")
    }

    private fun showResults(hymns: List<Hymn>) {
        pagerAdapter = SearchResultsPagerAdapter(supportFragmentManager, hymns) {
            val args = Bundle().apply { putParcelable(SELECTED_HYMN, it) }
            val data = Intent().apply { putExtras(args) }

            setResult(Activity.RESULT_OK, data)
            supportFinishAfterTransition()
        }

        viewPager.adapter = pagerAdapter

        if (hymns.map { it.language }.distinct().size > 1) {
            tabLayout.setupWithViewPager(viewPager)

            tabLayout.show()
        } else {
            tabLayout.hide()
        }
        viewPager.show()
    }

    companion object {

        const val SELECTED_HYMN = "SELECTED_HYMN"

        fun launch(activity: Activity, resultCode: Int) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivityForResult(intent, resultCode)
        }
    }
}