package com.vitali.tikaltestapp

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vitali.scanovatetest.Logger
import kotlinx.android.synthetic.main.movies_fragment.*


class MoviesFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MainActivityViewModel

    private val adapter = MoviesAdapter { clickedMovie ->

        Logger.logDebug(logText = "Clicked Movie is ${clickedMovie.title}")
        viewModel.clickedMovie.postValue(clickedMovie)
    }
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activity?.let {
            viewModel = ViewModelProviders.of(
                it,
                Injection.provideViewModelFactory(it)).get(MainActivityViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getMovies()
    }



    private fun setupRecyclerView() {
        setUpLayoutManager()
        setUpAdapter()
    }

    private fun setUpLayoutManager()
    {
        val columnNum = resources.getInteger(R.integer.column_num)
        layoutManager = GridLayoutManager(activity, columnNum)
        list_rv.layoutManager = this.layoutManager
    }


    private fun setUpAdapter()
    {
        list_rv.adapter = this.adapter

        viewModel.movies.observe(this, Observer<PagedList<Movie>> {
            Logger.logDebug("MoviesFragment", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })


        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(activity, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
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
