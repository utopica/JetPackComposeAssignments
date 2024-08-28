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
        var count = 0
        input.forEach {
            if (it == 'a' || it == 'A') {
                count++
            }
        }
        return count
    }

    fun calculateInteriorAngles(sides: Int): Int {
        return (sides - 2) * 180
    }

    fun calculateSalary(hoursWorked: Int): Int {
        val regularRate = 10
        val overtimeRate = 20
        val maxRegularHours = 160

        return if (hoursWorked <= maxRegularHours) {
            hoursWorked * regularRate
        } else {
            val overtimeHours = hoursWorked - maxRegularHours
            (maxRegularHours * regularRate) + (overtimeHours * overtimeRate)
        }
    }

    fun calculateQuotaCost(quotaUsed: Double): Double {
        val baseQuota = 50.0
        val baseCost = 100.0
        val extraCostPerGb = 4.0

        return if (quotaUsed <= baseQuota) {
            baseCost
        } else {
            val extraQuota = quotaUsed - baseQuota
            baseCost + (extraQuota * extraCostPerGb)
        }
    }
}