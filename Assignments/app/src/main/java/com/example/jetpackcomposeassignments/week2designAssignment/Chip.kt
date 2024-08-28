package com.example.jetpackcomposeassignments.week2designAssignment

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeassignments.ui.theme.Blue
import com.example.jetpackcomposeassignments.ui.theme.LightBlue
import com.example.jetpackcomposeassignments.ui.theme.Red
import com.example.jetpackcomposeassignments.ui.theme.White
import com.example.jetpackcomposeassignments.ui.theme.Yellow
import com.example.jetpackcomposeassignments.ui.theme.poppinsItalic
import com.example.jetpackcomposeassignments.ui.theme.poppinsMedium
import com.example.jetpackcomposeassignments.ui.theme.poppinsRegular

@Composable
fun Chip(icerik: String, icon: ImageVector, iconSize: Dp = 46.dp,
         iconColor: Color = Blue) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Watch",
                tint = iconColor,
                modifier = Modifier.size(iconSize)

            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = icerik, fontFamily = poppinsItalic, fontSize = 18.sp)


        }

}
