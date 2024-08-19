package com.example.jetpackcomposeassignments.week3_page_navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow


class Anasayfa : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text(text = "Anasayfa")})}
        ){
            paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                Button(
                    onClick = {
                        navigator.push(SayfaA())
                    }
                ){
                    Text(text = "Sayfa A")
                }

                Button(
                    onClick = {
                        navigator.push(SayfaX())
                    }
                ){
                    Text(text = "Sayfa X")
                }
            }
        }
    }

}