package com.app.waypoint.core

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.app.waypoint.model.WayPlace
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PreferenceManager @Inject constructor(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "waypoint_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val gson = Gson()

    fun saveWayPlace(wayPlace: WayPlace) {
        val places = getWayPlaces().toMutableList()
        places.add(wayPlace)
        val json = gson.toJson(places)
        sharedPreferences.edit { putString("saved_places", json) }
    }

    fun getWayPlaces(): List<WayPlace> {
        val json = sharedPreferences.getString("saved_places", null) ?: return emptyList()
        val type = object : TypeToken<List<WayPlace>>() {}.type
        return gson.fromJson(json, type)
    }
}
