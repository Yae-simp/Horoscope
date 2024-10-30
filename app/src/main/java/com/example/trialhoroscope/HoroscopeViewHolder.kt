package com.example.trialhoroscope

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var nameTextView: TextView = view.findViewById(R.id.nameTextView)
    private var datesTextView: TextView = view.findViewById(R.id.datesTextView)
    private var svgImageView: ImageView = view.findViewById(R.id.svgImageView)

    fun render (horoscope: Horoscope) {

        nameTextView.setText(horoscope.name)
        datesTextView.setText(horoscope.dates)
        svgImageView.setImageResource(horoscope.image)
    }
}