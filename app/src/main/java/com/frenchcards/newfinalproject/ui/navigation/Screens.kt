package com.frenchcards.newfinalproject.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddCard : Screen("add_card")
    object Review : Screen("review")
    object Stats : Screen("stats")
    object Settings : Screen("settings")
   // object Categories : Screen("categories")  // Add this line!
}