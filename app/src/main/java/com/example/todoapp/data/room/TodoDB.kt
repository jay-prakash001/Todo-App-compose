package com.example.todoapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.room.TodoDao

@Database(entities = [Todo::class, Category::class], version = 1)
abstract class TodoDB : RoomDatabase() {
    abstract val dao: TodoDao
}
