package com.training.starthub.ui.signup.company.page1


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class CompanyViewModOne : ViewModel(){

    private val repository = CompanyRepoOne()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        phone: String,
        onSuccess: (FirebaseUser) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val user = repository.registerUser(name, email, password, phone)
                user?.let {
                    onSuccess(it)
                } ?: onError("User registration failed.")
            } catch (e: Exception) {
                onError(e.message ?: "An unknown error occurred.")
            }
        }
    }

    fun checkEmailVerification(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            repository.checkEmailVerification(user, onSuccess, onFailure)
        }
    }

}