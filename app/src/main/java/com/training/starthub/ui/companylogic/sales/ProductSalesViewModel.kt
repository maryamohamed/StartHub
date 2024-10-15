package com.training.starthub.ui.company.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.Product
import kotlinx.coroutines.launch

class ProductSalesViewModel : ViewModel() {

    private val productSalesRepo = ProductSalesRepo()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchUserProducts(userId: String) {
        viewModelScope.launch {
            val productsList = productSalesRepo.fetchProductsForUser(userId)
            _products.value = productsList
        }
    }
}