package com.example.graduationproject.uix.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.ui.theme.poppinsMedium
import com.example.graduationproject.uix.viewmodel.AuthState
import com.example.graduationproject.uix.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()
    val currentUser = authViewModel.currentUser.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontFamily = poppinsMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (authState.value) {
                is AuthState.Authenticated -> LoggedInProfile(currentUser.value, authViewModel, navController)
                is AuthState.Unauthenticated -> LoggedOutProfile(navController)
                else -> Unit
            }
        }
    }
}

@Composable
fun LoggedInProfile(currentUser : com.google.firebase.auth.FirebaseUser? ,authViewModel: AuthViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = currentUser?.displayName ?: currentUser?.email ?: "User",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = poppinsMedium
        )

        currentUser?.email?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        ProfileOption(icon = Icons.Default.Person, text = "Edit Profile")
        ProfileOption(icon = Icons.Default.Lock, text = "Change Password")
        ProfileOption(icon = Icons.Default.Settings, text = "Settings")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                authViewModel.signout()
                navController.navigate("login") {
                    popUpTo("profile") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Sign Out")
            Spacer(Modifier.width(8.dp))
            Text("Sign Out", fontFamily = poppinsMedium)
        }
    }
}

@Composable
fun ProfileOption(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge, fontFamily = poppinsMedium)
    }
}

@Composable
fun LoggedOutProfile(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You're not logged in",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = poppinsMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("login") }
        ) {
            Text("Go to Login", fontFamily = poppinsMedium)
        }
    }
}