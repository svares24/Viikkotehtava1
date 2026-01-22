package com.example.viikkotehtava1.domain
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.collections.listOf

class TaskViewModel : ViewModel() {
    data class Task(
        val id: Int,
        val title: String,
        val description: String,
        val priority: Int,
        val dueDate: String,
        val done: Boolean,
    )
        var Tasks by mutableStateOf(listOf<Task>())

    init {
        Tasks = listOf(
            Task(id=1, title="Task1",description= "Description1", priority=4, dueDate="2026-01-08", done=false),
            Task(id=2, title="Task3",description= "Description3", priority=2, dueDate="2026-01-11", done=true),
            Task(id=3, title="Task2",description= "Description2", priority=3, dueDate="2026-01-10", done=false),
            Task(id=4, title="Task4",description= "Description4", priority=2, dueDate="2026-01-12", done=false),
            Task(id=5, title="Task5",description= "Description5", priority=1, dueDate="2026-01-13", done=false),
        )
    }
    fun addTask(newtask: Task): List<Task> {
        Tasks = Tasks + newtask
        return Tasks
    }

    fun sortTasksbyPriority(): List<Task> {
        return Tasks.sortedBy { it.priority }
    }

    fun sortTasksbyDate(): List<Task> {
        return Tasks.sortedBy { it.dueDate }
    }

    fun filterTasksbyDone(): List<Task> {
        return Tasks.filter{ it.done }
    }

    fun filterTasksbyNotDone(): List<Task> {
        return Tasks.filter{ !it.done }
    }

    fun toggleDone(id: Int): List<Task>  {
        Tasks = Tasks.map {
            if (it.id == id) it.copy(done = true) else it
        }
        return Tasks
    }
    fun removeTask(id: Int): List<Task>  {
        Tasks = Tasks.filterNot { it.id == id}
        return Tasks
    }
}