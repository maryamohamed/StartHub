package com.training.starthub.ui.companylogic.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.ui.model.Product
import kotlinx.coroutines.launch

class HomeProductViewModel : ViewModel() {

    private val repository: HomeProductRepo = HomeProductRepo()

    val products = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    fun loadUserProducts( userId: String = FirebaseAuth.getInstance().currentUser?.uid.toString()) {
        viewModelScope.launch {
            try {
                val productList = repository.getUserProducts(userId)
                products.postValue(productList)
            } catch (e: Exception) {
                errorMessage.postValue("Failed to load products: ${e.message}")
            }
        }
    }
}