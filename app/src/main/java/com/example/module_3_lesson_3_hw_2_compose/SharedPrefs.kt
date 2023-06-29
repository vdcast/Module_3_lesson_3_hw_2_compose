package com.example.module_3_lesson_3_hw_2_compose

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPrefs(context: Context) {
    private val SKIN = "skin"
    private val REPEAT = "repeat"
    private val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    private val _skinFlow = MutableStateFlow(getSkin())
    val skinFlow = _skinFlow.asStateFlow()

    fun setSkin(skin: String) {
        prefs.edit().putString(SKIN, skin).apply()
        _skinFlow.value = skin
    }
    fun getSkin(): String {
        return prefs.getString(SKIN, "Nature") ?: "Nature"
    }
    fun setRepeat(repeat: Boolean) {
        prefs.edit().putBoolean(REPEAT, repeat).apply()
    }
    fun getRepeat(): Boolean {
        return prefs.getBoolean(REPEAT, false)
    }
}