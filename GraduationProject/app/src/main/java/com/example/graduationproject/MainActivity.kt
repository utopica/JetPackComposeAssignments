package com.example.graduationproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.graduationproject.ui.theme.GraduationProjectTheme
import com.example.graduationproject.uix.viewmodel.AuthViewModel
import com.example.graduationproject.uix.viewmodel.CartPageViewModel
import com.example.graduationproject.uix.viewmodel.HomePageViewModel
import com.example.graduationproject.uix.views.PageNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homePageViewModel: HomePageViewModel by viewModels()
    val cartPageViewModel: CartPageViewModel by viewModels()
    val authViewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraduationProjectTheme {
                PageNavigation(homePageViewModel, cartPageViewModel, authViewModel)
            }
        }
    }
}
