package com.example.lightsaver.persistence

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Store preferences
 */
class Preferences @Inject
constructor(private val preferences: SharedPreferences) {

    fun inactivityTime():Long {
        return this.preferences.getLong(PREF_INACTIVITY_TIME, 300000)
    }

    fun inactivityTime(value:Long) {
        this.preferences.edit().putLong(PREF_INACTIVITY_TIME, value).commit()
    }

    fun address(): String? {
        return this.preferences.getString(PREF_SSID, "192.168.4.1")
    }

    fun address(value:String) {
        this.preferences.edit().putString(PREF_SSID, value).commit()
    }

    fun ssID():String? {
        return this.preferences.getString(PREF_SSID, "Esp8266TestNet")
    }

    fun ssId(value:String) {
        this.preferences.edit().putString(PREF_SSID, value).commit()
    }

    fun password():String? {
        return this.preferences.getString(PREF_PASSWORD, "Esp8266Test")
    }

    fun password(value:String) {
        this.preferences.edit().putString(PREF_PASSWORD, value).commit()
    }

    /**
     * Reset the `SharedPreferences` and database
     */
    fun reset() {
        preferences.edit().clear().commit()
    }

    companion object {
        @JvmField val PREF_SSID = "pref_ssid"
        @JvmField val PREF_PASSWORD = "pref_password"
        @JvmField val PREF_INACTIVITY_TIME = "pref_inactivity_time"
    }
}