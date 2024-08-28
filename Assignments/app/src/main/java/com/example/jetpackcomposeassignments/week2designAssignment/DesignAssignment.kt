package com.example.jetpackcomposeassignments.week2designAssignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeassignments.R
import com.example.jetpackcomposeassignments.ui.theme.Blue
import com.example.jetpackcomposeassignments.ui.theme.Red
import com.example.jetpackcomposeassignments.ui.theme.White
import com.example.jetpackcomposeassignments.ui.theme.Yellow
import com.example.jetpackcomposeassignments.ui.theme.poppinsMedium
import com.example.jetpackcomposeassignments.ui.theme.poppinsMediumBold
import com.example.jetpackcomposeassignments.week2designAssignment.ui.theme.JetPackComposeAssignmentsTheme

class DesignAssignment : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetPackComposeAssignmentsTheme {
                Anasayfa()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(darkTheme: Boolean = isSystemInDarkTheme()){

    val configuration = LocalConfiguration.current

    var ekranGenisliği = configuration.screenWidthDp
    var ekranYuksekliği = configuration.screenHeightDp

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(text = stringResource(id = R.string.appTopBar_name), fontFamily = poppinsMedium, fontSize =  (ekranGenisliği/18).sp)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                ),
                navigationIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = White,

                        )
                    }

                    MenuDropdown(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        onMenuItemClick = {
                            expanded = false

                        }
                    )
                }
                )


        }
    ){
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
            ){
            Text(text = stringResource(id = R.string.movie_name), color = if(darkTheme) White else Color.Black, fontFamily = poppinsMedium, fontSize = (ekranGenisliği/17).sp)
            Image(painter = painterResource(id = R.drawable.kiki), contentDescription = "movie poster")

            Text(text = stringResource(id = R.string.director_name), color = if(darkTheme) White else Color.Black, fontFamily = poppinsMedium, fontSize = (ekranGenisliği/18).sp)
            Text(text = stringResource(id = R.string.duration), color = if(darkTheme) White else Color.Black, fontFamily = poppinsMedium, fontSize = (ekranGenisliği/18).sp)

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Chip(icerik = stringResource(id = R.string.playButtonText), icon = Icons.Default.PlayArrow)
                Spacer(modifier = Modifier.width((ekranGenisliği/18).dp))
                Chip(icerik = stringResource(id = R.string.favouriteButtonText), icon = Icons.Default.Favorite, iconColor = Red, iconSize = (ekranGenisliği/14).dp)
            }


        }

    }
}

@Preview(showBackground = true, locale = "tr")
@Composable
fun AnasayfaPreview() {
    JetPackComposeAssignmentsTheme {
        Anasayfa()
    }
}