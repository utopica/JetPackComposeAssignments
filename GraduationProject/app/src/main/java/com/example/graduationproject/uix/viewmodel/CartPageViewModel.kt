package com.example.graduationproject.uix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject.data.entities.CRUDResponse
import com.example.graduationproject.data.entities.Carts
import com.example.graduationproject.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(var foodsRepo : FoodsRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<Carts>>(emptyList())
    val cartItems: LiveData<List<Carts>> = _cartItems
    val username = "elif_okumus"
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    private val _itemsCount = MutableStateFlow(0)
    val itemsCount: StateFlow<Int> = _itemsCount

    init {
        getCartItems(username)
    }

    val _cartOperationResult = MutableLiveData<CRUDResponse>()

    fun addToCart(cart: Carts) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("CartPageViewModel", "Adding to cart: $cart")

            val requestMap = mapOf(
                "yemek_adi" to cart.food_name,
                "yemek_resim_adi" to cart.food_picName,
                "yemek_fiyat" to cart.food_price.toString(),
                "yemek_siparis_adet" to cart.cart_order_count.toString(),
                "kullanici_adi" to cart.username
            )

            Log.e("CartPageViewModel", "MAP: $requestMap")


            val result = foodsRepo.addToCart(requestMap)

            _cartOperationResult.value = result

            Log.e("CartPageViewModel", "API Response: success=${result.success}, message=${result.message}")

            if(result.success == 1){
                Log.e("HomePage", "Item added to cart")

            }else{
                Log.e("HomePage", "Item did not add to cart")

            }
        }
    }

    fun getCartItems(username: String) {
        viewModelScope.launch {
            Log.e("CartPageViewModel", "Fetching cart items for user: $username")
            _isLoading.value = true
            _error.value = null
            try {
                val items = foodsRepo.getCartItems(username)
                _cartItems.value = items
                _itemsCount.value = items.size
                Log.e("CartPageViewModel", "Fetched ${items.size} cart items for user: $username")
                if (items.isEmpty()) {
                    Log.e("CartPageViewModel", "Cart is empty for user: $username")
                }
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Error fetching cart items: ${e.message}")
                _error.value = "Failed to fetch cart items: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFromCart(cartItemId: Int, username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("CartPageViewModel", "Removing item $cartItemId from cart for user: $username")
            try {
                foodsRepo.removeFromCart(cartItemId, username)
                Log.e("CartPageViewModel", "Item $cartItemId removed from cart successfully")
                getCartItems(username)
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Error removing item from cart: ${e.message}")
            }
        }
    }
}