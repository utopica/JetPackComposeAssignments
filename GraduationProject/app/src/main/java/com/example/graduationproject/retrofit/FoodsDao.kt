package com.example.graduationproject.retrofit

import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.CartRequest
import com.example.graduationproject.data.entities.CartResponse
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.FoodsResponse
import com.example.graduationproject.data.entities.RemoveCartRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {
    //http://kasimadalan.pe.hu/ -> baseUrl
    //yemekler/tumYemekleriGetir.php -> apiUrl

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getFoods() : FoodsResponse

    @GET("yemekler/resimler/{food_picName}")
    suspend fun getFoodImage(picName: String): String

    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun addToCart(@Body cartItem: Carts): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    suspend fun getCartItems(@Body request: CartRequest): CartResponse

    @POST("yemekler/sepettenYemekSil.php")
    suspend fun removeFromCart(@Body request: RemoveCartRequest): CRUDResponse


}