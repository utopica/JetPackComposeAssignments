package com.example.graduationproject.data.entities

import com.google.gson.annotations.SerializedName

data class CartResponse (
    @SerializedName("sepet_yemekler") val cartItems: List<Carts>,
    @SerializedName("success") val success: Int
)