package com.example.todoapp.presentation.screens

sealed class NavScreenItems(val name: String) {
    data object Home : NavScreenItems(name = "Home")
    data object AddTodo : NavScreenItems(name = "AddTodo")
    data object AddCategory : NavScreenItems(name = "AddCategory")
    data object  ContactUs : NavScreenItems(name = "contactUs")


}
