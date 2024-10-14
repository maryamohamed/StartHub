package com.training.starthub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.company.home.HomeProductRepository
import com.training.starthub.ui.model.Product
import kotlinx.coroutines.launch

class HomeProductViewModel : ViewModel() {

    private val repository: HomeProductRepository = HomeProductRepository()

    // LiveData لحفظ البيانات اللي هترجع من Firebase
    val products = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    // دالة لتحميل المنتجات باستخدام Coroutines
    fun loadUserProducts() {
        // تشغيل العملية في background باستخدام viewModelScope
        viewModelScope.launch {
            try {
                val productList = repository.getUserProducts()
                products.postValue(productList)  // تحديث الـ LiveData بالنتائج
            } catch (e: Exception) {
                errorMessage.postValue("Failed to load products: ${e.message}")
            }
        }
    }
}
