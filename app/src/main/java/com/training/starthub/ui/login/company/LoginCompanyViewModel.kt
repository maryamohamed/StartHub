package com.training.starthub.ui.login.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.login.investor.LoginInvestorRepo
import kotlinx.coroutines.launch

class LoginCompanyViewModel : ViewModel() {
    private val repository = LoginInvestorRepo()
    val loginResult = MutableLiveData<Result<Boolean>>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.signInUser(email, password)
            loginResult.postValue(result)
        }
    }
}