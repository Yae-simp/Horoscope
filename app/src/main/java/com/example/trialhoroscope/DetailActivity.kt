package com.example.trialhoroscope

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trialhoroscope.R.id.nameTextView

class DetailActivity : AppCompatActivity() {

    private lateinit var horoscope: Horoscope

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_on_click)

        val id = intent.getStringExtra("horoscope_id")!!

        horoscope = HoroscopeProvider.findById(id)

        findViewById<TextView>(nameTextView).setText(horoscope.name)
        findViewById<ImageView>(R.id.svgImageView).setImageResource(horoscope.image)
    }
}