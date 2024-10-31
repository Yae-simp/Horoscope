package com.example.trialhoroscope

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var horoscopeList: List<Horoscope>

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        horoscopeList = HoroscopeProvider.findAll()

        val adapter = HoroscopeAdapter(horoscopeList) { position: Int ->
            val horoscope = horoscopeList [position]
            navigateToDetail(horoscope)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }


    private fun navigateToDetail(horoscope: Horoscope) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("horoscope_id", horoscope.id)
        startActivity(intent)
    }
}

