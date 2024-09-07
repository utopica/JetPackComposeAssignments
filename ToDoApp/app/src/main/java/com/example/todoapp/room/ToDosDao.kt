package com.example.todoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.entity.ToDos

@Dao
interface ToDosDao {

    @Query("SELECT * FROM todos")
    suspend fun loadToDos() : List<ToDos>

    @Insert
    suspend fun save(toDo: ToDos)

    @Update
    suspend fun update(toDo: ToDos)

    @Delete
    suspend fun delete(toDo: ToDos)

    @Query(" SELECT * FROM todos WHERE todo_name like '%' || :searchWord || '%' ")
    suspend fun search(searchWord:String) : List<ToDos>

}