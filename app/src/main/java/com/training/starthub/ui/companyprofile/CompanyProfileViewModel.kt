package com.training.starthub.ui.companyprofile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class CompanyProfileViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var auth: FirebaseAuth

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    private val _coverImageUrl = MutableLiveData<String>()
    val coverImageUrl: LiveData<String> get() = _coverImageUrl

    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: LiveData<Map<String, String>> get() = _userData

    private lateinit var repository: CompanyProfileRepo

    fun initialize(context: Context, auth: FirebaseAuth) {
        this.context = context
        this.auth = auth
        repository = CompanyProfileRepo(context, auth)
    }

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }

    fun setCoverImageUrl(url: String) {
        _coverImageUrl.value = url
    }

    fun uploadImage(fileUri: Uri) {
        viewModelScope.launch {
            val uri = repository.uploadImage(fileUri)
            uri?.let {
                setImageUrl(it.toString())
                updateUserImage(it.toString(), null)
            }
        }
    }

    fun uploadCoverImage(fileUri: Uri) {
        viewModelScope.launch {
            val uri = repository.uploadImage(fileUri)
            uri?.let {
                setCoverImageUrl(it.toString())
                updateUserImage(null, it.toString())
            }
        }
    }

    fun fetchUserDescriptionAndName(userId: String) {
        viewModelScope.launch {
            val data = repository.fetchUserDescriptionAndName(userId)
            data?.let { _userData.value = it }
        }
    }

    private fun updateUserImage(imageUrl: String?, coverImageUrl: String?) {
        viewModelScope.launch {
            repository.updateUserImage(imageUrl, coverImageUrl, userData.value ?: emptyMap())
        }
    }
}