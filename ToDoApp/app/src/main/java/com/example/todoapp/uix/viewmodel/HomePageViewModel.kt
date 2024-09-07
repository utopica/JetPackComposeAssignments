package com.example.todoapp.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.entity.ToDos
import com.example.todoapp.data.repo.ToDosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //parametre aktarma özelliği oto yap
class HomePageViewModel @Inject constructor(var todorepo: ToDosRepository) : ViewModel() {

    var toDoList = MutableLiveData<List<ToDos>>()

    init{
        loadToDos()
    }

    fun loadToDos(){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value = todorepo.loadToDos()
        }
    }

    fun delete(todo_id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            todorepo.delete(todo_id)

            loadToDos()
        }
    }

    fun search(searchWord:String){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value = todorepo.search(searchWord)
        }
    }



}
