package com.example.labscm20242_gr03.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataActivity(navController: NavController) {
    var inputPhone by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Información de Contacto")
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (true) {
                ContactPortraitLayout(
                    inputPhone = inputPhone,
                    onInputPhoneChanges = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            inputPhone = newValue
                        }
                    },
                    inputEmail = inputEmail,
                    onInputEmailChanges = { newValue -> inputEmail = newValue }
                )
            } else {
                ContactLandscapeLayout()
            }
        }
    }
}

@Composable
fun ContactPortraitLayout (
    inputPhone: String,
    onInputPhoneChanges: (String) -> Unit,
    inputEmail: String,
    onInputEmailChanges: (String) -> Unit
) {
    Column (
        modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Call,
                contentDescription = "Phone icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            TextField(
                value = inputPhone,
                onValueChange = onInputPhoneChanges,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Email,
                contentDescription = "Email icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            TextField(
                value = inputEmail,
                onValueChange = onInputEmailChanges,
                label = { Text("Correo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
    }
}

@Composable
fun ContactLandscapeLayout () {
    Text("Hola landscape")
}