package com.training.starthub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.signup.investor.page2.SignUpInvestor2Repo

import kotlinx.coroutines.launch

class InvestorViewModel : ViewModel() {

    private val repository = SignUpInvestor2Repo()

    fun saveInvestorData(from: String, to: String, category: String,
        onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val userId = repository.getCurrentUserId() ?: return onError("User not logged in.")

        viewModelScope.launch {
            val result = repository.saveInvestorData(userId, from, to, category)
            result.onSuccess {
                onSuccess(it)
            }.onFailure {
                onError(it.message ?: "An unknown error occurred.")
            }
        }
    }
}
