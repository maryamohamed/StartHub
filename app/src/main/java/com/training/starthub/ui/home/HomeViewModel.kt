package com.training.starthub.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import Product
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RepoCustomerHome = RepoCustomerHome()) : ViewModel() {

    val products = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = repository.fetchProducts()
                products.postValue(productList)
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching products: ${e.message}")
            }
        }
    }
}
