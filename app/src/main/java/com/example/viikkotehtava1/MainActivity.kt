package com.example.viikkotehtava1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.viikkotehtava1.ui.theme.Viikkotehtava1Theme
import androidx.compose.runtime.remember
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava1.domain.TaskViewModel


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val tag = "MainActivity"
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        enableEdgeToEdge()
        setContent {
            Viikkotehtava1Theme {
                Scaffold(modifier = Modifier.fillMaxSize ()) { innerPadding ->
                    ParentComponent(modifier = Modifier.padding (innerPadding)
                        )
                }
            }
        }
    }

    override fun onStart() {
        val tag = "MainActivity"
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        val tag = "MainActivity"
        super.onResume()
        Log.d(tag, "onResume")
    }
}

@Composable
fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("title")}
    )
}

@Composable
fun DescriptionTextField(
    description: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = description,
        onValueChange = onNameChange,
        label = { Text("description")}
    )
}

@Composable
fun dueDateTextField(
    dueDate: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = dueDate,
        onValueChange = onNameChange,
        label = { Text("dueDate")}
    )
}
@Composable
fun priorityTextField(
    priority: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value =  priority,
        onValueChange = onNameChange,
        label = { Text("priority")}
    )
}

@Composable
fun ParentComponent(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val tag = "MainActivity"
    var name by remember { mutableStateOf("Task") }
    var description by remember { mutableStateOf("Description") }
    var dueDate by remember { mutableStateOf("2026-01-15") }
    var priority by remember { mutableStateOf("1") }
    var viewList by remember { mutableStateOf(viewModel.Tasks) }

    Column {
        Spacer(modifier = Modifier.height(height = 16.dp))
        NameTextField(
            name = name,
            onNameChange = { name = it }
        )
        DescriptionTextField(
            description = description,
            onNameChange = { description = it }
        )
        dueDateTextField(
            dueDate = dueDate,
            onNameChange = { dueDate = it }
        )
        priorityTextField(
            priority = priority,
            onNameChange = { priority = it }
        )

        viewList.forEach { task ->
            Text("${task.id} - ${task.title} - Priority: ${task.priority} - Date: ${task.dueDate} - ${task.description} - ${task.done}")
            Button(
                onClick = {
                    viewList = viewModel.toggleDone(task.id)
                },
                content = {
                    Text("Mark task as done")
                }
            )
        }
        Row() {
            Button(
                onClick = {
                    val newTask = TaskViewModel.Task(
                        id = viewModel.Tasks.size + 1,
                        title = name,
                        description = description,
                        priority = priority.toInt(),
                        dueDate = dueDate,
                        done = false
                    )
                    Log.d(tag, "add task")
                    viewList = viewModel.addTask(newTask)
                },
                content = {
                    Text("Add a task")
                }
            )
            Button(
                onClick = { viewList = viewModel.filterTasksbyDone() },
                content = {
                    Text("Filter by done")
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
            Button(
                onClick = {
                    viewList = viewModel.sortTasksbyDate() },
                content = {
                    Text("Sort tasks by date")
                }
            )
        }
    }
}