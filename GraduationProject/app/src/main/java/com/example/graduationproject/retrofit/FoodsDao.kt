package com.example.graduationproject.retrofit

import com.example.graduationproject.data.entities.FoodsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FoodsDao {
    //http://kasimadalan.pe.hu/ -> baseUrl
    //yemekler/tumYemekleriGetir.php -> apiUrl

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getFoods() : FoodsResponse

    @GET("yemekler/resimler/{food_picName}")
    suspend fun getFoodImage(picName: String): String
}