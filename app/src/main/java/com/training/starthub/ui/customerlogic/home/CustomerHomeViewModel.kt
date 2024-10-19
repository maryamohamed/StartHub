package com.training.starthub.ui.customerlogic.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.launch

class CustomerHomeViewModel(private val repository: CustomerHomeRepo = CustomerHomeRepo()) : ViewModel() {
    val products = MutableLiveData<List<CustomerProduct>>()
    val errorMessage = MutableLiveData<String>()
    var listOfIds = hashMapOf<Int, String>()
    var setPosition = MutableLiveData<String>()

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = repository.fetchProducts()
                products.postValue(productList)
                Log.d("CustomerHomeViewModel", "Loaded products: $listOfIds")
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching products: ${e.message}")
            }
        }
    }


}

