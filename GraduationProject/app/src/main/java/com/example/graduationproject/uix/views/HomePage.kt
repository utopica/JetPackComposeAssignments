package com.example.graduationproject.uix.views

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.ui.theme.Orange
import com.example.graduationproject.ui.theme.PeachContainer
import com.example.graduationproject.ui.theme.PeachSearch
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
                modifier = Modifier
                    .padding(2.dp, 5.dp),
                title = {
                    SearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painterResource(id = R.drawable.menu),
                            modifier = Modifier
                                .size(24.dp),
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painterResource(id = R.drawable.filter),
                            modifier = Modifier
                                .size(24.dp),
                            contentDescription = "Filter"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PeachContainer)

            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = PeachContainer,
        bottomBar = {
            BottomNavigationBar(navController = navController)
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
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier
            .height(40.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 0.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = PeachContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = { },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        textStyle = LocalTextStyle.current.copy(
            color = Color.Black,
            fontSize = 5.sp
        )
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

    var orderCount = remember { mutableStateOf(0) }

    val username = "elif_okumus"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .clickable {
                val foodJson = Gson().toJson(food)
                detailsPageViewModel.orderCount = orderCount.value
                navController.navigate("detailsPage/$foodJson/${orderCount.value}")
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
            ) {
                if (orderCount.value > 0) {
                    Text(text = "${orderCount.value}", fontFamily = poppinsMedium, fontSize = 20.sp)
                }
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                orderCount.value += 1

                                val cartItem = Carts(
                                    cart_food_id = 0,
                                    food_name = food.food_name,
                                    food_picName = food.food_picName,
                                    food_price = food.food_price,
                                    cart_order_count = orderCount.value,
                                    username = username
                                )

                                cartPageViewModel.addToCart(cartItem)

                                isPlaying = true
                                progress = 0f

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

@Composable
fun BottomNavigationBar(navController: NavController) {

    val selectedNavIndex = remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = PeachContainer,
        modifier = Modifier.height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val navigationItems = listOf(
                painterResource(id = R.drawable.home) to "Home",
                painterResource(id = R.drawable.search) to "Search",
                painterResource(id = R.drawable.cart) to "Cart",
                painterResource(id = R.drawable.user) to "Profile",
            )

            navigationItems.forEachIndexed { index, (icon, contentDescription) ->
                IconButton(
                    onClick = {
                        selectedNavIndex.value = index

                        when (index) {
                            0 -> navController.navigate("homePage")
                            1 -> navController.navigate("searchPage")
                            2 -> navController.navigate("cartPage")
                            3 -> navController.navigate("profilePage")
                        }
                    }
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = contentDescription,
                        tint = if (selectedNavIndex.value == index) Orange else Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}