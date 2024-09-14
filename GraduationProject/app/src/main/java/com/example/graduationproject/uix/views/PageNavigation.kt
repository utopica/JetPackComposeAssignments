package com.example.graduationproject.uix.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.graduationproject.uix.viewmodel.HomePageViewModel

@Composable
fun PageNavigation(homePageViewModel: HomePageViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homepage") {
        composable("homepage"){
            HomePage(navController, homePageViewModel)
        }
    }
}