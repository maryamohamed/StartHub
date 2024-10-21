package com.training.starthub.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.data.local.CustomerProduct
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RepoCustomerHome = RepoCustomerHome()) : ViewModel() {
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
