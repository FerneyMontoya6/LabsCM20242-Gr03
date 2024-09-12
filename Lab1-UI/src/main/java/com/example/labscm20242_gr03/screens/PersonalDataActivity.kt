package com.example.labscm20242_gr03.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataActivity(navController: NavController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold (
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Información Personal")
                }
            )
        }
    ) { innerPadding ->
        Box (modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (isPortrait) {
                PersonalPortraitLayout()
            } else {
                PersonalLandscapeLayout()
            }
        }
    }
}

@Composable
fun PersonalPortraitLayout() {
    Text("Hola mundo portrait!!")

}

@Composable
fun PersonalLandscapeLayout() {
    Text("Hola mundo landscape!!")
}

