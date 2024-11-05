package com.example.trialhoroscope

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import utils.SessionManager

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //Cell visual components
    private var nameTextView: TextView = view.findViewById(R.id.nameTextView)
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