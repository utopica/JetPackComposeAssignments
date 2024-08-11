package com.example.jetpackcomposeassignments.lecture2.functions

fun main(){

    val function = Functions()

    println("30 degrees Celsius in Fahrenheit: ${function.celsiusToFahrenheit(32.0)} F")

    println("Perimeter of the rectangle:  ${function.calculateRectanglePerimeter(7.0, 12.0)}")

    println("Factorial of 5: ${function.calculateFactorial(4)}")

    println("Count of 'a' characters in the input: ${function.countACharacter
        ("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum et pellentesque enim, et sagittis sem. Sed laoreet sed lacus nec efficitur.")}")
}