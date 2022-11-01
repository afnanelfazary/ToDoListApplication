package com.afnanelfazary.todolistapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.afnanelfazary.todolistapplication.database.dao.TodoDao
import com.afnanelfazary.todolistapplication.database.model.Todo
import java.lang.Class
import java.security.AccessControlContext

@Database( entities = [Todo::class], version = 1,)
@TypeConverters(DateConverter::class)
abstract class MyDataBase :RoomDatabase(){

    abstract  fun getTaskDao(): TodoDao
    companion object{
        private val DATABASE_NAME="todo-Database"
      private  var myDataBase:MyDataBase?=null
        fun getInstance (context: Context):MyDataBase
        {
            //single(instance) object from database (singletone pattern
        if (myDataBase==null){
            myDataBase=  Room.databaseBuilder(context,
                MyDataBase::class.java,
                DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()

                .build()
        }
return  myDataBase!!
        }
    }
}