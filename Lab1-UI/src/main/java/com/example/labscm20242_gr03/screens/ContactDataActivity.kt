package com.example.labscm20242_gr03.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labscm20242_gr03.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataActivity(navController: NavController) {
    var inputPhone by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputCountry by remember { mutableStateOf("") }
    var inputAddress by remember { mutableStateOf("") }

    val latinAmericanCountries = listOf(
        "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica",
        "Cuba", "Dominicana", "Ecuador", "El Salvador", "Guatemala", "Honduras",
        "México", "Nicaragua", "Panamá", "Paraguay", "Perú", "Puerto Rico",
        "Uruguay", "Venezuela"
    )

    var countrySuggestions by remember { mutableStateOf(latinAmericanCountries) }

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
            ContactPortraitLayout(
                inputPhone = inputPhone,
                onInputPhoneChanges = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        inputPhone = newValue
                    }
                },
                inputEmail = inputEmail,
                onInputEmailChanges = { newValue -> inputEmail = newValue },
                inputCountry = inputCountry,
                countrySuggestions = countrySuggestions,
                onCountryChange = { newValue ->
                    inputCountry = newValue
                    countrySuggestions = if (newValue.isEmpty()) {
                        latinAmericanCountries
                    } else {
                        latinAmericanCountries.filter {
                            it.contains(newValue, ignoreCase = true)
                        }
                    }
                },
                onCountrySuggestionClick = { country ->
                    inputCountry = country
                    countrySuggestions = emptyList()
                },
                navController = navController,
                inputAddress = inputAddress,
                onAddressChange = { newValue -> inputAddress = newValue }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPortraitLayout(
    inputPhone: String,
    onInputPhoneChanges: (String) -> Unit,
    inputEmail: String,
    onInputEmailChanges: (String) -> Unit,
    inputCountry: String,
    countrySuggestions: List<String>,
    onCountryChange: (String) -> Unit,
    onCountrySuggestionClick: (String) -> Unit,
    navController: NavController,
    inputAddress: String,
    onAddressChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
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

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.LocationOn,
                    contentDescription = "Location icon",
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(36.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    TextField(
                        value = inputCountry,
                        onValueChange = {
                            onCountryChange(it)
                            expanded = true
                        },
                        label = { Text("País") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded && countrySuggestions.isNotEmpty(),
                        onDismissRequest = { expanded = false }
                    ) {
                        countrySuggestions.forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion) },
                                onClick = {
                                    onCountrySuggestionClick(suggestion)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.LocationOn,
                    contentDescription = "Location icon",
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(36.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    TextField(
                        value = inputCountry,
                        onValueChange = {
                            onCountryChange(it)
                            expanded = true
                        },
                        label = { Text("Ciudad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded && countrySuggestions.isNotEmpty(),
                        onDismissRequest = { expanded = false }
                    ) {
                        countrySuggestions.forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion) },
                                onClick = {
                                    onCountrySuggestionClick(suggestion)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth(0.85f)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Text("Dirección")
            }
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Home icon",
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(36.dp)
                )
                TextField(
                    value = inputAddress,
                    onValueChange = onAddressChange,
                    label = { Text("Carrera 51") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        }

        Row (
            Modifier
                .fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = {
                navController.navigate(route = AppScreens.PersonalDataActivity.route)
            }) {
                Text("Siguiente")
            }
        }

    }
}

@Composable
fun ContactLandscapeLayout() {
    Text("Hola landscape")
}
