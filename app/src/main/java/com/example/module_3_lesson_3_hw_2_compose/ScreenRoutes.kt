package com.example.module_3_lesson_3_hw_2_compose

sealed class ScreenRoutes(val route: String) {
    object ScreenMain : ScreenRoutes("screenMain")
    object ScreenSettings : ScreenRoutes("screenSettings")
}