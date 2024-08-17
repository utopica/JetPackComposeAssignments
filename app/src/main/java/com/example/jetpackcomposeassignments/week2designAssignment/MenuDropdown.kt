package com.example.jetpackcomposeassignments.week2designAssignment

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeassignments.ui.theme.LightBlue
import com.example.jetpackcomposeassignments.ui.theme.poppinsRegular

@Composable
fun MenuDropdown(expanded: Boolean,
                 onDismissRequest: () -> Unit,
                 onMenuItemClick: (String) -> Unit,
                 darkTheme: Boolean = isSystemInDarkTheme(),
                 menuItems: List<String> = listOf("Home Page", "Watch List", "My Favourites"),
                 menuColor: Color = if (darkTheme) Color.White else Color.Black,
                 menuBackgroundColor: Color = LightBlue,
                 fontSizeRatio: Int = 24,

                 ) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val calculatedFontSize = (screenWidth/fontSizeRatio).sp

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .width(200.dp)
            .background(menuBackgroundColor)
    ) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                text = { Text(item, color = menuColor, fontSize = calculatedFontSize, fontFamily = poppinsRegular) },
                onClick = { onMenuItemClick(item) }
            )
        }
    }
}