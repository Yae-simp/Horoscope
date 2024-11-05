package com.example.trialhoroscope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class HoroscopeAdapter(private val items: List<Horoscope>, val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<HoroscopeViewHolder>() {

    // Creamos la vista de la celda
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        return HoroscopeViewHolder(view)
    }

    // Rellenamos los datos de la celda cada vez que se va a mostrar
    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = items[position]
        holder.render(horoscope)

        // Utilizamos la funcion lambda cuando se haga click sobre la celda
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    // Cuantas celdas va a haber
    override fun getItemCount(): Int = items.size
}