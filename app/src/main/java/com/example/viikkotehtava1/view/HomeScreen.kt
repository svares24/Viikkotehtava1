package com.example.viikkotehtava1.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava1.viewmodel.TaskViewModel
import com.example.viikkotehtava1.model.Task
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    var title by remember { mutableStateOf("") }

    @Composable
    fun TaskTitleField(
        title: String,
        onTitleChange: (String) -> Unit
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Task name") }

        )
    }



    Column(modifier = Modifier.padding(16.dp)) {
        Text("Task List", style = MaterialTheme.typography.headlineMedium)
        TaskTitleField(
            title = title,
            onTitleChange = { title = it }
        )
        Row()
        {

            Button(
                onClick = {

                    viewModel.addTask(
                        Task(
                            id = tasks.size + 1,
                            title = title,
                            priority = 1,
                            dueDate = LocalDate.now().toString(),
                            description = "",
                            done = false
                        )

                    );
                    title = " ";
                },
                content = {
                    Text("Add new task");
                },

            )
        }
        LazyColumn() {
            items(tasks) { task ->
                Card(modifier = Modifier
                    .padding(8.dp)
                    .clickable { viewModel.selectTask(task) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(task.title, style = MaterialTheme.typography.headlineSmall)
                            Text(task.description)
                        }
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) }
                        )
                    }
                }
            }
        }
    }


    if (selectedTask != null) {
        DetailDialog(task = selectedTask!!, viewModel, onClose = { viewModel.closeDialog() }, onUpdate = { viewModel.updateTask(it) })
    }
}
