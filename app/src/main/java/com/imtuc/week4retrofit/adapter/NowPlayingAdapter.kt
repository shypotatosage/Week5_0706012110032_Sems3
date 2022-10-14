package com.imtuc.week4retrofit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.imtuc.week4retrofit.R
import com.imtuc.week4retrofit.model.Result
import com.imtuc.week4retrofit.view.MovieDetailsActivity

class NowPlayingAdapter(private val dataSet: ArrayList<Result>) :
        RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val tvReleased: TextView
        val cvNowPlaying: CardView

        init {
            // Define click listener for the ViewHolder's View.
            tvTitle = view.findViewById(R.id.tv_title_nowplaying)
            tvReleased = view.findViewById(R.id.tv_releasedate_nowplaying)
            cvNowPlaying = view.findViewById(R.id.cv_nowplaying)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_nowplaying, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvTitle.text = dataSet[position].title
        viewHolder.tvReleased.text = dataSet[position].release_date

        viewHolder.cvNowPlaying.setOnClickListener {
            val myIntent = Intent(it.context, MovieDetailsActivity::class.java).apply {
                putExtra("movieID", dataSet[position].id)
            }

            it.context.startActivity(myIntent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
