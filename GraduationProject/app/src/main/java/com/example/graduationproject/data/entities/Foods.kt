package com.example.graduationproject.data.entities

import com.google.gson.annotations.SerializedName

data class Foods (
    @SerializedName("yemek_id") val food_id: Int,
    @SerializedName("yemek_adi") val food_name: String,
    @SerializedName("yemek_resim_adi") val food_picName: String,
    @SerializedName("yemek_fiyat") val food_price: Int
)