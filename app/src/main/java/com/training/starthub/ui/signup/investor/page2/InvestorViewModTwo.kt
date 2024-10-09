package com.training.starthub.ui.signup.investor.page2

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.launch

class InvestorViewModTwo : ViewModel() {

    private val repository = InvestorRepoTwo()

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