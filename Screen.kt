package com.example.wishlistapp

sealed class Screen(val route:String) {
    data object HomeScreen : Screen("HomeScreen")
    data object AddScreen : Screen("AddScreen")
}