package com.example.graduationproject.uix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.entities.AddCartRequest
import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(var foodsRepo : FoodsRepository) : ViewModel() {

    val _cartItems = MutableLiveData<List<Carts>>()
    val cartItems: LiveData<List<Carts>> = _cartItems

    val _cartOperationResult = MutableLiveData<CRUDResponse>()
    val cartOperationResult: LiveData<CRUDResponse> = _cartOperationResult

    fun addToCart(cartItem: AddCartRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = foodsRepo.addToCart(cartItem)
            _cartOperationResult.value = result
            getCartItems(cartItem.username)
        }
    }

    fun getCartItems(username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _cartItems.value = foodsRepo.getCartItems(username)
        }
    }

    fun removeFromCart(cartItemId: Int, username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = foodsRepo.removeFromCart(cartItemId, username)
            _cartOperationResult.value = result
            getCartItems(username)
        }
    }
}