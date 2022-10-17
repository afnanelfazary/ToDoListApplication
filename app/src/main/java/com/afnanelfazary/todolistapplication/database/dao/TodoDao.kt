package com.afnanelfazary.todolistapplication.database.dao

import androidx.room.*
import com.afnanelfazary.todolistapplication.database.model.Todo

@Dao
interface TodoDao {
    //insert new Todo
    @Insert
    fun addTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("select * from Todo")
    fun getAllTodo(): List<Todo>

}