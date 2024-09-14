package com.example.graduationproject.uix.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel){

    var foodsList = homePageViewModel.foodsList.observeAsState(listOf())

    LaunchedEffect(key1 = true) {
        homePageViewModel.getFoods()
    }

    Scaffold (

        topBar = { CenterAlignedTopAppBar(title = { Text(text = "Homepage") }) }
    ){
        paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            items(
                count = foodsList.value.count(),
                itemContent = {
                    val food = foodsList.value[it]
                    Card (modifier = Modifier.padding(all=5.dp)){
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val foodJson = Gson().toJson(food)
                                    //navController.navigate("foodDetailPage/$foodJson")
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                           Column (modifier = Modifier.padding(all = 10.dp)){
                               Text(text = food.food_name, fontSize = 20.sp)
                               Spacer(modifier = Modifier.size(10.dp))
                               Text(text = food.food_price.toString(), fontSize = 20.sp)
                               Spacer(modifier = Modifier.size(10.dp))
                               Text(text = food.food_picName, fontSize = 20.sp)
                               Spacer(modifier = Modifier.size(10.dp))
                               Text(text = food.food_id.toString(), fontSize = 20.sp)

                           }
                        }
                    }
                }
            )
        }




    }
}