package com.example.viikkotehtava1.domain

import com.example.viikkotehtava1.domain.Task

val mockTasks = listOf(
    Task(id=1, title="Task",description= "Description", priority=4, dueDate="2026-01-08", done=false),
    Task(id=2, title="Task3",description= "Description3", priority=2, dueDate="2026-01-11", done=false),
    Task(id=3, title="Task2",description= "Description2", priority=3, dueDate="2026-01-10", done=false),
    Task(id=4, title="Task4",description= "Description4", priority=2, dueDate="2026-01-12", done=false),
    Task(id=5, title="Task5",description= "Description5", priority=1, dueDate="2026-01-13", done=false),
)