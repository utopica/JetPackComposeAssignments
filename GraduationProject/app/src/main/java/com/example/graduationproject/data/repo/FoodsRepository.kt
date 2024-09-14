package com.example.graduationproject.data.repo

import com.example.graduationproject.data.datasource.FoodsDataSource
import com.example.graduationproject.data.entities.Foods

class FoodsRepository (var foodsDataSource: FoodsDataSource) {

    suspend fun getFoods() : List<Foods> = foodsDataSource.getFoods()
}