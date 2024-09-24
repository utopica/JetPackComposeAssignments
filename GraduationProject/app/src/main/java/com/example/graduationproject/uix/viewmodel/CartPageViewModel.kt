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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(var foodsRepo: FoodsRepository) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Carts>>(emptyList())
    val cartItems: StateFlow<List<Carts>> = _cartItems.asStateFlow()
    val username = "elif_okumus"
    private val _isLoading = MutableLiveData<Boolean>(false)
    private val _error = MutableLiveData<String?>(null)
    private val _itemsCount = MutableStateFlow(0)

    init {
        getCartItems(username)
    }

    fun addToCart(cart: Carts) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentItems = try {
                    foodsRepo.getCartItems(cart.username)
                } catch (e: Exception) {
                    emptyList()
                }

                Log.e("CartPageViewModel", " ${cart.food_name} - ${cart.cart_order_count} Item is gonna be updated")

                val existingItem = currentItems.find { it.food_name == cart.food_name }

                if (existingItem != null) {

                    Log.e("CartPageViewModel", " ${existingItem?.food_name} - ${existingItem?.cart_order_count} Item is existed")

                    val updatedItem = existingItem.copy(cart_order_count = existingItem.cart_order_count + cart.cart_order_count)

                    Log.e("CartPageViewModel", " ${updatedItem.food_name} - ${updatedItem.cart_order_count} Item is changing")

                    try {
                        removeFromCart(existingItem.cart_food_id, existingItem.username, existingItem.food_name, existingItem.cart_order_count)

                        addNewItemToCart(updatedItem)

                        Log.e("CartPageViewModel", " ${updatedItem.food_name} - ${updatedItem.cart_order_count} Item updated to cart successfully")

                    } catch (e: Exception) {
                        Log.e("CartPageViewModel", "Error updating existing item: ${e.message}")
                        _error.value = "Failed to update existing item: ${e.message}"
                    }
                } else {
                    try {
                        addNewItemToCart(cart)
                    } catch (e: Exception) {
                        Log.e("CartPageViewModel", "Error adding new item: ${e.message}")
                        _error.value = "Failed to add new item: ${e.message}"
                    }
                }

                getCartItems(cart.username)
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Unexpected error in addToCart: ${e.message}")
                _error.value = "Unexpected error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun addNewItemToCart(cart: Carts) {
        val requestMap = mapOf(
            "yemek_adi" to cart.food_name,
            "yemek_resim_adi" to cart.food_picName,
            "yemek_fiyat" to cart.food_price.toString(),
            "yemek_siparis_adet" to cart.cart_order_count.toString(),
            "kullanici_adi" to cart.username
        )

        val result = foodsRepo.addToCart(requestMap)
        if (result.success == 1) {
            Log.d("CartPageViewModel", "${cart.food_name} - ${cart.cart_order_count} Item added to cart successfully")
        } else {
            throw Exception("Failed to add item to cart: ${result.message}")
        }
    }

    fun getCartItems(username: String) {
        viewModelScope.launch {
            Log.d("CartPageViewModel", "Fetching cart items for user: $username")
            _isLoading.value = true
            _error.value = null
            try {
                val items = try {
                    foodsRepo.getCartItems(username)
                } catch (e: Exception) {
                    Log.d("CartPageViewModel", "Cart is empty or error occurred: ${e.message}")
                    emptyList()
                }
                _cartItems.value = items
                _itemsCount.value = items.size
                Log.d("CartPageViewModel", "Fetched ${items.size} cart items for user: $username")
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Error fetching cart items: ${e.message}")
                e.printStackTrace()
                _error.value = "Failed to fetch cart items: ${e.message}"
                _cartItems.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFromCart(cartItemId: Int, username: String, foodName: String, orderCount : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("CartPageViewModel", "Removing item $cartItemId - $foodName - $orderCount from cart for user: $username")
            try {
                val result = foodsRepo.removeFromCart(cartItemId, username)
                if (result.success == 1) {
                    Log.e("CartPageViewModel", "Item $cartItemId $foodName $orderCount removed from cart successfully")
                    getCartItems(username)
                } else {
                    Log.e("CartPageViewModel", "Failed to remove item from cart: ${result.message}")
                    _error.value = "Failed to remove item from cart: ${result.message}"
                }
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Error removing item from cart: ${e.message}")
                e.printStackTrace() // Print the full stack trace
                _error.value = "Error removing item from cart: ${e.message}"
            }
        }
    }

    fun getCartItemCount(foodName: String): Int {
        return cartItems.value.find { it.food_name == foodName }?.cart_order_count ?: 0
    }

}