package com.example.graduationproject.data.entities

data class Carts (
    val cart_food_id : Int,
    val food_name : String,
    val food_picName: String,
    val food_price: Int,
    val cart_order_count : Int,
    val username : String
)