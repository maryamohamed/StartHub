package com.training.starthub.ui.customerlogic.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.launch

class CustomerFavoriteViewModel : ViewModel() {

    val productListLiveData: MutableLiveData<List<CustomerProduct>> = MutableLiveData()
    private val repository: CustomerFavoriteRepo = CustomerFavoriteRepo()
    private val favoritesIdsLiveData: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    // Getter for favorite products LiveData
//    fun getFavoriteProducts(): LiveData<List<CustomerProduct>> = productListLiveData

    fun getProductsByIds(productIds: List<String>) {
        viewModelScope.launch {
            val products = repository.getProductsByIds(productIds)
            productListLiveData.value = products
        }
    }

    fun getFavoritesIds(): LiveData<HashMap<String, String>> {
        viewModelScope.launch {
            val favoritesIds = repository.getFavoritesIds()
            favoritesIdsLiveData.value = favoritesIds
        }
        return favoritesIdsLiveData
    }
}
