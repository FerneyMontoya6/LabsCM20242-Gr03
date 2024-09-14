package com.example.labscm20242_gr03.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labscm20242_gr03.navigation.AppScreens
import com.example.labscm20242_gr03.services.CityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataActivity(navController: NavController) {
    var inputPhone by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputCountry by remember { mutableStateOf("") }
    var inputCity by remember { mutableStateOf("") }
    var inputAddress by remember { mutableStateOf("") }

    val latinAmericanCountries = listOf(
        "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica",
        "Cuba", "Dominicana", "Ecuador", "El Salvador", "Guatemala", "Honduras",
        "México", "Nicaragua", "Panamá", "Paraguay", "Perú", "Puerto Rico",
        "Uruguay", "Venezuela"
    )

    var countrySuggestions by remember { mutableStateOf(latinAmericanCountries) }

    // Obtén el ViewModel
    val cityViewModel: CityViewModel = viewModel()
    val cities by cityViewModel.cities.collectAsState()

    // Filtra las ciudades en base al inputCountry
    val citySuggestions = remember(inputCity) {
        cities.filter { city ->
            city.name.contains(inputCity, ignoreCase = true)
        }.map { it.name }
    }

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
                inputCity = inputCity,
                citySuggestions = citySuggestions, // Usa citySuggestions en lugar de countrySuggestions
                onCityChange = { newValue ->
                    inputCity = newValue
                },
                onCitySuggestionClick = { city ->
                    inputCity = city
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
    inputCity: String,
    citySuggestions: List<String>,
    onCityChange: (String) -> Unit,
    onCitySuggestionClick: (String) -> Unit,
    navController: NavController,
    inputAddress: String,
    onAddressChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var countryExpanded by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var countryError by remember { mutableStateOf("") }

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
            Column {
                TextField(
                    value = inputPhone,
                    onValueChange = {
                        onInputPhoneChanges(it)
                        if (it.isEmpty()) {
                            phoneError = "El teléfono es requerido"
                        } else {
                            phoneError = ""
                        }
                    },
                    label = { Text("Teléfono*") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = phoneError.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (phoneError.isNotEmpty()) {
                    Text(text = phoneError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }
            }

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
            Column {
                TextField(
                    value = inputEmail,
                    onValueChange = {
                        onInputEmailChanges(it)
                        if (it.isEmpty()) {
                            emailError = "El correo es requerido"
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                            emailError = "Correo inválido"
                        } else {
                            emailError = ""
                        }
                    },
                    label = { Text("Correo*") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = emailError.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (emailError.isNotEmpty()) {
                    Text(text = emailError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }

            }
        }

        // Country
        // Country
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

            Column {
                ExposedDropdownMenuBox(
                    expanded = countryExpanded,
                    onExpandedChange = { countryExpanded = !countryExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    TextField(
                        value = inputCountry,
                        onValueChange = {
                            onCountryChange(it)
                            countryExpanded = true
                            if (it.isEmpty()) {
                                countryError = "El país es requerido"
                            } else {
                                countryError = ""
                            }
                        },
                        label = { Text("País *") },  // Indica que es obligatorio
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .padding(end = 16.dp),
                        isError = countryError.isNotEmpty()  // Indica si hay error
                    )

                    // Mostrar las sugerencias de países
                    ExposedDropdownMenu(
                        expanded = countryExpanded && countrySuggestions.isNotEmpty(),
                        onDismissRequest = { countryExpanded = false }
                    ) {
                        countrySuggestions.forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion) },
                                onClick = {
                                    onCountrySuggestionClick(suggestion)
                                    countryExpanded = false
                                    countryError = ""  // Limpiar el error al seleccionar un país
                                }
                            )
                        }
                    }
                }

                // Mostrar el mensaje de error debajo del input
                Spacer(modifier = Modifier.height(5.dp))
                if (countryError.isNotEmpty()) {
                    Text(
                        text = countryError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }




        // City
        Column(
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
                        value = inputCity,
                        onValueChange = {
                            onCityChange(it)
                            expanded = true
                        },
                        label = { Text("Ciudad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded && citySuggestions.isNotEmpty(),
                        onDismissRequest = { expanded = false }
                    ) {
                        citySuggestions.forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion) },
                                onClick = {
                                    onCitySuggestionClick(suggestion)
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

        Row(
            Modifier
                .fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = {
                var isValid = true

                if (inputPhone.isEmpty()) {
                    phoneError = "El teléfono es requerido"
                    isValid = false
                }

                if (inputEmail.isEmpty()) {
                    emailError = "El correo es requerido"
                    isValid = false
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
                    emailError = "Correo inválido"
                    isValid = false
                }

                if (inputCountry.isEmpty()) {
                    countryError = "El país es requerido"
                    isValid = false
                }

                if (isValid) {
                    printContactInformation(inputPhone, inputEmail, inputCountry, inputCity, inputAddress)
                    navController.navigate(route = AppScreens.PersonalDataActivity.route)
                }
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

fun printContactInformation(
    phone: String,
    email: String,
    country: String,
    city: String,
    address: String
) {
    val countryText = if (country.isNotEmpty()) country else "No especificado"
    val cityText = if (city.isNotEmpty()) city else "No especificado"
    val addressText = if (address.isNotEmpty()) address else "No especificado"

    val message = """
        Información de contacto:

        Teléfono: ${if (phone.isNotEmpty()) phone else "No especificado"}

        Correo electrónico: ${if (email.isNotEmpty()) email else "No especificado"}

        País: $countryText

        Ciudad: $cityText

        Dirección: $addressText
    """.trimIndent()

    Log.d("ContactInfo", message)
}

