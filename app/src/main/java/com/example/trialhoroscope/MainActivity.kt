package com.example.trialhoroscope

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    //List of horoscopes to be shown
    private lateinit var horoscopeList: List<Horoscope>

    private lateinit var recyclerView: RecyclerView

    //Adapter, indicates recyclerView which data to show and how
    private lateinit var adapter: HoroscopeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = Html.fromHtml("<font color='#000000'>Horoscope </font>")

        //Looks for RecyclerView
        recyclerView = findViewById(R.id.recyclerView)

        //Obtains horoscope list
        horoscopeList = HoroscopeProvider.findAll()

        // Creamos el adapter pasandole la lista de horoscopos y la función lambda para cuando se haga click en uno
        adapter = HoroscopeAdapter(horoscopeList) { position: Int ->
            val horoscope = horoscopeList [position]
            navigateToDetail(horoscope)
        }

        // Asignamos el adapter al RecyclerView y le decimos que muestre las celdas verticalmente
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onResume() {
        super.onResume()
        // Refrescamos la lista notificando al adapter de que los datos han cambiado
        adapter.notifyDataSetChanged() // Esto lo hacemos para que refleje el favorito cuando cambie
    }

    // Navegar a DetailActivity pasandole el id del horóscopo seleccionado
    private fun navigateToDetail(horoscope: Horoscope) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("horoscope_id", horoscope.id)
        startActivity(intent)
    }
}

