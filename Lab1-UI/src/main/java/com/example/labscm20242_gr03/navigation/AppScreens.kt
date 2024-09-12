package com.example.labscm20242_gr03.navigation

sealed class AppScreens(val route: String) {
    object PersonalDataActivity: AppScreens("personal_data_activity")
    object ContactDataActivity: AppScreens("contact_data_activity")
}