package utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("horoscope_session", Context.MODE_PRIVATE)

    fun setFavorite(horoscopeId: String) {
        val editor = prefs.edit()
        editor.putString("favorite_horoscope", horoscopeId)
        editor.apply()
    }

    private fun getFavorite(): String {
        return prefs.getString("favorite_horoscope", "")!!
    }

    fun isFavorite(horoscopeId: String) : Boolean {
        return horoscopeId == getFavorite()
    }


}