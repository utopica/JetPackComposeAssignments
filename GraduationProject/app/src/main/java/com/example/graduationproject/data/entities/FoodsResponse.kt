package com.example.graduationproject.data.entities

import com.google.gson.annotations.SerializedName

class FoodsResponse (
    @SerializedName("yemekler") val foods: List<Foods>,
    @SerializedName("success") val success: Int
)