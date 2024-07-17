package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val dateOfAddition: Long,
    val dueDate: Long,
    val category: String,
    val priority: Int = 10,
    val status: String
)
