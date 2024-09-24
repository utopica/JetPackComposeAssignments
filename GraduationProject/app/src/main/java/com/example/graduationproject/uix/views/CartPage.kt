package com.example.graduationproject.uix.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.ui.theme.Peach
import com.example.graduationproject.ui.theme.PeachCard
import com.example.graduationproject.ui.theme.PeachContainer
import com.example.graduationproject.ui.theme.poppinsItalic
import com.example.graduationproject.ui.theme.poppinsMediumBold
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(
    navController: NavController,
    cartPageViewModel: CartPageViewModel
) {
    val cartItems by cartPageViewModel.cartItems.collectAsState(emptyList())
    val username = "elif_okumus"



    LaunchedEffect(key1 = true) {
        cartPageViewModel.getCartItems(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = { Text("Cart", color = Color.Black, fontFamily = poppinsItalic) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PeachContainer)

            )
        },
        containerColor = PeachContainer
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PeachContainer)

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    //.weight(1f) //newly added
                    .padding(16.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(
                            cartItem = item,
                            onDeleteClick = {
                                cartPageViewModel.removeFromCart(
                                    cartItemId = item.cart_food_id,
                                    username = username
                                )
                            },
                            cartPageViewModel = cartPageViewModel,
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }


                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(PeachCard)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Amount")
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(30.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White.copy(alpha = 0.5f))
                                    .border(1.dp, color = Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("$${20}")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Amount", fontWeight = FontWeight.Bold)

                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "USD ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }


                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Make Payment", fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.ArrowForward, contentDescription = "Make Payment")
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CartItemRow(
    cartItem: Carts,
    onDeleteClick: () -> Unit,
    cartPageViewModel: CartPageViewModel,
    navController: NavController,
) {

    var orderCount by remember { mutableStateOf(cartItem.cart_order_count) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val food = Foods(
                    food_id = 0,
                    food_name = cartItem.food_name,
                    food_picName = cartItem.food_picName,
                    food_price = cartItem.food_price
                )

                val foodJson = Gson().toJson(food)
                navController.navigate("detailsPage/$foodJson")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${cartItem.food_picName}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(modifier = Modifier
            .weight(1f)
            .padding(16.dp)) {

            Text(
                text = cartItem.food_name,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (orderCount > 0) {
                        orderCount--
                        cartPageViewModel.updateCartItemCount(cartItem, orderCount)
                    }else{
                        onDeleteClick()
                    }
                    },
                    modifier = Modifier
                        .background(Peach.copy(alpha = 0.3f), CircleShape)
                        .size(24.dp)
                ) {
                    Text(text = "-", fontWeight = FontWeight.Bold, fontFamily = poppinsMediumBold)
                }

                Text(
                    text = "$orderCount",
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = {
                        orderCount++
                        cartPageViewModel.updateCartItemCount(cartItem, orderCount)
                    },
                    modifier = Modifier
                        .background(Peach.copy(alpha = 0.3f), CircleShape)
                        .size(24.dp)
                ) {
                    Text(text = "+", fontWeight = FontWeight.Bold, fontFamily = poppinsMediumBold)
                }
            }
        }

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(30.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "$${cartItem.food_price * cartItem.cart_order_count}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = onDeleteClick) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }

    }
}
