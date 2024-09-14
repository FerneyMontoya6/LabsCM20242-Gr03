package com.example.labscm20242_gr03.screens

import android.content.res.Configuration
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labscm20242_gr03.R
import com.example.labscm20242_gr03.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataActivity(navController: NavController) {
    var fullNames by rememberSaveable { mutableStateOf("") }
    var fullLastNames by rememberSaveable { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    val fechaNacimiento = rememberSaveable  { mutableStateOf<String>("") }
    val openDate = rememberSaveable { mutableStateOf(false) }

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
                    fullNames = fullNames,
                    onFullNameHandleChange = { fullNames = it },
                    lastNames = fullLastNames,
                    onLastNameHandleChange = { fullLastNames = it },
                    selectedGender = selectedGender,
                    onGenderChange = { selectedGender = it },
                    navController = navController,
                    openDate = openDate,
                    fechaNacimiento = fechaNacimiento
                )
            } else {
                PersonalLandscapeLayout()
            }
        }
    }
}

@Composable
fun PersonalPortraitLayout(
    fullNames: String,
    onFullNameHandleChange: (String) -> Unit,
    lastNames: String,
    onLastNameHandleChange: (String) -> Unit,
    selectedGender: String,
    onGenderChange: (String) -> Unit,
    navController: NavController,
    openDate: MutableState<Boolean>,
    fechaNacimiento: MutableState<String>
) {

    // Estados para los mensajes de error
    var fullNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var fechaNacimientoError by remember { mutableStateOf("") }

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
                horizontalArrangement = Arrangement.Start,
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
                Column {
                    TextField(
                        value = fullNames,
                        onValueChange = {
                            onFullNameHandleChange(it)
                            if (it.isEmpty()) {
                                fullNameError = "Campo obligatorio"
                            } else {
                                fullNameError = ""
                            }
                        },
                        label = { Text("Nombres *") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        isError = fullNameError.isNotEmpty(),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (fullNameError.isNotEmpty()) {
                        Text(text = fullNameError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }
                }

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
            Column {
                TextField(
                    value = lastNames,
                    onValueChange = {
                        onLastNameHandleChange(it)
                        if (it.isEmpty()) {
                            lastNameError = "Campo obligatorio"
                        } else {
                            lastNameError = ""
                        }
                    },
                    label = { Text("Apellidos *") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    isError = lastNameError.isNotEmpty(),
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (lastNameError.isNotEmpty()) {
                    Text(text = lastNameError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }
            }
        }


        // Género
        Row(
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

        Spacer(modifier = Modifier.height(20.dp))

        // Fecha de nacimiento
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.DateRange,
                contentDescription = "Person icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            // TextField para abrir el DatePicker
            Column {
                TextField(
                    value = if (fechaNacimiento.value.isNotEmpty()) fechaNacimiento.value else stringResource(id = R.string.seleccionar_fecha),
                    onValueChange = {},
                    readOnly = true, // El campo será de solo lectura
                    label = { Text("Fecha de nacimiento *") },
                    trailingIcon = {
                        IconButton(onClick = { openDate.value = true }) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Abrir calendario")
                        }
                    },
                    isError = fechaNacimientoError.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (fechaNacimientoError.isNotEmpty()) {
                    Text(text = fechaNacimientoError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }
            }
            DatePickerDialogInput(openDate = openDate, fechaNacimiento = fechaNacimiento, onErrorChange = { error -> fechaNacimientoError = error })
        }


        Spacer(modifier = Modifier.height(20.dp))

        // Escolaridad
        Row {
            EscolaridadSpinner()
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            Modifier
                .fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = {
                var isValid = true

                if (fullNames.isEmpty()) {
                    fullNameError = "El campo nombres es requerido"
                    isValid = false
                }

                if (lastNames.isEmpty()) {
                    lastNameError = "El campo apellidos es requerido"
                    isValid = false
                }

                if (fechaNacimiento.value.isEmpty()) {
                    fechaNacimientoError = "La fecha de nacimiento es requerida"
                    isValid = false
                }

                if (isValid) {
                    navController.navigate(route = AppScreens.ContactDataActivity.route)
                }
            }) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
fun PersonalLandscapeLayout() {
    Text("Hola mundo landscape!!")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogInput(openDate: MutableState<Boolean>, fechaNacimiento: MutableState<String>, onErrorChange: (String) -> Unit) {
    if (openDate.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        // Cambia el formato de la fecha para que solo incluya día/mes/año
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())

        DatePickerDialog(
            onDismissRequest = {
                openDate.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDate.value = false
                        fechaNacimiento.value = formatter.format(datePickerState.selectedDateMillis)
                        onErrorChange("")
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDate.value = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscolaridadSpinner() {
    val escolaridadOptions = listOf("Primaria", "Secundaria", "Pregrado", "Posgrado", "Doctorado", "Maestría")
    var expanded by remember { mutableStateOf(false) }
    var selectedEscolaridad by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Person icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            TextField(
                readOnly = true,
                value = selectedEscolaridad,
                onValueChange = {},
                label = { Text("Grado de Escolaridad") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
            )
        }
        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            escolaridadOptions.forEach { escolaridad ->
                DropdownMenuItem(
                    text = { Text(escolaridad) },
                    onClick = {
                        selectedEscolaridad = escolaridad
                        expanded = false
                    }
                )
            }
        }
    }
}
