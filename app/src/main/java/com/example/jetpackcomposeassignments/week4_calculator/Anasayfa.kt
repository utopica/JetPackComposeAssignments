package com.example.jetpackcomposeassignments.week4_calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeassignments.R
import com.example.jetpackcomposeassignments.ui.theme.poppinsMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa() {

    val displayValue = remember { mutableStateOf("0") }
    val currentOperation = remember { mutableStateOf<String?>(null) }
    val firstOperand = remember { mutableStateOf<Double?>(null) }
    val result = remember { mutableStateOf<String?>(null) }

    val buttons = listOf(
        listOf("7", "8", "9", "AC"),
        listOf("4", "5", "6", "+"),
        listOf("1", "2", "3", "Del"),
        listOf("+/-", "0", ",", "=")
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Calculator") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${firstOperand.value?.let { "${it} ${currentOperation.value ?: ""} " } ?: ""}${if (currentOperation.value != null) displayValue.value else ""}",
                    fontSize = 24.sp,
                    fontFamily = poppinsMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = result.value ?: displayValue.value,
                    fontSize = 36.sp,
                    fontFamily = poppinsMedium,
                )
            }

            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    row.forEach { button ->
                        Button(
                            onClick = {
                                when (button) {
                                    "AC" -> {
                                        displayValue.value = "0"
                                        result.value = null
                                        firstOperand.value = null
                                        currentOperation.value = null
                                    }

                                    "Del" -> {
                                        if (displayValue.value.length > 1) {
                                            displayValue.value = displayValue.value.dropLast(1)
                                        } else {
                                            displayValue.value = "0"
                                        }
                                    }

                                    "+" -> {
                                        if (currentOperation.value != null && firstOperand.value != null) {
                                            calculateResult(firstOperand, displayValue, result, currentOperation)
                                        }
                                        firstOperand.value = displayValue.value.toDouble()
                                        currentOperation.value = "+"
                                        displayValue.value = "0"
                                    }

                                    "=" -> {
                                        if (currentOperation.value != null && firstOperand.value != null) {
                                            calculateResult(firstOperand, displayValue, result, currentOperation)
                                            currentOperation.value = null
                                        }
                                    }

                                    "+/-" -> {
                                        displayValue.value = if (displayValue.value.startsWith("-")) {
                                            displayValue.value.removePrefix("-")
                                        } else {
                                            "-${displayValue.value}"
                                        }
                                    }

                                    "," -> {
                                        if (!displayValue.value.contains(".")) {
                                            displayValue.value += "."
                                        }
                                    }

                                    else -> {
                                        displayValue.value = if (displayValue.value == "0") button else displayValue.value + button
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                        ) {
                            when (button) {
                                "+/-" -> Image(painter = painterResource(id = R.drawable.plus_minus), contentDescription = button)
                                "+" -> Image(painter = painterResource(id = R.drawable.plus), contentDescription = button)
                                "Del" -> Image(painter = painterResource(id = R.drawable.delete), contentDescription = button)
                                "=" -> Image(painter = painterResource(id = R.drawable.equal), contentDescription = button)
                                "AC" -> Image(painter = painterResource(id = R.drawable.eraser), contentDescription = button)
                                else -> Text(text = button, fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
