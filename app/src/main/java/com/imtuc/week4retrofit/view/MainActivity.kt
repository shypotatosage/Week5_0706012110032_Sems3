package com.imtuc.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.imtuc.week4retrofit.adapter.NowPlayingAdapter
import com.imtuc.week4retrofit.databinding.ActivityMainBinding
import com.imtuc.week4retrofit.helper.Const
import com.imtuc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NowPlayingAdapter
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        moviesViewModel.getNowPlaying(Const.API_KEY, "en-US", 1)

        binding.progressIndicatorMain.visibility = View.VISIBLE
        binding.rvMain.visibility = View.INVISIBLE

        moviesViewModel.nowPlaying.observe(this, Observer { response ->
            binding.rvMain.layoutManager = LinearLayoutManager(this)
            adapter = NowPlayingAdapter(response)
            binding.rvMain.adapter = adapter

            binding.progressIndicatorMain.visibility = View.INVISIBLE
            binding.rvMain.visibility = View.VISIBLE
        })
    }
}