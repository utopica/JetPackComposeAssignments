package com.example.todoapp.uix.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.data.entity.ToDos
import com.example.todoapp.uix.viewmodel.AddPageViewModel
import com.example.todoapp.uix.viewmodel.EditPageViewModel
import com.example.todoapp.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson

@Composable
fun PageNavigation(homePageViewModel: HomePageViewModel,
                   addPageViewModel: AddPageViewModel,
                   editPageViewModel: EditPageViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homepage") {
        composable("homepage"){
            HomePage(navController, homePageViewModel)
        }

        composable("addPage"){
            AddPage(addPageViewModel)
        }

        composable("editPage/{toDo}",
            arguments = listOf(navArgument("toDo"){type = NavType.StringType})
        ){
            val json = it.arguments?.getString("toDo")

            val todoObject = Gson().fromJson(json, ToDos::class.java)

            EditPage(todoObject, editPageViewModel)
        }

    }


}