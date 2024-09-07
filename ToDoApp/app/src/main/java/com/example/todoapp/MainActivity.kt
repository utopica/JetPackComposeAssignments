package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.uix.viewmodel.AddPageViewModel
import com.example.todoapp.uix.viewmodel.EditPageViewModel
import com.example.todoapp.uix.viewmodel.HomePageViewModel
import com.example.todoapp.uix.views.PageNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val homePageViewModel : HomePageViewModel by viewModels()
    val addPageViewModel : AddPageViewModel by viewModels()
    val editPageViewModel : EditPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                PageNavigation(homePageViewModel, addPageViewModel, editPageViewModel)
            }
        }
    }
}
