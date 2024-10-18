package com.training.starthub.ui.all_companies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.all_companies.CompanyRepository
import com.training.starthub.ui.model.Company
import kotlinx.coroutines.launch

class CompanyViewModel : ViewModel() {

    private val repository = CompanyRepository()

    private val _companies = MutableLiveData<List<Company>>()
    val companies: LiveData<List<Company>> get() = _companies

    fun fetchCompaniesFromFirestore() {
        viewModelScope.launch {
            val companiesList = repository.fetchCompanies()
            _companies.postValue(companiesList)
        }
    }
}
