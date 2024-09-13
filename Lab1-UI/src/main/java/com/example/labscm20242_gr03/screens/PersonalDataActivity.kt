package com.example.labscm20242_gr03.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataActivity(navController: NavController) {
    var inputText by rememberSaveable { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Información Personal")
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isPortrait) {
                PersonalPortraitLayout(
                    inputText = inputText,
                    onInputChanges = { inputText = it },
                    selectedGender = selectedGender,
                    onGenderChange = { selectedGender = it }
                )
            } else {
                PersonalLandscapeLayout()
            }
        }
    }
}

@Composable
fun PersonalPortraitLayout(
    inputText: String,
    onInputChanges: (String) -> Unit,
    selectedGender: String,
    onGenderChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth(0.85f)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Nombres")
            }
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = "Person icon",
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(36.dp)
                )
                TextField(
                    value = inputText,
                    onValueChange = onInputChanges,
                    label = { Text("Melissa") }
                )
            }
        }

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Person,
                contentDescription = "Person icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            TextField(
                value = inputText,
                onValueChange = onInputChanges,
                label = { Text("Apellidos") }
            )
        }

        // Género
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Info,
                contentDescription = "Person icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            Text("Sexo:")
            RadioButton(
                selected = selectedGender == "Hombre",
                onClick = { onGenderChange("Hombre") }
            )
            Text("Hombre")
            RadioButton(
                selected = selectedGender == "Mujer",
                onClick = { onGenderChange("Mujer") }
            )
            Text("Mujer")
        }
    }
}

@Composable
fun PersonalLandscapeLayout() {
    Text("Hola mundo landscape!!")
}
