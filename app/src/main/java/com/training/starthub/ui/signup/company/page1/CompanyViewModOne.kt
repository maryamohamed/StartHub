package com.training.starthub.ui.signup.company.page1

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.launch

class CompanyViewModOne constructor(private val context: Context, private val companyRepoOne: CompanyRepoOne) : ViewModel() {

    val _name = ObservableField("")
    var name: String?
        get() = _name.get()
        set(value) = _name.set(value)

    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)

    val _phone = ObservableField("")
    var phone: String?
        get() = _phone.get()
        set(value) = _phone.set(value)

    val _password = ObservableField("")
    var password: String?
        get() = _password.get()
        set(value) = _password.set(value)

    val _confirmPassword = ObservableField("")
    var confirmPassword: String?
        get() = _confirmPassword.get()
        set(value) = _confirmPassword.set(value)

    fun validateInputs(name: String, email: String, password: String, confirmPassword: String, phone: String): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() -> {
                ToastUtil.showToast(context = context,"All fields are required.")
                false
            }
            password != confirmPassword -> {
                ToastUtil.showToast(context = context,"Passwords do not match.")
                false
            }
            else -> true
        }
    }


    fun registerUser(name: String, email: String, password: String , confirmPassword: String, phone: String ) {
        if (validateInputs(name, email, password, confirmPassword ,phone)){
            viewModelScope.launch {
                companyRepoOne.saveUserToFirestore(name, email, phone ,password, "Company")
            }
        }
        else{
            return
        }

    }




}