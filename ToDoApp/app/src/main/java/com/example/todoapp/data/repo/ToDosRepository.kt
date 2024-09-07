package com.example.todoapp.data.repo

import com.example.todoapp.data.datasource.ToDosDataSource
import com.example.todoapp.data.entity.ToDos

class ToDosRepository (var todods : ToDosDataSource){

    suspend fun loadToDos() : List<ToDos> = todods.loadToDos()

    suspend fun save(todo_name:String) = todods.save(todo_name)

    suspend fun update(todo_id:Int, todo_name:String) = todods.update(todo_id, todo_name)

    suspend fun delete(todo_id:Int) = todods.delete(todo_id)

    suspend fun search(searchWord : String) : List<ToDos> = todods.search(searchWord)
}