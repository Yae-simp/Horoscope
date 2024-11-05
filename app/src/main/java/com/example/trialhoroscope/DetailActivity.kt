package com.example.trialhoroscope

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trialhoroscope.R.id.nameTextView
import utils.SessionManager

class DetailActivity : AppCompatActivity() {

    //Horoscope to be shown
    private lateinit var horoscope: Horoscope

    //Whether horoscope is favorite or not
    private var isFavorite = false

    //Fav menu option for changing the fav icon
    private lateinit var favoriteMenuItem: MenuItem

    //Session manager, saves fav horoscope
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_on_click)

        //Provides the id to visualize
        val id = intent.getStringExtra("horoscope_id")!!

        //Looks for the id
        horoscope = HoroscopeProvider.findById(id)

        //Action bar, title and subtitle
        supportActionBar?.title = getString(horoscope.name)
        supportActionBar?.subtitle = getString(horoscope.dates)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Instantiates session object
        session = SessionManager(this)

        //Revises whether horoscope is fav or not
        isFavorite = session.isFavorite(horoscope.id)

        findViewById<TextView>(nameTextView).setText(horoscope.name)
        findViewById<ImageView>(R.id.svgImageView).setImageResource(horoscope.image)
    }

    //Function to show menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)

        //Looks for fav menu option
        favoriteMenuItem = menu?.findItem(R.id.menu_fav)!!

       //Changes fav icon, according to fav settings
        setFavoriteIcon()
        return true
    }

    //Function that catches whether menu option has been clicked or not
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                println("Pressed 'return' button.")
                finish() //Closes activity
                return true
            }
            R.id.menu_fav -> {
                println("Pressed 'favorite' button.")
                if (isFavorite) {
                    session.setFavorite("")
                } else {
                    session.setFavorite(horoscope.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
                return true
            }
            R.id.menu_share -> {
                println("Pressed 'share' button.")
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
    private fun setFavoriteIcon() {
        if(isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_selected_fav)
        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_fav)
        }
    }
}
