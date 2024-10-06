package com.example.calories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@Composable
fun CalorieScreen() {
    var weightInput by remember { mutableStateOf("") }
    var male by remember { mutableStateOf(true) }
    var intensity by remember { mutableStateOf(1.3f) }
    var result by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Heading(title = "Calories")

        // Painokenttä
        OutlinedTextField(
            value = weightInput,
            onValueChange = { weightInput = it },
            label = { Text("Enter weight") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Sukupuolen valinta
        GenderChoices(male = male, setGenderMale = { male = it })

        // Intensity valinta
        IntensityList(onClick = { intensity = it })

        // Laskuri-painike
        Calculation(male = male, weight = weightInput.toIntOrNull() ?: 0, intensity = intensity) {
            result = it
        }

        // Näytetään tulos
        Text(text = result.toString(), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Heading(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun GenderChoices(male: Boolean, setGenderMale: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = male, onClick = { setGenderMale(true) })
        Text(text = "Male")
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(selected = !male, onClick = { setGenderMale(false) })
        Text(text = "Female")
    }
}

@Composable
fun IntensityList(onClick: (Float) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Light") }
    var items = listOf("Light" to 1.3f, "Moderate" to 1.7f, "Hard" to 2f, "Very hard" to 2.2f)

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            readOnly = true,
            label = { Text("Select intensity") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(icon, contentDescription = "Dropdown icon")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { (label, intensity) ->
                DropdownMenuItem(onClick = {
                    selectedText = label  // Päivitetään valittu teksti
                    onClick(intensity)    // Kutsutaan valittua intensiteettiä
                    expanded = false      // Suljetaan dropdown valinnan jälkeen
                }) {
                    Text(text = label) // Näytetään valittu kohde
                }
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

@Composable
fun Calculation(male: Boolean, weight: Int, intensity: Float, setResult: (Int) -> Unit) {
    Button(onClick = {
        val result = if (male) {
            (10.2 * weight * intensity).toInt()
        } else {
            (7.18 * weight * intensity).toInt()
        }
        setResult(result)
    }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "CALCULATE")
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieScreenPreview() {
    CalorieScreen()
}
