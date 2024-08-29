package com.example.appdesignassignment.uix.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.example.appdesignassignment.ui.theme.Black
import com.example.appdesignassignment.ui.theme.White
import com.example.appdesignassignment.ui.theme.arial

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, darkTheme: Boolean = isSystemInDarkTheme()) {

    val configuration = LocalConfiguration.current
    val backgroundColor = if (darkTheme) Black else White
    val textColor = if (darkTheme) White else Black

    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = { Text(text = "Anasayfa", fontFamily = arial) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                titleContentColor = textColor,
            )) },
        containerColor = backgroundColor

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text("Column")
        }
    }
}