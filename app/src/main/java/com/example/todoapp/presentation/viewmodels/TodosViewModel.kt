package com.example.todoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.room.TodoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodosViewModel(val dao: TodoDao) : ViewModel() {
    val isSortedInPriority: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val todos = isSortedInPriority.flatMapLatest {
        if (it) {
            dao.getAllTodos()
        } else {
            dao.getAllByPriority()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(0), emptyList())


    val selectedCategory = MutableStateFlow("")

    private val _categories = dao.getCategories()
    val _state = MutableStateFlow(States())
    val state = combine(
        isSortedInPriority,
        _state,
        _categories,
        todos,
    ) { isSortedInPriority, _state, _categories, todos ->
        _state.copy(todos = todos, categories = _categories)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), States())


    init {
        viewModelScope.launch {
          addCategoriesByDefault()
        }
    }



    fun saveTodo() {
        val todo = Todo(
            id = state.value.id.value,
            title = state.value.title.value,
            desc = state.value.desc.value,
            dateOfAddition = System.currentTimeMillis().toLong(),
            dueDate = state.value.dueDate.value,
            priority = state.value.priority.value,
            status = state.value.status.value,
            category = state.value.category.value
        )

        viewModelScope.launch {
            dao.upsert(todo)
        }
        updateStates()
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            dao.delete(todo)
        }
    }

    private fun updateStates() {
        state.value.id.value = 0
        state.value.desc.value = ""
        state.value.title.value = ""
        state.value.dueDate.value = 0
        state.value.dateAdded.value = 0
        state.value.category.value = ""
        state.value.priority.value = 5
        state.value.status.value = "Incomplete"
    }


    fun setTodoValues(todo: Todo) {
        state.value.id.value = todo.id
        state.value.title.value = todo.title
        state.value.desc.value = todo.desc
        state.value.dueDate.value = todo.dueDate
        state.value.priority.value = todo.priority
        state.value.category.value = todo.category
        state.value.status.value = todo.status
        state.value.dateAdded.value = System
            .currentTimeMillis()
            .toLong()
    }

    fun updateTodoStatus(todo: Todo) {
        viewModelScope.launch {
            dao.updateStatus("Completed", todo.id)
        }
    }

    fun sortTodo() {
        isSortedInPriority.value = !isSortedInPriority.value
    }

    fun setCategoryValues(category: Category) {
        state.value.id.value = category.id
        state.value.category.value = category.desc
    }

    fun addCategory() {
        val category = Category(
            state.value.id.value,
            state.value.category.value
        )

        viewModelScope.launch {
            dao.addNewCategory(category)
        }
        updateStates()
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            dao.deleteCategory(category)

        }

    }

    fun addCategoriesByDefault() {
        if (state.value.categories.isEmpty()) {

            val categoriesList = listOf("Default", "Completed", "Uncompleted", "Work")
            var id = 0
            viewModelScope.launch {
                categoriesList.forEach {

                    dao.insertIfEmpty(id = id, desc = it)
                    id++
                }
            }
        }
    }
}