package com.training.starthub.ui.investorlogic.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.model.Company
import kotlinx.coroutines.launch

class CompanyDetailsItemViewModel : ViewModel() {
    private val repo = CompanyDetailsItemRepo()
    private val _companyDetails = MutableLiveData<Company>()
    val companyDetails: LiveData<Company> = _companyDetails
    private val _listOfIds = MutableLiveData<HashMap<String, String>>()
    val listOfIds: MutableLiveData<HashMap<String, String>> get() = _listOfIds
    val errorMessage = MutableLiveData<String>()
    private val _companyInfo = MutableLiveData<Company>()
    val companyInfo: LiveData<Company> = _companyInfo

    fun fetchCompanyDetails(companyId: String) {
        viewModelScope.launch {
            try {
                _companyDetails.value = repo.fetchCompanyDetails(companyId)
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching company details: ${e.message}")
            }
        }
    }

    fun getCompaniesIds() {
        viewModelScope.launch {
            try {
                _listOfIds.value = repo.getCompaniesIds()
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching company IDs: ${e.message}")
            }
        }
    }

    fun fetchCompanyInfo(productId: String) {
        viewModelScope.launch {
            try {
                _companyInfo.value = repo.fetchCompanyInfo(productId)
            } catch (e: Exception) {
                errorMessage.postValue("Error fetching company info: ${e.message}")
                }
        }

    }

}