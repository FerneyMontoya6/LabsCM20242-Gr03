package com.example.labscm20242_gr03.screens

import android.content.res.Configuration
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labscm20242_gr03.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataActivity(navController: NavController) {
    var fullNames by rememberSaveable { mutableStateOf("") }
    var fullLastNames by rememberSaveable { mutableStateOf("") }
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
                    fullNames = fullNames,
                    onFullNameHandleChange = { fullNames = it },
                    lastNames = fullLastNames,
                    onLastNameHandleChange = { fullLastNames = it },
                    selectedGender = selectedGender,
                    onGenderChange = { selectedGender = it },
                    navController = navController
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
    navController: NavController
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
                TextField(
                    value = fullNames,
                    onValueChange = onFullNameHandleChange,
                    label = { Text("Nombres") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true
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
                value = lastNames,
                onValueChange = onLastNameHandleChange,
                label = { Text("Apellidos") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
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

        // Fecha de nacimiento
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.DateRange,
                contentDescription = "Date icon",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(36.dp)
            )
        }

        Row (
            Modifier
                .fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = {
                navController.navigate(route = AppScreens.ContactDataActivity.route)
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
fun DatePickerDialogInput(openDate: MutableState<Boolean>, fechaNacimiento:MutableState<String>){
    if (openDate.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", java.util.Locale.getDefault())
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
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDate.value = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
          }
}
}