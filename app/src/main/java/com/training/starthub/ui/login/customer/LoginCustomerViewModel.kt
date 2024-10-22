package com.training.starthub.ui.login.customer

import LoginCustomerRepo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginCustomerViewModel : ViewModel() {
    private val repository = LoginCustomerRepo()
    val loginResult = MutableLiveData<Result<Boolean>>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.signInUser(email, password, "Customer")
            loginResult.postValue(result)
        }
    }
}