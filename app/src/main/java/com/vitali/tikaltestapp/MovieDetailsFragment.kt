package com.vitali.tikaltestapp

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
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
            updateUi(it)
        })
    }


    private fun updateUi(item: Movie) {

        //updateImdButton()
        //updateVideoButton()

        //item?.mId?.let { mViewModel.getMovieExternalIds(it) }
        //item?.mId?.let { mViewModel.getMovieVideos(it) }
        item.posterPath?.let {
            Picasso.get().load("https://image.tmdb.org/t/p/w300$it")
                .fit()
                .centerCrop()
                .into(iv_movieDetails_poster)
        }

        tv_movieDetails_releaseDate.text =
            if (item.releaseDate?.length ?: 0 >= 4)
                    item.releaseDate?.substring(0, 4)
            else ""

        movie_details_title_tv.text = item.title

        rtb_movieDetails_rating.rating = ((item.voteAverage) / 2).toFloat()
        tv_movieDetails_rating.text = getString(R.string.out_of_ten_rating, item.voteAverage)
        tv_movieDetails_overview.text = item.overview

        tv_movieDetails_NoneSelectedCover.visibility = View.GONE
    }


}
