package com.example.graduationproject.data.entities

import com.google.gson.annotations.SerializedName

data class CartRequest (
    @SerializedName("kullanici_adi") val username: String
)