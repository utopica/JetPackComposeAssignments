package com.example.graduationproject.uix.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.uix.viewmodel.DetailsPageViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(food : Foods, detailsPageViewModel: DetailsPageViewModel){

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

            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${food.food_picName}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Text(text = "Food Name: ${food.food_name}", fontSize = 24.sp)
            Text(text = "Price: $${food.food_price}", fontSize = 20.sp)
            Text(text = "Order Count: ${detailsPageViewModel.orderCount}", fontSize = 20.sp)
        }
    }
}