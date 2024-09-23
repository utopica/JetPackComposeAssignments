package com.example.graduationproject.data.repo

import com.example.graduationproject.data.datasource.FoodsDataSource
import com.example.graduationproject.data.entities.AddCartRequest
import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.entities.Foods

class FoodsRepository (var foodsDataSource: FoodsDataSource) {

    suspend fun getFoods(): List<Foods> = foodsDataSource.getFoods()

    suspend fun addToCart(cartItem: Map<String, String>): CRUDResponse = foodsDataSource.addToCart(cartItem)

    suspend fun getCartItems(username: String): List<Carts> = foodsDataSource.getCartItems(username)

    suspend fun removeFromCart(cartItemId: Int, username: String): CRUDResponse = foodsDataSource.removeFromCart(cartItemId, username)

}