package com.example.graduationproject.uix.views

import android.telecom.Call.Details
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.graduationproject.data.entities.Carts
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

        /*
        composable("cartPage/cart"){
            val json = it.arguments?.getString("cart")
            val cartObject = Gson().fromJson(json, Carts::class.java)
            CartPage(navController = navController, cart = cartObject, cartPageViewModel = cartPageViewModel)
        }*/
        composable(
            "cartPage/{foodName}/{foodPicName}/{foodPrice}/{orderCount}/{username}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType },
                navArgument("foodPicName") { type = NavType.StringType },
                navArgument("foodPrice") { type = NavType.IntType },
                navArgument("orderCount") { type = NavType.IntType },
                navArgument("username") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val foodName = backStackEntry.arguments?.getString("foodName") ?: ""
            val foodPicName = backStackEntry.arguments?.getString("foodPicName") ?: ""
            val foodPrice = backStackEntry.arguments?.getInt("foodPrice") ?: 0
            val orderCount = backStackEntry.arguments?.getInt("orderCount") ?: 0
            val username = backStackEntry.arguments?.getString("username") ?: ""


            CartPage(
                navController = navController,
                cartPageViewModel = cartPageViewModel,
                foodName = foodName,
                foodPicName = foodPicName,
                foodPrice = foodPrice,
                orderCount = orderCount,
                username = username
            )
        }

        composable(
            "detailsPage/{food}/{orderCount}",
            arguments = listOf(
                navArgument("food") { type = NavType.StringType },
                navArgument("orderCount") { type = NavType.IntType }
            )
        ){
            val json = it.arguments?.getString("food")
            val foodObject  = Gson().fromJson(json,Foods::class.java)
            val orderCount = it.arguments?.getInt("orderCount") ?: 0
            DetailsPage(navController = navController, food = foodObject,  orderCount = orderCount, detailsPageViewModel = detailsPageViewModel, cartPageViewModel = cartPageViewModel)
        }
    }
}