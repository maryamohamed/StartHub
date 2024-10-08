package com.training.starthub.ui.signup.company.page2

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.launch

class CompanyViewModelTwo constructor(private val context: Context, private val companyRepoTwo: CompanyRepoTwo) : ViewModel() {

    val _dateOfCreation = ObservableField("")
    var dateOfCreation: String?
        get() = _dateOfCreation.get()
        set(value) = _dateOfCreation.set(value)

    val _description = ObservableField("")
    var description: String?
        get() = _description.get()
        set(value) = _description.set(value)

    val _category = ObservableField("")
    var category: String?
        get() = _category.get()
        set(value) = _category.set(value)




    private fun isValidDate( dateOfCreation: String, description: String, category: String): Boolean {
        when {
            dateOfCreation.isEmpty() -> {
                ToastUtil.showToast(context = context,"Date of creation is required")
                return false
            }

            description.isEmpty() -> {
                ToastUtil.showToast(context = context,"Description is required")
                return false
            }

            category.isEmpty()|| category == "Choose Category" -> {
                ToastUtil.showToast(context = context,"Category is required")
            }
            else -> return true
        }
        return true
    }


    fun registerUser(dateOfCreation: String, description: String, category: String) {

        if (isValidDate(dateOfCreation, description, category)) {
            viewModelScope.launch {
                companyRepoTwo.saveUserToFirestore(dateOfCreation, description, category)
            }
        } else {
            return
        }
    }
}