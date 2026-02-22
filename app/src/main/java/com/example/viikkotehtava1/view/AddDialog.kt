package com.example.viikkotehtava1.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,                          // Sulkee dialogin
    onAdd: (String, String) -> Unit                 // Palauttaa otsikon ja kuvauksen
) {
    // Dialogin sisäiset tilat: käyttäjän syöttämät tekstit
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,               // Sulkee dialogin taustaa koskettamalla
        title = { Text("Uusi tehtävä") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Otsikkokenttä
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Otsikko") },
                    singleLine = true,              // Ei rivinvaihtoa
                    modifier = Modifier.fillMaxWidth()
                )
                // Kuvauskenttä (monirivinen)
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Kuvaus (valinnainen)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAdd(title, description) },
                // Nappi on pois käytöstä jos otsikko on tyhjä
                enabled = title.isNotBlank()
            ) {
                Text("Lisää")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Peruuta")
            }
        }
    )
}