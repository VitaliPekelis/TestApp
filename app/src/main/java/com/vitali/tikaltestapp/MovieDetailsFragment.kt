package com.vitali.tikaltestapp

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.movie_details_fragment.*


class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
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

        viewModel.clickedMovie.observe(this, Observer {
            movie_details_title_tv.text = it.title
        })
    }


}
