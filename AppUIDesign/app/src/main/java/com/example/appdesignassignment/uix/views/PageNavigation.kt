package com.example.jetpackcomposelectures.week4_3_movies.uix.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appdesignassignment.uix.views.HomePage

@Composable
fun PageNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homepage"){
        composable("homepage"){
            HomePage(navController)
        }

    }
}