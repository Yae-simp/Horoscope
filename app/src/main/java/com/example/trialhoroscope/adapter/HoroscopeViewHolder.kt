package com.example.trialhoroscope.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trialhoroscope.data.Horoscope
import com.example.trialhoroscope.R
import utils.SessionManager

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //Cell visual components
    private var nameTextView: TextView = view.findViewById(R.id.dailyTextView)
    private var datesTextView: TextView = view.findViewById(R.id.datesTextView)
    private var svgImageView: ImageView = view.findViewById(R.id.svgImageView)
    private var favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)

    fun render (horoscope: Horoscope) {

        nameTextView.setText(horoscope.name)
        datesTextView.setText(horoscope.dates)
        svgImageView.setImageResource(horoscope.image)

        //If fav, shows. If not, does not show.
        if (SessionManager(itemView.context).isFavorite(horoscope.id)) {
            favoriteImageView.visibility = View.VISIBLE
        } else {
            favoriteImageView.visibility = View.GONE
        }
    }
}