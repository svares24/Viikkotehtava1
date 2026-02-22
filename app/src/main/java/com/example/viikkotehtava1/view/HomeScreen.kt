package com.example.viikkotehtava1.view

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava1.viewmodel.TaskViewModel
import com.example.viikkotehtava1.view.AddTaskDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(viewModel: TaskViewModel, onNavigateCalendar: () -> Unit = {}) {
    // collectAsState() muuntaa StateFlow:n Compose-tilaksi
    // → kun tietokanta muuttuu, tämä Composable piirretään uudelleen
    val tasks by viewModel.allTasks.collectAsState()
    val pendingCount by viewModel.pendingCount.collectAsState()

    // remember = muista arvo uudelleenpiirtojen välillä
    // mutableStateOf = Compose seuraa tätä arvoa
    var showDialog by remember { mutableStateOf(false) }

    // Scaffold = Material Design -pohjarakenne (yläpalkki + FAB + sisältö)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Tehtävät")
                        Text(
                            "$pendingCount tekemättä",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                // Yläpalkin oikea puoli: nappi valmiiden poistoon
                actions = {
                    IconButton(onClick = { viewModel.deleteCompletedTasks() }) {
                        Icon(Icons.Default.DeleteSweep, "Poista valmiit")
                    }
                    IconButton(onClick = onNavigateCalendar) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Go to calendar"
                        )
                    }
                }
            )
        },
        // FAB = Floating Action Button (kelluva toimintopainike)
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, "Lisää")
            }
        }
    ) { padding ->
        // Näytä joko tyhjä tila tai tehtävälista
        if (tasks.isEmpty()) {
            // Tyhjä tila: kiva ikoni ja teksti keskellä
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Text("Ei tehtäviä", color = Color.Gray)
                }
            }
        } else {
            // LazyColumn = vieritettävä lista (renderöi vain näkyvät rivit)
            // key = { it.id } → Room-id animaatioiden ja suorituskyvyn tueksi
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggle = { viewModel.toggleTask(task) },
                        onDelete = { viewModel.deleteTask(task) }
                    )
                }
            }
        }

        // Dialogi uuden tehtävän lisäämiseksi
        if (showDialog) {
            AddTaskDialog(
                onDismiss = { showDialog = false },
                onAdd = { title, description ->
                    viewModel.addTask(title, description)
                    showDialog = false
                }
            )
        }
    }
}
