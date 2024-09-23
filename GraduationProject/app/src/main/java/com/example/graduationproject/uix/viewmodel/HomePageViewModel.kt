package com.example.graduationproject.uix.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.entities.Foods
import com.example.graduationproject.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var foodsRepo : FoodsRepository) : ViewModel() {

    var foodsList = MutableLiveData<List<Foods>>()
    var foodImage = MutableLiveData<String>()

    init {
        getFoods()
    }

    fun getFoods(){
        CoroutineScope(Dispatchers.Main).launch {
            foodsList.value = foodsRepo.getFoods()

            Log.e("HomePageViewModel", " images ${foodsList.value}")

        }
    }

    fun getFoodImage(picName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodImage.value = foodsRepo.getFoodImage(picName)

                Log.e("HomePageViewModel", "images ${foodsList}")
            } catch (e: Exception) {
                foodImage.value = "error"
            }
        }
    }
}