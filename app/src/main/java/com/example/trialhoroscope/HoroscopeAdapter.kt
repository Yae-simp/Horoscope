package com.example.trialhoroscope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class HoroscopeAdapter(private val items: List<Horoscope>, val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<HoroscopeViewHolder>() {

    //Creates cell view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        return HoroscopeViewHolder(view)
    }

    //Fills in cell data every time it is going to be displayed
    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = items[position]
        holder.render(horoscope)

        //Lambda cell click function
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    //Cell size
    override fun getItemCount(): Int = items.size
}