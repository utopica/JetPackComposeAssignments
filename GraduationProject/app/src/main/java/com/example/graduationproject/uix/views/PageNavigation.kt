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
            "cartPage/{cartItem}",
            arguments = listOf(navArgument("cartItem") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("cartItem")
            val cartItemObject = Gson().fromJson(json, AddCartItem::class.java)
            CartPage(
                navController = navController,
                cartPageViewModel = cartPageViewModel,
                cartItem = cartItemObject
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