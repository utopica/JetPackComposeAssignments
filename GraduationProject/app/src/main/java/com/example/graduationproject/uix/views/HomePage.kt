package com.example.graduationproject.uix.views

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.ui.theme.Orange
import com.example.graduationproject.ui.theme.PeachContainer
import com.example.graduationproject.ui.theme.poppinsMedium
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.example.graduationproject.uix.viewmodel.DetailsPageViewModel
import com.example.graduationproject.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController,
    homePageViewModel: HomePageViewModel,
    cartPageViewModel: CartPageViewModel,
    detailsPageViewModel: DetailsPageViewModel
) {

    val foodsList by homePageViewModel.foodsList.observeAsState(listOf())

    var searchQuery by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        homePageViewModel.getFoods()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp).background(PeachContainer),
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
                    IconButton(onClick = { }) {
                        Icon(
                            painterResource(id = R.drawable.menu),
                            modifier = Modifier.size(24.dp).background(PeachContainer),
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painterResource(id = R.drawable.filter),
                            modifier = Modifier.size(24.dp).background(PeachContainer),
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = PeachContainer
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
                RecommendedSection(
                    foodsList = foodsList,
                    homePageViewModel = homePageViewModel,
                    cartPageViewModel = cartPageViewModel,
                    detailsPageViewModel = detailsPageViewModel,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )
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
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
        colors = CardDefaults.cardColors(containerColor = Orange, contentColor = Color.White)
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
fun RecommendedSection(
    foodsList: List<Foods>,
    homePageViewModel: HomePageViewModel,
    cartPageViewModel: CartPageViewModel,
    detailsPageViewModel: DetailsPageViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
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
                    FoodItem(
                        food = foodsList[index],
                        homePageViewModel = homePageViewModel,
                        cartPageViewModel = cartPageViewModel,
                        detailsPageViewModel = detailsPageViewModel,
                        navController = navController,
                        snackbarHostState = snackbarHostState,
                        scope = scope
                    )
                }
            },
            modifier = Modifier.height(400.dp)
        )
    }
}

@Composable
fun FoodItem(
    food: Foods,
    homePageViewModel: HomePageViewModel,
    cartPageViewModel: CartPageViewModel,
    detailsPageViewModel: DetailsPageViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.cart)
    )
    var isPlaying by remember { mutableStateOf(false) }

    var progress by remember { mutableStateOf(0f) }

    var order_count = remember { mutableStateOf(0) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .clickable {
                val foodJson = Gson().toJson(food)
                detailsPageViewModel.orderCount = order_count.value
                navController.navigate("detailsPage/$foodJson/${order_count.value}")
            },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "${order_count.value}", fontFamily = poppinsMedium, fontSize = 20.sp)
            }
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
                Column{
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier
                            .size(70.dp)
                            .clickable {
                                isPlaying = true
                                progress = 0f

                                order_count.value += 1

                                scope.launch {
                                    snackbarHostState.showSnackbar("${food.food_name} added to cart.")
                                }
                            },
                        progress = { progress },
                    )

                    LaunchedEffect(isPlaying) {
                        if (isPlaying) {
                            animate(
                                0f, 1f, animationSpec = tween(durationMillis = 1000)
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



