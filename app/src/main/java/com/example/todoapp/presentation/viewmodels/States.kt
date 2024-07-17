package com.example.todoapp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Todo

data class States(
    val id : MutableState<Int> = mutableIntStateOf(0),
    val todos :List<Todo> = emptyList(),
    val tempTodos :MutableList<Todo> = mutableListOf(),
    val title: MutableState<String> = mutableStateOf(""),
    val desc :MutableState<String> = mutableStateOf(""),
    val dateAdded :MutableState<Long> = mutableLongStateOf(0),
    val dueDate : MutableState<Long> = mutableLongStateOf(0),
    val category: MutableState<String> = mutableStateOf("Default"),
    val priority: MutableState<Int> = mutableIntStateOf(0),
    val status: MutableState<String> = mutableStateOf("uncompleted"),
    val categories : List<Category> = emptyList()
)