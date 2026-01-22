package com.example.viikkotehtava1

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava1.domain.TaskViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val tag = "MainActivity"
    var name by remember { mutableStateOf("Task") }
    var description by remember { mutableStateOf("Description") }
    var dueDate by remember { mutableStateOf("2026-01-15") }
    var priority by remember { mutableStateOf("1") }
    var viewList by remember { mutableStateOf(viewModel.Tasks) }

    Column {
        Spacer(modifier = Modifier.height(height = 16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = { Text("title")}
        )
        OutlinedTextField(
            value = description,
            onValueChange = {description = it},
            label = { Text("description")}
        )
        OutlinedTextField(
            value = dueDate,
            onValueChange = {dueDate = it},
            label = { Text("dueDate")}
        )
        OutlinedTextField(
            value =  priority,
            onValueChange = {priority = it},
            label = { Text("priority")}
        )

        Row() {
            Button(
                onClick = {
                    val newTask = TaskViewModel.Task(
                        id = viewModel.Tasks.size + 1,
                        title = if (name.isBlank()) "No title" else name,
                        description = if (description.isBlank()) "" else description,
                        priority = priority.toIntOrNull() ?: 1,
                        dueDate = if (dueDate.isBlank()) "2026-01-01" else dueDate,
                        done = false
                    )
                    Log.d(tag, "add task")
                    viewList = viewModel.addTask(newTask)
                },
                content = {
                    Text("Add a task")
                }
            )
            Spacer(Modifier.width(4.dp))
            Button(
                onClick = { viewList = viewModel.filterTasksbyDone() },
                content = {
                    Text("Filter by done")
                }
            )
            Spacer(Modifier.width(4.dp))
            Button(
                onClick = { viewList = viewModel.filterTasksbyNotDone() },
                content = {
                    Text("Filter by not done")
                }
            )

        }

        Row() {
            Button(
                onClick = { viewList = viewModel.sortTasksbyPriority() },
                content = {
                    Text("Sort tasks by priority")
                }
            )
            Spacer(Modifier.width(4.dp))
            Button(
                onClick = {
                    viewList = viewModel.sortTasksbyDate() },
                content = {
                    Text("Sort tasks by date")
                }
            )
        }

        LazyColumn() {
            items(viewList, key = { it.id }) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { viewList = viewModel.toggleDone(task.id) }
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("${task.id}. ${task.title} (${task.dueDate}) - ${task.priority} ")
                    Spacer(Modifier.weight(1f))
                    Button(onClick = { viewList = viewModel.removeTask(task.id) }) {
                        Text("Delete")
                    }
                }
            }
        }

    }
}