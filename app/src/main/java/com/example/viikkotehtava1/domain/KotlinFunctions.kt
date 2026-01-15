package com.example.viikkotehtava1.domain

fun addTask(list: List<Task>, newtask: Task): List<Task> {
    return list + newtask
}

fun sortTasksbyPriority(list: List<Task>): List<Task> {
    return list.sortedBy { it.priority }
}

fun sortTasksbyDate(list: List<Task>): List<Task> {
    return list.sortedBy { it.dueDate }
}

fun filterTasksbyDone(list: List<Task>): List<Task> {
    return list.filter{ it.done }
}

fun toggleDone(list: List<Task>, id: Int): List<Task>  {
    return list.map {
        if (it.id == id) it.copy(done = true) else it
    }
}