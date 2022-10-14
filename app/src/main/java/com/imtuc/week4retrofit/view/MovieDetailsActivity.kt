package com.imtuc.week4retrofit.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imtuc.week4retrofit.adapter.GenresAdapter
import com.imtuc.week4retrofit.adapter.NowPlayingAdapter
import com.imtuc.week4retrofit.adapter.ProductionCompanyAdapter
import com.imtuc.week4retrofit.adapter.SpokenLanguageAdapter
import com.imtuc.week4retrofit.databinding.ActivityMovieDetailsBinding
import com.imtuc.week4retrofit.helper.Const
import com.imtuc.week4retrofit.model.Genre
import com.imtuc.week4retrofit.model.SpokenLanguage
import com.imtuc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private var movieID = 0
    private lateinit var adapter1: GenresAdapter
    private lateinit var adapter2: ProductionCompanyAdapter
    private lateinit var adapter3: SpokenLanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressIndicator.visibility = View.INVISIBLE

        GetIntent()

        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        setViewModel()
    }

    private fun GetIntent() {
        movieID = intent.getIntExtra("movieID", 0)
    }

    private fun setViewModel() {
        moviesViewModel.getMovieDetails(movieID, Const.API_KEY)

        binding.progressIndicator.visibility = View.VISIBLE
        binding.svMoviedetails.visibility = View.INVISIBLE
        binding.ivPosterMoviedetails.visibility = View.INVISIBLE

        moviesViewModel.movieDetails.observe(this, Observer { response ->
            binding.tvTitleMoviedetails.text = response.title
            binding.tvOverview.text = response.overview

            Glide.with(applicationContext)
                .load(Const.IMG_URL + response.backdrop_path)
                .into(binding.ivPosterMoviedetails)

            binding.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapter1 = GenresAdapter(response.genres)
            binding.rvGenre.adapter = adapter1

            var a = ArrayList(response.production_companies)

            for (comp in response.production_companies) {
                if (comp.logo_path == null) {
                    a.remove(comp)
                }
            }

            binding.rvProductioncompany.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapter2 = ProductionCompanyAdapter(a)
            binding.rvProductioncompany.adapter = adapter2

            binding.rvSpokenlanguage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapter3 = SpokenLanguageAdapter(response.spoken_languages)
            binding.rvSpokenlanguage.adapter = adapter3

            binding.progressIndicator.visibility = View.INVISIBLE
            binding.svMoviedetails.visibility = View.VISIBLE
            binding.ivPosterMoviedetails.visibility = View.VISIBLE
        })
    }
}