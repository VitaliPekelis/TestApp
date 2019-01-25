package com.vitali.tikaltestapp


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.vitali.scanovatetest.Logger
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel :MainActivityViewModel
    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this,
            Injection.provideViewModelFactory(this)).get(MainActivityViewModel::class.java)

        setupRecyclerView()

        viewModel.getMovies()
    }

    private fun setupRecyclerView() {
        setUpListeners()
        setUpAdapter()
    }

    private fun setUpListeners() {
        val layoutManager = list_rv.layoutManager as androidx.recyclerview.widget.GridLayoutManager
        list_rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })

    }

    private fun setUpAdapter()
    {
        list_rv.adapter = this.adapter

        viewModel.movies.observe(this, Observer<List<Movie>> {
            Logger.logDebug("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })


        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })


        adapter.submitList(null)
    }

    private fun showEmptyList(show: Boolean)
    {
        if (show)
        {
            emptyList_tv.visibility = View.VISIBLE
            list_rv.visibility = View.GONE
        }
        else
        {
            emptyList_tv.visibility = View.GONE
            list_rv.visibility = View.VISIBLE
        }
    }


}
