package com.training.starthub.ui.customerlogic.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.Review
import kotlinx.coroutines.launch

class CustomerReviewViewModel : ViewModel() {
    private val customerReviewRepo = CustomerReviewRepo()

    private val _products = MutableLiveData<List<Review>>()
    val products: LiveData<List<Review>> get() = _products

    private val _listOfIds = MutableLiveData<HashMap<String, String>>()
    val listOfIds: LiveData<HashMap<String, String>> get() = _listOfIds

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchReviews(productId: String) {
        viewModelScope.launch {
            try {
                val productList = customerReviewRepo.fetchReviews(productId)
                _products.value = productList
            } catch (e: Exception) {
                _error.value = "Error fetching reviews: ${e.message}"
                Log.e("CustomerReviewViewModel", "Error fetching reviews: ${e.message}")
            }
        }
    }

    fun getIds() {
        viewModelScope.launch {
            try {
                _listOfIds.value = customerReviewRepo.getIds()
                Log.d("CustomerReviewViewModel", "Loaded product IDs")
            } catch (e: Exception) {
                _error.value = "Error fetching product IDs: ${e.message}"
                Log.e("CustomerReviewViewModel", "Error fetching product IDs: ${e.message}")
            }
        }
    }
}
