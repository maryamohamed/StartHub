package com.training.starthub.ui.customerlogic.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class CustomerProfileViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var auth: FirebaseAuth

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl


    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: LiveData<Map<String, String>> get() = _userData

    private lateinit var repository: CustomerProfileRepo

    fun initialize(context: Context, auth: FirebaseAuth) {
        this.context = context
        this.auth = auth
        repository = CustomerProfileRepo(context, auth)
    }

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }


    fun uploadImage(fileUri: Uri) {
        viewModelScope.launch {
            val uri = repository.uploadImage(fileUri)
            uri?.let {
                setImageUrl(it.toString())
                updateUserImage(it.toString())
            }
        }
    }


    fun fetchUserName(userId: String) {
        viewModelScope.launch {
            val data = repository.fetchUserName(userId)
            data?.let { _userData.value = it }
        }
    }

    private fun updateUserImage(imageUrl: String?) {
        viewModelScope.launch {
            repository.updateUserImage(imageUrl, userData.value ?: emptyMap())
        }
    }

    fun updateUserName(newName: String) {
        viewModelScope.launch {
            repository.updateUserName(newName, userData.value ?: emptyMap())
        }


    }
}