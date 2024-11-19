package com.training.starthub.ui.customerlogic.rate

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.launch

class RateProductViewModel : ViewModel() {
    private val rateProductRepo = RateProductRepo()
    private val _listOfIds = MutableLiveData<HashMap<String, String>>()
    val listOfIds: MutableLiveData<HashMap<String, String>> get() = _listOfIds
    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: MutableLiveData<Map<String, String>> get() = _userData



    fun setProductRating(productId: String, rating: Double, feedback: String, imageUrl: String, name: String) {
        viewModelScope.launch {
            rateProductRepo.setProductRating(productId, rating, feedback, imageUrl, name)
        }
    }

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            val data = rateProductRepo.fetchUserData(userId)
            data?.let { _userData.value = it }
        }
    }



    fun getIds() {
        viewModelScope.launch {
            try {
                _listOfIds.value = rateProductRepo.getIds()
                Log.d("ItemDetailsViewModel", "Loaded products: ${_listOfIds.value}")
            } catch (e: Exception) {
                Log.e("ItemDetailsViewModel", "Error fetching product IDs: ${e.message}")
            }
        }
    }

}