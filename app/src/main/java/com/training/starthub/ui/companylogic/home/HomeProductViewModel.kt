package com.training.starthub.ui.company.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.Product
import kotlinx.coroutines.launch

class HomeProductViewModel : ViewModel() {

    private val repository: HomeProductRepo = HomeProductRepo()

    val products = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    fun loadUserProducts() {
        viewModelScope.launch {
            try {
                val productList = repository.getUserProducts()
                products.postValue(productList)
            } catch (e: Exception) {
                errorMessage.postValue("Failed to load products: ${e.message}")
            }
        }
    }
}