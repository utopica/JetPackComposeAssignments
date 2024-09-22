package com.example.graduationproject.uix.views

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.graduationproject.R
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.ui.theme.PeachText
import com.example.graduationproject.ui.theme.poppinsMedium
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.example.graduationproject.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel, cartPageViewModel: CartPageViewModel) {

    val foodsList by homePageViewModel.foodsList.observeAsState(listOf())

    var searchQuery by remember { mutableStateOf("") }


    LaunchedEffect(key1 = true) {
        homePageViewModel.getFoods()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp),
                title = {
                    SearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(40.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(painterResource(id = R.drawable.menu), modifier = Modifier.size(24.dp), contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(painterResource(id = R.drawable.filter), modifier = Modifier.size(24.dp), contentDescription = "Filter")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DiscountCard()
            }

            item {
                RecommendedSection(foodsList = foodsList, homePageViewModel = homePageViewModel, cartPageViewModel = cartPageViewModel , navController = navController)
            }

            item {
                IconButton(onClick = { navController.navigate("cartPage") }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Go to Cart")
                }
            }
        }

    }
}

@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier,
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
fun DiscountCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.5f),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = PeachText, contentColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "-50% off the full price of traditional foods",
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = poppinsMedium,
                    fontSize = 22.sp
                )

            }
            Image(
                painter = painterResource(id = R.drawable.sarma),
                contentDescription = "Traditional Food",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
fun RecommendedSection(foodsList: List<Foods>, homePageViewModel: HomePageViewModel, cartPageViewModel: CartPageViewModel, navController: NavController) {
    Column {
        Text(
            "Recommended",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(foodsList.size) { index ->
                    FoodItem(food = foodsList[index], homePageViewModel = homePageViewModel, cartPageViewModel = cartPageViewModel , navController = navController)
                }
            },
            modifier = Modifier.height(400.dp) // Adjust this value as needed
        )
    }
}
@Composable
fun FoodItem(food: Foods, homePageViewModel: HomePageViewModel, cartPageViewModel: CartPageViewModel, navController: NavController) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.cart)
    )
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    val username = "elif_okumus"

    LaunchedEffect(food.food_picName) {
        homePageViewModel.getFoodImage(food.food_picName)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable {
                val foodJson = Gson().toJson(food)
                navController.navigate("detailsPage/$foodJson")
            },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${food.food_picName}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = food.food_name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = poppinsMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$${food.food_price}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsMedium,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                                try {
                                    val cartItem = Carts(
                                        cart_food_id = food.food_id,
                                        food_name = food.food_name,
                                        food_picName = food.food_picName,
                                        food_price = food.food_price,
                                        cart_order_count = 1,
                                        username = username
                                    )
                                    cartPageViewModel.addToCart(cartItem)
                                    isPlaying = true
                                    progress = 0f
                                } catch (e: Exception) {
                            e.printStackTrace()
                            // Handle error, perhaps show a toast or snackbar
                        }

                        },
                    contentAlignment = Alignment.CenterEnd
                ) {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(70.dp).clickable {
                            isPlaying = true
                            progress = 0f
                        },
                        progress = { progress },
                    )

                    LaunchedEffect(isPlaying) {
                        if (isPlaying) {
                            animate(
                                0f,
                                1f,
                                animationSpec = tween(durationMillis = 1000)
                            ) { value, _ ->
                                progress = value
                            }
                            isPlaying = false
                        }
                    }
                }
            }

        }
    }
}


