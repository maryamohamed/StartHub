package com.training.starthub.ui.customerlogic.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.launch

class ItemDetailsViewModel : ViewModel() {
    private val _productDetails = MutableLiveData<CustomerProduct?>()
    val productDetails: MutableLiveData<CustomerProduct?> = _productDetails
    private val repo = ItemDetailsRepo()
    private val _listOfIds = MutableLiveData<HashMap<String, String>>()
    val listOfIds: MutableLiveData<HashMap<String, String>> get() = _listOfIds
    val errorMessage = MutableLiveData<String>()

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                _productDetails.value = repo.fetchProductDetails(productId)
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching product details: ${e.message}")
            }
        }
    }

    fun getIds() {
        viewModelScope.launch {
            try {
                _listOfIds.value = repo.getIds()
                Log.d("ItemDetailsViewModel", "Loaded products: ${_listOfIds.value}")
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching product IDs: ${e.message}")
            }
        }
    }

    fun clearProductDetails() {
        _productDetails.value = null
    }
}
