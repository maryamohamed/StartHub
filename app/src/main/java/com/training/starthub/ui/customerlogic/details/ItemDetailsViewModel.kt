package com.training.starthub.ui.customerlogic.details

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.customerlogic.home.CustomerHomeRepo
import com.training.starthub.ui.customerlogic.home.CustomerHomeViewModel
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.launch

class ItemDetailsViewModel : ViewModel(){
    private val _productDetails = MutableLiveData<CustomerProduct>()
    val productDetails: MutableLiveData<CustomerProduct> = _productDetails
    private val repo = ItemDetailsRepo()

    private val _listOfIds = MutableLiveData<HashMap<Int,String>>()
    val listOfIds: MutableLiveData<HashMap<Int,String>> = _listOfIds




    fun fetchProductDetails(productId: String) {

        viewModelScope.launch {
            _productDetails.value = repo.fetchProductDetails(productId)
        }

    }

    fun getIds(){
        viewModelScope.launch {
            _listOfIds.value = repo.getIds()
            Log.d("ItemDetailsViewModel", "Loaded products: ${_listOfIds.value}")

        }

    }

}