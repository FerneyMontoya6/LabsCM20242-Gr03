package com.example.labscm20242_gr03.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.labscm20242_gr03.screens.PersonalDataActivity

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.PersonalDataActivity.route) {
        composable(route = AppScreens.PersonalDataActivity.route) {
            PersonalDataActivity(navController)
        }

        composable(route = AppScreens.ContactDataActivity.route) {
            // Missing the activity
        }
    }
}