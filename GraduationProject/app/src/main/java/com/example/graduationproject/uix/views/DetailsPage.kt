package com.example.graduationproject.uix.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.uix.viewmodel.DetailsPageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(food : Foods, detailsPageViewModel: DetailsPageViewModel){

    val foodName = remember { mutableStateOf("") }
    val foodPrice = remember { mutableStateOf("") }
    val cartOrderCount = remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        foodName.value = food.food_name
        foodPrice.value = food.food_price.toString()
        //cartOrderCount.value = cart.cart_order_count.toString()
    }

    Scaffold (
        topBar = { TopAppBar(title = { Text(text = "Details") })}
    ) {
        paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

        }
    }
}