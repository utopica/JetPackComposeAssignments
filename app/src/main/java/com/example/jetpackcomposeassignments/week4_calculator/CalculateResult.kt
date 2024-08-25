package com.example.jetpackcomposeassignments.week4_calculator

import androidx.compose.runtime.MutableState

fun calculateResult(
    firstOperand: MutableState<Double?>,
    displayValue: MutableState<String>,
    result: MutableState<String?>,
    currentOperation: MutableState<String?>
) {
    when (currentOperation.value) {
        "+" -> {
            result.value = (firstOperand.value!! + displayValue.value.toDouble()).toString()
        }
    }
    displayValue.value = result.value.toString()
    firstOperand.value = result.value?.toDouble()
}
