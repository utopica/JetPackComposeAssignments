package com.example.graduationproject.retrofit

import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.CartResponse
import com.example.graduationproject.data.entities.FoodsResponse
import com.example.graduationproject.data.entities.RemoveCartRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodsDao {
    //http://kasimadalan.pe.hu/ -> baseUrl
    //yemekler/tumYemekleriGetir.php -> apiUrl

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getFoods() : FoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(@FieldMap fields: Map<String, String>): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getCartItems(@Field("kullanici_adi") username: String): CartResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun removeFromCart(@Body request: RemoveCartRequest): CRUDResponse
}