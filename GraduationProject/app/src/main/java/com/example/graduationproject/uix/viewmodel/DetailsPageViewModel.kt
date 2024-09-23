package com.example.graduationproject.uix.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsPageViewModel @Inject constructor(var foodsRepo : FoodsRepository) : ViewModel(){
    var orderCount by mutableStateOf(1)
}