package com.example.graduationproject.data.entities

import com.google.gson.annotations.SerializedName

data class Carts (
    @SerializedName("sepet_yemek_id") val cart_food_id : Int,
    @SerializedName("yemek_adi") val food_name : String,
    @SerializedName("yemek_resim_adi") val food_picName: String,
    @SerializedName("yemek_fiyat") val food_price: Int,
    @SerializedName("yemek_siparis_adet") val cart_order_count : Int,
    @SerializedName("kullanici_adi") val username : String
)