package com.example.graduationproject.uix.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.ui.theme.Brown
import com.example.graduationproject.ui.theme.Peach
import com.example.graduationproject.ui.theme.PeachContainer
import com.example.graduationproject.ui.theme.poppinsMedium
import com.example.graduationproject.ui.theme.poppinsMediumBold
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.example.graduationproject.uix.viewmodel.DetailsPageViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(navController: NavController, food: Foods, orderCount: Int, detailsPageViewModel: DetailsPageViewModel, cartPageViewModel: CartPageViewModel) {

    var currentOrderCount = remember { mutableStateOf(orderCount) }

    val username = "elif_okumus"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("homepage")}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = PeachContainer
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PeachContainer)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 180.dp)
                        .background(
                            Peach.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                        )
                )

                GlideImage(
                    imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${food.food_picName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()//size(250.dp)
                        .align(Alignment.Center)
                        .padding(24.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .background(Peach.copy(alpha = 0.3f))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = food.food_name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp), //24.dp
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (currentOrderCount.value > 1) currentOrderCount.value-- },
                        modifier = Modifier
                            .background(Peach.copy(alpha = 0.3f), CircleShape)
                            .size(56.dp)
                    ) {
                        Text(text = "-", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsMediumBold)
                    }

                    Text(
                        text = "${currentOrderCount.value}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { currentOrderCount.value++ },
                        modifier = Modifier
                            .background(Peach.copy(alpha = 0.3f), CircleShape)
                            .size(56.dp)
                    ) {
                        Text(text = "+", fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsMediumBold)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Brown, RoundedCornerShape(40.dp))
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${food.food_price}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    IconButton(
                        onClick = {

                            val cartItem = Carts(
                                cart_food_id = 0,
                                food_name = food.food_name,
                                food_picName = food.food_picName,
                                food_price = food.food_price,
                                cart_order_count = currentOrderCount.value,
                                username = username
                            )
                            cartPageViewModel.addToCart(cartItem)

                            Log.e("Details", "Cart : ${cartItem}")
                            cartPageViewModel.addToCart(cartItem)

                        },
                        modifier = Modifier
                            .size(62.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to Cart",
                            tint = Brown,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}