package com.example.trialhoroscope

import android.annotation.SuppressLint
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

        //Looks for RecyclerView in layout
        recyclerView = findViewById(R.id.recyclerView)

        //Obtains horoscope list
        horoscopeList = HoroscopeProvider.findAll()

        //Creates adapter, it obtains the list of horoscopes and lambda click function
        adapter = HoroscopeAdapter(horoscopeList) { position: Int ->
            val horoscope = horoscopeList [position]
            navigateToDetail(horoscope)
        }

        //Assigns adapter to recyclerView, tells it to display the cells vertically
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        //Refreshes list by notifying adapter of data changes
        adapter.notifyDataSetChanged() //Reflects changes
    }

    //Navigates to DetailActivity by giving it selected horoscope id
    private fun navigateToDetail(horoscope: Horoscope) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("horoscope_id", horoscope.id)
        startActivity(intent)
    }
}

