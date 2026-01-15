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
import androidx.compose.ui.tooling.preview.Preview
import com.example.viikkotehtava1.domain.mockTasks
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
import com.example.viikkotehtava1.domain.Task
import com.example.viikkotehtava1.domain.addTask
import com.example.viikkotehtava1.domain.filterTasksbyDone
import com.example.viikkotehtava1.domain.sortTasksbyDate
import com.example.viikkotehtava1.domain.sortTasksbyPriority
import com.example.viikkotehtava1.domain.toggleDone

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val tag = "MainActivity"
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        enableEdgeToEdge()
        setContent {
            Viikkotehtava1Theme {
                Scaffold(modifier = Modifier.fillMaxSize ()) { innerPadding ->
                    ParentComponent(modifier = Modifier.padding (innerPadding))
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
fun ParentComponent(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("Task") }
    var description by remember { mutableStateOf("Description") }
    var dueDate by remember { mutableStateOf("2026-01-15") }
    var priority by remember { mutableStateOf("1") }
    var tasklist by remember { mutableStateOf(mockTasks) }
    var list by remember { mutableStateOf(mockTasks) }

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
        list.forEach { task ->
            Text("${task.id} - ${task.title} - Priority: ${task.priority} - Date: ${task.dueDate} - ${task.description} - ${task.done}")
            Button(
                onClick = {
                tasklist = toggleDone(tasklist, task.id)
                    list = tasklist
                },
                content = {
                    Text("Mark task as done")
                }
            )
        }
        Row() {
            Button(
                onClick = {
                    val newTask = Task(
                        id = tasklist.size + 1,
                        title = name,
                        description = description,
                        priority = priority.toInt(),
                        dueDate = dueDate,
                        done = false
                    )
                    tasklist = addTask(tasklist, newTask)
                    list = tasklist
                },
                content = {
                    Text("Lisää uusi task")
                }
            )
            Button(
                onClick = { list = filterTasksbyDone(tasklist) },
                content = {
                    Text("Filter by done")
                }
            )

        }
        Row() {
            Button(
                onClick = { list = sortTasksbyPriority(tasklist) },
                content = {
                    Text("Sort tasks by priority")
                }
            )
            Button(
                onClick = {
                    list = sortTasksbyDate(tasklist) },
                content = {
                    Text("Sort tasks by date")
                }
            )
        }
    }
}

//Kotlin