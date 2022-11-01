package com.afnanelfazary.todolistapplication.database.dao

import androidx.room.*
import com.afnanelfazary.todolistapplication.database.model.Todo
import java.util.*

@Dao
interface TodoDao {
    //insert new Todo
    @Insert
    fun insertTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("select * from Todo")
    fun getAllTodo(): List<Todo>


    @Query("select * from Todo where date= :date  ")
    fun getTodoByDate(date: Date):List<Todo>


}