package com.vitali.tikaltestapp


import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vitali.scanovatetest.Logger
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel :MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieDetailsFragment = movie_details_fragment as? MovieDetailsFragment ?: MovieDetailsFragment.newInstance()

        viewModel = ViewModelProviders.of(
            this,
            Injection.provideViewModelFactory(this)).get(MainActivityViewModel::class.java)


        viewModel.clickedMovie.observe(this, Observer {

            val screenLayout = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

            if (screenLayout != Configuration.SCREENLAYOUT_SIZE_LARGE && screenLayout != Configuration.SCREENLAYOUT_SIZE_XLARGE)
            {
                // on all except large and extra large screen device ...
                Logger.logDebug(logText = "Open Movie Details Fragment")

                addFragment(R.id.fragments_container, movieDetailsFragment)
            }
        })
    }

}
