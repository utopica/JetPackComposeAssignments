package com.example.appdesignassignment.uix.views

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appdesignassignment.ui.theme.Black
import com.example.appdesignassignment.ui.theme.White
import com.example.appdesignassignment.ui.theme.arial
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
            Pins(1, "Cat", "cat", "mouse"),
            Pins(2, "Coffee Shop", "coffeeshop", "mouse"),
            Pins(3, "Flower", "flower", "mouse"),
            Pins(4, "Lime", "lime", "mouse"),
            Pins(5, "Monet", "monet", "mouse"),
            Pins(6, "Mouse", "mouse", "mouse"),
            Pins(7, "Outfit", "outfit", "mouse"),
            Pins(8, "Sculpture", "sculpture", "mouse")
        )
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
            ) {
                val navigationItems = listOf(
                    Icons.Default.Home to "Home",
                    Icons.Default.Search to "Search",
                    Icons.Default.Add to "Add",
                    painterResource(id = R.drawable.message) to "Message",
                    Icons.Default.Person to "Profile"
                )

                navigationItems.forEachIndexed { index, (icon, contentDescription) ->
                    NavigationBarItem(
                        selected = selectedNavIndex.value == index,
                        onClick = { selectedNavIndex.value = index },
                        icon = {
                            when (icon) {
                                is ImageVector -> Icon(
                                    imageVector = icon,
                                    contentDescription = contentDescription,
                                    tint = if (selectedNavIndex.value == index) textColor else iconColor,
                                    modifier = Modifier.size(40.dp)
                                )
                                is Painter -> Icon(
                                    painter = icon,
                                    contentDescription = contentDescription,
                                    tint = if (selectedNavIndex.value == index) textColor else iconColor,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = textColor,
                            unselectedIconColor = iconColor
                        )
                    )
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
                        TabRowDefaults. SecondaryIndicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                                .width(tabPositions[selectedTabIndex.value].contentWidth)
                                .clip(RoundedCornerShape(5.dp)),
                            height = 5.dp,
                            color = textColor
                        )

                    },
                    divider = { // Remove the default gray divider line
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
                                    fontSize = 20.sp
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

