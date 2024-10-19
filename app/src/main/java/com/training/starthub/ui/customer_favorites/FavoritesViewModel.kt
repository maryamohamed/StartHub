package com.training.starthub.ui.customer_favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.training.starthub.ui.customer_favorites.FavoritesRepository
import com.training.starthub.ui.model.Product

class FavoritesViewModel : ViewModel() {

    private val productListLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    private val repository: FavoritesRepository = FavoritesRepository()

    // Getter for favorite products LiveData
    fun getFavoriteProducts(): LiveData<List<Product>> = productListLiveData

    // Fetch products from repository
    fun fetchFavoriteProducts() {
        repository.fetchFavoriteProducts(productListLiveData)
    }
}
