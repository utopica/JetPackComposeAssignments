package com.example.graduationproject.uix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.entities.AddCartRequest
import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.CartRequest
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
    val cartItems: LiveData<List<Carts>> get() = _cartItems

    val _cartOperationResult = MutableLiveData<CRUDResponse>()
    val cartOperationResult: LiveData<CRUDResponse> = _cartOperationResult

    fun addToCart(cartRequest: AddCartRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("CartPageViewModel", "Adding to cart: $cartRequest")

            val result = foodsRepo.addToCart(cartRequest)
            _cartOperationResult.value = result
            getCartItems(cartRequest.username)

            Log.e("CartPageViewModel", "API Response: success=${result.success}, message=${result.message}")

            if(result.success == 1){
                Log.e("HomePage", "Item added to cart")

            }else{
                Log.e("HomePage", "Item did not add to cart")

            }
        }
    }

    fun getCartItems(username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val items = foodsRepo.getCartItems(username = "elif_okumus")
            _cartItems.value = items
        }
    }

    fun removeFromCart(cartItemId: Int, username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepo.removeFromCart(cartItemId, username)
            getCartItems(username)
        }
    }
}