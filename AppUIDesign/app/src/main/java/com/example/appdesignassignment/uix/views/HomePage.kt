package com.example.appdesignassignment.uix.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appdesignassignment.ui.theme.Black
import com.example.appdesignassignment.ui.theme.White
import com.example.appdesignassignment.R
import com.example.appdesignassignment.data.entity.Boards
import com.example.appdesignassignment.data.entity.Pins
import com.example.appdesignassignment.ui.theme.DarkModeIconColor
import com.example.appdesignassignment.ui.theme.IconColor
import com.example.appdesignassignment.ui.theme.arialBold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, darkTheme: Boolean = isSystemInDarkTheme()) {

    val backgroundColor = if (darkTheme) Black else White
    val textColor = if (darkTheme) White else Black
    val iconColor = if (darkTheme) DarkModeIconColor else IconColor

    val pinList = remember { mutableStateListOf<Pins>() }
    val boardList = remember { mutableStateListOf<Boards>() }

    val selectedNavIndex = remember { mutableStateOf(0) }
    val selectedTabIndex = remember { mutableStateOf(0) } // Separate state for tabs

    LaunchedEffect(key1 = true) {
        val pins = listOf(
            Pins(1, "Flowers On Bed", "flowersonbed", "profilegirl"),
            Pins(2, "Bedroom Decore", "bedroomdecore", "defaultprofile"),
            Pins(3, "Butterfly", "butterfly", "profilegirl"),
            Pins(4, "Lime", "lime", "profilegirl"),
            Pins(5, "Monet", "monet", "profilegirl"),
            Pins(6, "Outfit Fall", "outfitfall", "profilegreen"),
            Pins(7, "Flower", "flower", "profilegreen"),
            Pins(8, "Coffee Shop", "coffeeshop", "profilenature"),
            Pins(9, "Outfit", "outfit", "profilegirl"),
            Pins(10, "Sculpture", "sculpture", "profilegreen"),
            Pins(11, "HomeDecor", "homedecor", "defaultprofile"),
            Pins(12, "Cat With Scarf", "catwithscarf", "mouse"),
            Pins(13, "Purple Cafe", "purplecafe", "profilenature"),
            Pins(14, "Purple Wp", "purplewp", "defaultprofile"),
            Pins(15, "Reader Girl", "readergirl", "profilegirl"),
            Pins(16, "Ship", "ship", "profilenature"),
            Pins(17, "Country Girl", "countrygirl", "profilecat"),
            Pins(18, "Cat", "cat", "mouse"),
            Pins(19, "Painting", "painting", "profilegreen"))
        pinList.addAll(pins)

        val boards = listOf(
            Boards(1, "Tümü"),
            Boards(2, "fikirler"),
            Boards(3, "ootd"),
            Boards(4, "wallpaper")
        )
        boardList.addAll(boards)
    }

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            NavigationBar(
                containerColor = backgroundColor,
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
                        painterResource(id = R.drawable.plus) to "Add",
                        painterResource(id = R.drawable.chat) to "Chat",
                        painterResource(id = R.drawable.profile) to "Profile",
                    )

                    navigationItems.forEachIndexed { index, (icon, contentDescription) ->
                        IconButton(
                            onClick = { selectedNavIndex.value = index }
                        ) {
                            when (icon) {
                                is Painter -> Icon(
                                    painter = icon,
                                    contentDescription = contentDescription,
                                    tint = if (selectedNavIndex.value == index) textColor else iconColor,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Top tabs
            if (boardList.isNotEmpty()) { // Ensure boardList is populated
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex.value,
                    containerColor = backgroundColor,
                    contentColor = textColor,
                    edgePadding = 16.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            height = 5.dp,
                            color = textColor
                        )
                    },
                    divider = {
                        Divider(color = Color.Transparent)
                    },


                ) {
                    boardList.forEachIndexed { index, board ->
                        Tab(
                            selected = selectedTabIndex.value == index,
                            onClick = { selectedTabIndex.value = index },
                            text = {
                                Text(
                                    text = board.name,
                                    color = textColor,
                                    fontFamily = arialBold,
                                    fontSize = 20.sp,

                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val scrollState = rememberScrollState()

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 4.dp)
                ) {
                    pinList.filterIndexed { index, _ -> index % 2 == 0 }.forEach { pin ->
                        PinItem(pin)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp, end = 8.dp)
                ) {
                    pinList.filterIndexed { index, _ -> index % 2 == 1 }.forEach { pin ->
                        PinItem(pin)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PinItem(pin: Pins, darkTheme: Boolean = isSystemInDarkTheme()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Column {
            Image(
                bitmap = ImageBitmap.imageResource(
                    id = LocalContext.current.resources.getIdentifier(pin.pinUrl, "drawable", LocalContext.current.packageName)
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().background(if (darkTheme) Black else White).clip(RoundedCornerShape(20.dp))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (darkTheme) Black else White)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        id = LocalContext.current.resources.getIdentifier(pin.pinProfilePictureUrl, "drawable", LocalContext.current.packageName)
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                )

                Image(
                    painter = painterResource(id = R.drawable.threedotblack),
                    contentDescription = "More options",
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}

