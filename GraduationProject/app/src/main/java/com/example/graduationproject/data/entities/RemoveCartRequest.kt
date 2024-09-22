package com.example.graduationproject.data.entities

import com.google.gson.annotations.SerializedName

data class RemoveCartRequest(
    @SerializedName("sepet_yemek_id") val cartItemId: Int,
    @SerializedName("kullanici_adi") val username: String
)