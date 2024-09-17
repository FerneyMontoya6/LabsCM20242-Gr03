package com.example.labscm20242_gr03.screens

import android.content.res.Configuration
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

    val fechaNacimiento = rememberSaveable { mutableStateOf<String>("") }
    val openDate = rememberSaveable { mutableStateOf(false) }

    var selectedEscolaridad by remember { mutableStateOf("") }

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
                    Text(stringResource(id = R.string.top_bar_personal))
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
                    fechaNacimiento = fechaNacimiento,
                    selectedOption = selectedEscolaridad,
                    onOptionSelected = { selectedEscolaridad = it }
                )
            } else {
                PersonalLandscapeLayout(
                    fullNames = fullNames,
                    onFullNameHandleChange = { fullNames = it },
                    lastNames = fullLastNames,
                    onLastNameHandleChange = { fullLastNames = it },
                    selectedGender = selectedGender,
                    onGenderChange = { selectedGender = it },
                    navController = navController,
                    openDate = openDate,
                    fechaNacimiento = fechaNacimiento,
                    selectedOption = selectedEscolaridad,
                    onOptionSelected = { selectedEscolaridad = it }
                )
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
    fechaNacimiento: MutableState<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {

    // Estados para los mensajes de error
    var fullNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var fechaNacimientoError by remember { mutableStateOf("") }

    // Strings
    val campoNombreRequerido = stringResource(id = R.string.nombre_requerido)
    val campoApellidoRequerido = stringResource(id = R.string.apellido_requerido)
    val campoFechaNacimientoRequerido = stringResource(id = R.string.fecha_nacimiento_requerida)
    val campoObligatorio = stringResource(id = R.string.camplo_obligatorio)
    val hombre = stringResource(id = R.string.hombre)
    val mujer = stringResource(id = R.string.mujer)


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
                Text(stringResource(id = R.string.nombre))
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
                                fullNameError = campoObligatorio
                            } else {
                                fullNameError = ""
                            }
                        },
                        label = { Text(stringResource(id = R.string.nombres)) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        isError = fullNameError.isNotEmpty(),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (fullNameError.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.camplo_obligatorio),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
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
                            lastNameError = campoObligatorio
                        } else {
                            lastNameError = ""
                        }
                    },
                    label = { Text(stringResource(id = R.string.apellidos)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    isError = lastNameError.isNotEmpty(),
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (lastNameError.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.camplo_obligatorio),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
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
            Text(stringResource(id = R.string.sexo))
            RadioButton(
                selected = selectedGender == hombre,
                onClick = { onGenderChange(hombre) }
            )
            Text(stringResource(id = R.string.hombre))
            RadioButton(
                selected = selectedGender == mujer,
                onClick = { onGenderChange(mujer) }
            )
            Text(stringResource(id = R.string.mujer))
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
                    value = if (fechaNacimiento.value.isNotEmpty()) fechaNacimiento.value else stringResource(
                        id = R.string.seleccionar_fecha
                    ),
                    onValueChange = {},
                    readOnly = true, // El campo será de solo lectura
                    label = { Text(stringResource(id = R.string.fecha_de_nacimiento)) },
                    trailingIcon = {
                        IconButton(onClick = { openDate.value = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = stringResource(id = R.string.abrir_calendario)
                            )
                        }
                    },
                    isError = fechaNacimientoError.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (fechaNacimientoError.isNotEmpty()) {
                    Text(
                        text = fechaNacimientoError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            DatePickerDialogInput(
                openDate = openDate,
                fechaNacimiento = fechaNacimiento,
                onErrorChange = { error -> fechaNacimientoError = error })
        }


        Spacer(modifier = Modifier.height(20.dp))

        // Escolaridad
        Row {
            GenericSpinner(
                label = stringResource(R.string.escolaridad),
                options = listOf(
                    stringResource(id = R.string.primaria),
                    stringResource(id = R.string.secundaria),
                    stringResource(id = R.string.preparatoria),
                    stringResource(id = R.string.universidad),
                ),
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = {
                var isValid = true

                if (fullNames.isEmpty()) {
                    fullNameError = campoNombreRequerido
                    isValid = false
                }

                if (lastNames.isEmpty()) {
                    lastNameError = campoApellidoRequerido
                    isValid = false
                }

                if (fechaNacimiento.value.isEmpty()) {
                    fechaNacimientoError = campoFechaNacimientoRequerido
                    isValid = false
                }

                if (isValid) {
                    printPersonalInformation(
                        fullNames,
                        lastNames,
                        selectedGender,
                        fechaNacimiento.value,
                        selectedOption
                    )
                    navController.navigate(route = AppScreens.ContactDataActivity.route)
                }
            }) {
                Text(stringResource(id = R.string.siguiente))
            }
        }
    }
}

@Composable
fun PersonalLandscapeLayout(
    fullNames: String,
    onFullNameHandleChange: (String) -> Unit,
    lastNames: String,
    onLastNameHandleChange: (String) -> Unit,
    selectedGender: String,
    onGenderChange: (String) -> Unit,
    navController: NavController,
    openDate: MutableState<Boolean>,
    fechaNacimiento: MutableState<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    // Estados para los mensajes de error
    var fullNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var fechaNacimientoError by remember { mutableStateOf("") }

    // Strings
    val campoObligatorio = stringResource(id = R.string.camplo_obligatorio)
    val hombre = stringResource(id = R.string.hombre)
    val mujer = stringResource(id = R.string.mujer)
    val campoNombreRequerido = stringResource(id = R.string.nombre_requerido)
    val campoApellidoRequerido = stringResource(id = R.string.apellido_requerido)
    val campoFechaNacimientoRequerido = stringResource(id = R.string.fecha_nacimiento_requerida)

    LazyColumn(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(bottom = 20.dp).weight(1f),
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
                                    fullNameError = campoObligatorio
                                } else {
                                    fullNameError = ""
                                }
                            },
                            label = { Text(stringResource(id = R.string.nombres)) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                keyboardType = KeyboardType.Text
                            ),
                            singleLine = true,
                            isError = fullNameError.isNotEmpty(),
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        if (fullNameError.isNotEmpty()) {
                            Text(
                                text = fullNameError,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
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
                                    lastNameError = campoObligatorio
                                } else {
                                    lastNameError = ""
                                }
                            },
                            label = { Text(stringResource(id = R.string.apellidos)) },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                keyboardType = KeyboardType.Text
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = lastNameError.isNotEmpty(),
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        if (lastNameError.isNotEmpty()) {
                            Text(
                                text = lastNameError,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
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
                Text(stringResource(id = R.string.sexo))
                RadioButton(
                    selected = selectedGender == hombre,
                    onClick = { onGenderChange(hombre) }
                )
                Text(hombre)
                RadioButton(
                    selected = selectedGender == mujer,
                    onClick = { onGenderChange(mujer) }
                )
                Text(mujer)
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
                        value = if (fechaNacimiento.value.isNotEmpty()) fechaNacimiento.value else stringResource(
                            id = R.string.seleccionar_fecha
                        ),
                        onValueChange = {},
                        readOnly = true, // El campo será de solo lectura
                        label = { Text(stringResource(id = R.string.fecha_de_nacimiento)) },
                        trailingIcon = {
                            IconButton(onClick = { openDate.value = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = stringResource(id = R.string.seleccionar_fecha)
                                )
                            }
                        },
                        isError = fechaNacimientoError.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (fechaNacimientoError.isNotEmpty()) {
                        Text(
                            text = fechaNacimientoError,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                DatePickerDialogInput(
                    openDate = openDate,
                    fechaNacimiento = fechaNacimiento,
                    onErrorChange = { error -> fechaNacimientoError = error })
            }


            Spacer(modifier = Modifier.height(20.dp))

            // Escolaridad
            Row {
                GenericSpinner(
                    label = stringResource(id = R.string.escolaridad),
                    options = listOf(
                        stringResource(id = R.string.primaria),
                        stringResource(id = R.string.secundaria),
                        stringResource(id = R.string.preparatoria),
                        stringResource(id = R.string.universidad),
                    ),
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                Modifier
                    .fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(onClick = {
                    var isValid = true

                    if (fullNames.isEmpty()) {
                        fullNameError = campoNombreRequerido
                        isValid = false
                    }

                    if (lastNames.isEmpty()) {
                        lastNameError = campoApellidoRequerido
                        isValid = false
                    }

                    if (fechaNacimiento.value.isEmpty()) {
                        fechaNacimientoError = campoFechaNacimientoRequerido
                        isValid = false
                    }

                    if (isValid) {
                        printPersonalInformation(
                            fullNames,
                            lastNames,
                            selectedGender,
                            fechaNacimiento.value,
                            selectedOption
                        )
                        navController.navigate(route = AppScreens.ContactDataActivity.route)
                    }
                }) {
                    Text(stringResource(id = R.string.siguiente))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogInput(
    openDate: MutableState<Boolean>,
    fechaNacimiento: MutableState<String>,
    onErrorChange: (String) -> Unit
) {
    if (openDate.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        // Cambia el formato de la fecha para que solo incluya día/mes/año
        val formatter: SimpleDateFormat =
            SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")

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
                TextButton(onClick = { openDate.value = false }) { Text(stringResource(id = R.string.cancelar)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericSpinner(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Dropdown icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
            TextField(
                readOnly = true,
                value = selectedOption,
                onValueChange = {},
                label = { Text(label) },
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
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun printPersonalInformation(
    fullNames: String,
    lastNames: String,
    selectedGender: String?,
    fechaNacimiento: String,
    selectedEscolaridad: String?
) {
    val genderText = selectedGender ?: "No especificado"
    val escolaridadText = selectedEscolaridad ?: "No especificado"

    val message = """
        Información personal: 
        
        Nombre completo: $fullNames $lastNames
        
        Género: $genderText
        
        Nació el $fechaNacimiento
        
        Grado escolar: $escolaridadText
    """.trimIndent()

    Log.d("PersonalInfo", message)
}