package com.frenchcards.newfinalproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.frenchcards.newfinalproject.ui.screens.*

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
       composable("add_card") {
           AddCardScreen(navController = navController)
       }
        composable("review") {
            ReviewScreen(navController = navController)
        }
        composable("stats") {
            StatsScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}