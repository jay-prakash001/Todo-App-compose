package com.example.todoapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {
    //operations for todos
    @Upsert
    suspend fun upsert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM Todo")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM Todo ORDER BY priority DESC")
    fun getAllByPriority(): Flow<List<Todo>>

    @Query(value = "UPDATE todo SET category =:givenStatus WHERE id =:givenId ")
    suspend fun updateStatus(givenStatus: String = "Completed", givenId: Int)

    //     operations for categories

    @Query(
        "INSERT INTO category (`id`, `desc`) " +
                "SELECT :id, :desc " +
                "WHERE NOT EXISTS (SELECT * FROM category WHERE `desc` = :desc)"
    )
    suspend fun insertIfEmpty(id: Int, desc: String)


    @Upsert()
    suspend fun addNewCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM `category` ORDER BY `desc` ASC")
    fun getCategories(): Flow<List<Category>>


}