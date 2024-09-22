package com.example.graduationproject.data.datasource

import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.CartRequest
import com.example.graduationproject.data.entities.CartResponse
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.data.entities.RemoveCartRequest
import com.example.graduationproject.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodsDataSource (var foodsdao : FoodsDao){

    suspend fun getFoods() : List<Foods> = withContext(Dispatchers.IO){
        return@withContext foodsdao.getFoods().foods
    }

    suspend fun getFoodImage(picName: String): String = withContext(Dispatchers.IO) {
        return@withContext foodsdao.getFoodImage(picName)
    }

    suspend fun addToCart(cartItem: Carts): CRUDResponse = withContext(Dispatchers.IO) {
        foodsdao.addToCart(cartItem)
    }

    suspend fun getCartItems(username: String): List<Carts> = withContext(Dispatchers.IO) {
        foodsdao.getCartItems(CartRequest(username)).cartItems
    }

    suspend fun removeFromCart(cartItemId: Int, username: String): CRUDResponse = withContext(Dispatchers.IO) {
        foodsdao.removeFromCart(RemoveCartRequest(cartItemId, username))
    }


}