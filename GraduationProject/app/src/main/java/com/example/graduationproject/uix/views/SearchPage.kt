package com.example.graduationproject.uix.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.ui.theme.poppinsMedium


@Composable
fun SearchPage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Search Page Coming Soon!", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsMedium)
    }
}