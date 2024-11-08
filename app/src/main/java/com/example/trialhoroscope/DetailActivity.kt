package com.example.trialhoroscope

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import utils.SessionManager
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    //Horoscope to be shown
    private lateinit var horoscope: Horoscope

    //Whether horoscope is favorite or not
    private var isFavorite = false

    //Fav menu option for changing the fav icon
    private lateinit var favoriteMenuItem: MenuItem

    //Session manager, saves fav horoscope
    private lateinit var session: SessionManager

    private var dailyResult:String? = null
    private lateinit var dailyTextView: TextView
    private lateinit var svgImageView: ImageView
    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var navigationBar: BottomNavigationView


    @SuppressLint("MissingInflatedId")
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


        svgImageView = findViewById(R.id.svgImageView)
        navigationBar = findViewById(R.id.navigationBar)
        progressIndicator = findViewById(R.id.progressIndicator)
        dailyTextView = findViewById(R.id.dailyTextView)

        svgImageView.setImageResource(horoscope.image)

        navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_daily -> {
                    getDaily("daily")
                }
                R.id.menu_weekly -> {
                    getDaily("weekly")
                }
                R.id.menu_monthly -> {
                    getDaily("monthly")
                }
            }
            return@setOnItemSelectedListener true
        }
        navigationBar.selectedItemId = R.id.menu_daily
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
                shareDaily()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
    private fun shareDaily() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out my daily horoscope result: $dailyResult")
        sendIntent.type = "text/plain"

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun setFavoriteIcon() {
        if(isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_selected_fav)
        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_fav)
        }
    }

    private fun getDaily(method: String) {
        progressIndicator.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
            val url = URL ("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/$method?sign=${horoscope.id}&day=TODAY")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode
            Log.i("HTTP", "Response Code :: $responseCode")

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val jsonResponse = readStream(connection.inputStream).toString()
                dailyResult = JSONObject(jsonResponse).getJSONObject ("data").getString("horoscope_data")
            } else {
                dailyResult = "Call error"
            }
            CoroutineScope(Dispatchers.Main).launch {
                dailyTextView.text = dailyResult
                progressIndicator.visibility = View.GONE
            }
        } catch (e: Exception) {
            Log.e("HTTP", "Response Error :: ${e.stackTraceToString()}")
            }
        }
    }

    private fun readStream (inputStream: InputStream) : StringBuilder {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()
        return response
    }
}
