package com.example.graduationproject.uix.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.data.entities.AddCartRequest
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(
    navController: NavController,
    cartPageViewModel: CartPageViewModel,
    foodName: String,
    foodPicName: String,
    foodPrice: Int,
    orderCount: Int,
    username : String
) {

    val cartItems by cartPageViewModel.cartItems.observeAsState(listOf())

    Log.e("CartPage", "Item did not add to cart: ${cartItems}")

    var error by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(key1 = true) {
        val newCartItem = Carts(
            cart_food_id = 0,
            food_name = foodName,
            food_picName = foodPicName,
            food_price = foodPrice,
            cart_order_count = orderCount,
            username = username
        )

        cartPageViewModel.addToCart(newCartItem)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (cartItems.isNullOrEmpty()) {
            Log.e("CartPage", "Cart is empty or data is not loaded")
            Text("Your cart is empty", modifier = Modifier.padding(16.dp))
        } else {
            Log.e("CartPage", "Cart items loaded: ${cartItems.size}")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(cartItem, cartPageViewModel, username)
                }
            }
        }

        error?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(16.dp))
        }

    }
}


@Composable
fun CartItemCard(cartItem: Carts, cartViewModel: CartPageViewModel, username: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${cartItem.food_picName}",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = cartItem.food_name, fontWeight = FontWeight.Bold)
                Text(text = "Price: $${cartItem.food_price}")
                Text(text = "Quantity: ${cartItem.cart_order_count}")
            }
            IconButton(
                onClick = {
                    cartViewModel.removeFromCart(cartItem.cart_food_id, username)
                }
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Remove from cart")
            }
        }
    }
}