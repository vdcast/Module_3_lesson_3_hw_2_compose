package com.example.module_3_lesson_3_hw_2_compose

import android.content.Context

class SharedPrefs(context: Context) {
    private val SKIN = "skin"
    private val REPEAT = "repeat"
    private val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    fun setSkin(skin: String) {
        prefs.edit().putString(SKIN, skin).apply()
    }
    fun getSkin(): String {
        val skin: String =
            if (prefs.getString(SKIN, "Light") != null) {
                prefs.getString(SKIN, "Light")!!
            } else "Light"
        return skin
    }
    fun setRepeat(repeat: Boolean) {
        prefs.edit().putBoolean(REPEAT, repeat).apply()
    }
    fun getRepeat(): Boolean {
        return prefs.getBoolean(REPEAT, false)
    }
}