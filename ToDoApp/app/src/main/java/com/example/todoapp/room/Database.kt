package com.example.todoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.entity.ToDos

@Database(entities = [ToDos::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract fun getToDosDao() : ToDosDao

}