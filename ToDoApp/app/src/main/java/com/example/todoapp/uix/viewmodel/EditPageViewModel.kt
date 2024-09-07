package com.example.todoapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repo.ToDosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPageViewModel @Inject constructor(var todorepo : ToDosRepository): ViewModel(){

    fun update(todo_id:Int, todo_name:String){
        CoroutineScope(Dispatchers.Main).launch {
            todorepo.update(todo_id, todo_name)
        }
    }
}