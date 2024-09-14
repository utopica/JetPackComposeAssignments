package com.example.graduationproject.data.datasource

import com.example.graduationproject.data.entities.Foods
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
}