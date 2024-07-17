package com.example.todoapp.temp

fun main() {

    val list = listOf("Completed", "uncompleted","work","default")

    val list2 = list.filter {
        it.contains("completed",ignoreCase = true)
    }
    println("list1 : $list")
    println("list2 : $list2")
}