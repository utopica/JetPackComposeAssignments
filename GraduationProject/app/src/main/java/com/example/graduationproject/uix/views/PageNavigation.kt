package com.example.graduationproject.uix.views

import android.telecom.Call.Details
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.example.graduationproject.uix.viewmodel.DetailsPageViewModel
import com.example.graduationproject.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson

@Composable
fun PageNavigation(
    homePageViewModel: HomePageViewModel,
    detailsPageViewModel : DetailsPageViewModel,
    cartPageViewModel: CartPageViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homepage") {
        composable("homepage"){
            HomePage(navController, homePageViewModel, cartPageViewModel, detailsPageViewModel)
        }

        composable("cartPage"){
            CartPage(navController = navController, cartPageViewModel = cartPageViewModel)
        }

        composable(
            "detailsPage/{food}",
            arguments = listOf(
                navArgument("food") { type = NavType.StringType },
            )
        ){
            val json = it.arguments?.getString("food")
            val foodObject  = Gson().fromJson(json,Foods::class.java)
            DetailsPage(navController = navController, food = foodObject, detailsPageViewModel = detailsPageViewModel)
        }
    }
}