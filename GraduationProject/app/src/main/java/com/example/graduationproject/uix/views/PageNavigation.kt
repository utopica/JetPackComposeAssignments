package com.example.graduationproject.uix.views

import android.telecom.Call.Details
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.graduationproject.data.entities.AddCartItem
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.uix.viewmodel.AuthViewModel
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.example.graduationproject.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson

@Composable
fun PageNavigation(
    homePageViewModel: HomePageViewModel,
    cartPageViewModel: CartPageViewModel,
    authViewModel: AuthViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login"){
            LoginPage(navController = navController, authViewModel = authViewModel)
        }

        composable("signup"){
            SignUpPage(navController = navController, authViewModel = authViewModel)
        }

        composable("homepage"){
            HomePage(navController = navController, homePageViewModel =  homePageViewModel, cartPageViewModel = cartPageViewModel, authViewModel = authViewModel)
        }

        composable(
            "cartPage"
        ){
            CartPage(
                navController = navController,
                cartPageViewModel = cartPageViewModel
            )
        }

        composable(
            "detailsPage/{food}",
            arguments = listOf(
                navArgument("food") { type = NavType.StringType },
            )
        ){
            val json = it.arguments?.getString("food")
            val foodObject  = Gson().fromJson(json,Foods::class.java)
            DetailsPage(navController = navController, food = foodObject, cartPageViewModel = cartPageViewModel)
        }
        
        composable("searchPage"){
            SearchPage(navController = navController)
        }

        composable("profilePage"){
            ProfilePage(navController = navController, authViewModel = authViewModel)
        }
    }
}