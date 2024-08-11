package com.example.jetpackcomposeassignments.lecture2.functions

class Functions {

    fun celsiusToFahrenheit(celsius: Double): Double {
        return celsius * 1.8 + 32
    }

    fun calculateRectanglePerimeter(length: Double, width: Double): Double {
        return 2 * (length + width)
    }

    fun calculateFactorial(number: Int): Long {
        var factorial: Long = 1
        for (i in 1..number) {
            factorial *= i
        }
        return factorial
    }

    fun countACharacter(input: String): Int {
        return input.count { it == 'a' || it == 'A' }
    }
}