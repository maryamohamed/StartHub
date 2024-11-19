package com.training.starthub.ui.login.investor


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginInvestorRepo()
    val loginResult = MutableLiveData<Result<Boolean>>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.signInUser(email, password, "Investor")
            loginResult.postValue(result)
        }
    }
}