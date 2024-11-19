package com.training.starthub.ui.companylogic.addproduct


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.companylogic.addproduct.ProductAddRepo
import kotlinx.coroutines.launch

class ProductAddViewModel : ViewModel() {

    private val repository = ProductAddRepo()
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()


    fun uploadProduct(fileUri: Uri, name: String, description: String, price: String, category: String) {
        viewModelScope.launch {
            try {
                val imageUrl = repository.uploadImage(fileUri)
                repository.saveProductData(name, description, price, category, imageUrl)
                successMessage.postValue("Product added successfully")
            } catch (e: Exception) {
                errorMessage.postValue("Failed to add product: ${e.message}")
            }
        }
    }
}