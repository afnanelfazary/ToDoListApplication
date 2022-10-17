package com.afnanelfazary.todolistapplication.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id:Int,
    @ColumnInfo(name = "Title")
    val name:String?=null,
    val details:String?=null,
    val date: Date?=null,
    val isDone:Boolean?=false
)