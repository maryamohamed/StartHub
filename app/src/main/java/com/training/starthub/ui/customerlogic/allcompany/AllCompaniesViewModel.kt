package com.training.starthub.ui.customerlogic.allcompany


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.Company
import kotlinx.coroutines.launch

class AllCompanyViewModel : ViewModel() {

    private val repository = AllCompanyRepository()

    private val _companies = MutableLiveData<List<Company>>()
    val companies: LiveData<List<Company>> get() = _companies

    fun fetchCompaniesFromFirestore() {
        viewModelScope.launch {
            val companiesList = repository.fetchCompanies()
            _companies.postValue(companiesList)
        }
    }
}