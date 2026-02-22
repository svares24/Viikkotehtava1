package com.example.viikkotehtava1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viikkotehtava1.data.local.AppDatabase
import com.example.viikkotehtava1.data.repository.TaskRepository
import com.example.viikkotehtava1.view.TaskListScreen
import com.example.viikkotehtava1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    // Luo tietokanta lazy-patternilla (vasta kun sitä tarvitaan)
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    // Luo Repository, joka käyttää DAO:a tietokantaoperaatioihin
    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    // Luo ViewModel ViewModelProvider.Factory:n avulla
    // Factory tarvitaan koska ViewModel ottaa parametrin (repository)
    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent käynnistää Compose-UI:n
        setContent {
            MaterialTheme {
                // Annetaan ViewModel näkymälle
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
