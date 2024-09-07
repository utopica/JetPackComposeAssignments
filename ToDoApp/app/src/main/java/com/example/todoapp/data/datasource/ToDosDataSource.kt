package com.example.todoapp.data.datasource

import com.example.todoapp.data.entity.ToDos
import com.example.todoapp.room.ToDosDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToDosDataSource (var tododao : ToDosDao) {

    suspend fun loadToDos() : List<ToDos> = withContext(Dispatchers.IO){
        return@withContext tododao.loadToDos()
    }

    suspend fun save(todo_name:String){
        val newToDo = ToDos(0, todo_name)
        tododao.save(newToDo)
    }

    suspend fun update(todo_id:Int, todo_name:String){

        val updatedToDo = ToDos(todo_id, todo_name)

        tododao.update(updatedToDo)
    }

    suspend fun delete(todo_id:Int){

        val deletedToDo = ToDos(todo_id, "")

        tododao.delete(deletedToDo)

    }

    suspend fun search(searchWord : String) : List<ToDos> = withContext(Dispatchers.IO){

        return@withContext tododao.search(searchWord)
    }


}