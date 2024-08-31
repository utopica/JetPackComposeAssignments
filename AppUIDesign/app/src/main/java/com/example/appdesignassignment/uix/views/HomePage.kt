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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appdesignassignment.ui.theme.Black
import com.example.appdesignassignment.ui.theme.White
import com.example.appdesignassignment.ui.theme.arial
import com.example.appdesignassignment.R
import com.example.appdesignassignment.data.entity.Pins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, darkTheme: Boolean = isSystemInDarkTheme()) {

    val backgroundColor = if (darkTheme) Black else White
    val textColor = if (darkTheme) White else Black

    val pinList = remember { mutableStateListOf<Pins>() }

    LaunchedEffect(key1 = true) {
        val pin1 = Pins(1, "Cat", "cat", "mouse")
        val pin2 = Pins(2, "Coffee Shop", "coffeeshop", "mouse")
        val pin3 = Pins(3, "Flower", "flower", "mouse")
        val pin4 = Pins(4, "Lime", "lime", "mouse")
        val pin5 = Pins(5, "Monet", "monet", "mouse")
        val pin6 = Pins(6, "Mouse", "mouse", "mouse")
        val pin7 = Pins(7, "Outfit", "outfit", "mouse")
        val pin8 = Pins(8, "Sculpture", "sculpture", "mouse")

        pinList.add(pin1)
        pinList.add(pin2)
        pinList.add(pin3)
        pinList.add(pin4)
        pinList.add(pin5)
        pinList.add(pin6)
        pinList.add(pin7)
        pinList.add(pin8)
    }

    Scaffold(
        containerColor = backgroundColor,

        bottomBar = {
            NavigationBar(
                containerColor = backgroundColor,
                modifier = Modifier.padding(0.dp)
            ){
                NavigationBarItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = { Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = textColor,
                        modifier = Modifier.size(40.dp, 40.dp)

                    ) })

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = textColor,
                        modifier = Modifier.size(40.dp, 40.dp)

                    ) })

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = textColor,
                        modifier = Modifier.size(40.dp, 40.dp)

                    ) })

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = "Message",
                        tint = textColor,
                        modifier = Modifier.size(40.dp, 40.dp)

                    ) })

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = textColor,
                        modifier = Modifier.size(40.dp, 40.dp)

                    ) })
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Top tabs
            ScrollableTabRow(
                selectedTabIndex = 0,
                containerColor = backgroundColor,
                contentColor = textColor
            ) {
                Tab(selected = true, onClick = { /*TODO*/ }) {
                    Text("Tümü", color = textColor, fontFamily = arial)
                }
                Tab(selected = false, onClick = { /*TODO*/ }) {
                    Text("wallpaper yaparsın al b...", color = textColor, fontFamily = arial)
                }
                Tab(selected = false, onClick = { /*TODO*/ }) {
                    Text("ootd", color = textColor, fontFamily = arial)
                }
                Tab(selected = false, onClick = { /*TODO*/ }) {
                    Text("-dood", color = textColor, fontFamily = arial)
                }
            }

            // Content Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .wrapContentHeight()
                ) {
                    items(pinList.filterIndexed { index, _ -> index % 2 == 0 }) { pin ->
                        PinItem(pin)
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    items(pinList.filterIndexed { index, _ -> index % 2 == 1 }) { pin ->
                        PinItem(pin)
                    }
                }
            }
            //lazy vertical grid
        }
    }
}

@Composable
fun PinItem(pin: Pins) {
    val activity = (LocalContext.current as Activity)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Container for pinUrl image
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        id = activity.resources.getIdentifier(pin.pinUrl, "drawable", activity.packageName)
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEFEFEF))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        id = activity.resources.getIdentifier(pin.pinProfilePictureUrl, "drawable", activity.packageName)
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Image(
                    modifier = Modifier
                        .clickable { /* Handle click */ }
                        .size(28.dp),
                    painter = painterResource(id = R.drawable.threedotblack),
                    contentDescription = null,
                )
            }
        }
    }
}