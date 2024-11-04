package com.example.trialhoroscope

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trialhoroscope.R.id.nameTextView

class DetailActivity : AppCompatActivity() {

    private lateinit var horoscope: Horoscope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_on_click)

        val id = intent.getStringExtra("horoscope_id")!!

        horoscope = HoroscopeProvider.findById(id)

        supportActionBar?.title = getString(horoscope.name)
        supportActionBar?.subtitle = getString(horoscope.dates)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<TextView>(nameTextView).setText(horoscope.name)
        findViewById<ImageView>(R.id.svgImageView).setImageResource(horoscope.image)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                println("Pressed 'return' button.")
                finish()
                return true
            }
            R.id.menu_fav -> {
                println("Pressed 'favorite' button.")
                return true
            }
            R.id.menu_share -> {
                println("Pressed 'Share' button.")
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
