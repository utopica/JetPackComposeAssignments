package com.example.todoapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repo.ToDosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPageViewModel @Inject constructor(var todorepo: ToDosRepository) : ViewModel(){

    fun save(todo_name: String){
        CoroutineScope(Dispatchers.Main).launch {
            todorepo.save(todo_name)
        }
    }}