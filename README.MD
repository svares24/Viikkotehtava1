#Functiot

fun addTask(list: List<Task>, newtask: Task): List<Task> {
    return list + newtask // palauttaa listan uudella täskillä
}

fun sortTasksbyPriority(list: List<Task>): List<Task> {
    return list.sortedBy { it.priority } // lajittele priorityn numeron mukaan
}

fun sortTasksbyDate(list: List<Task>): List<Task> {
    return list.sortedBy { it.dueDate } // lajittele päivämäärän mukaan
}

fun filterTasksbyDone(list: List<Task>): List<Task> {
    return list.filter{ it.done } // näytä vain taskit, jotka ovat valmiina
}

fun toggleDone(list: List<Task>, id: Int): List<Task>  {
    return list.map {
        if (it.id == id) it.copy(done = true) else it //palauta lista taskeistä ja muuta valittu task tehdyksi
    }
}

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: String,
    val done: Boolean,
)