package com.training.starthub.ui.signup.company.page2

import android.content.Context
import androidx.databinding.ObservableField
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.launch

class CompanyViewModelTwo constructor(private val context: Context, private val companyRepoTwo: CompanyRepoTwo) : ViewModel() {
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

//    private val _name = MutableLiveData<Map<String, String>>()
//    val name: LiveData<Map<String, String>> get() = _name

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


    fun registerUser(dateOfCreation: String, description: String, category: String ) {

        if (isValidDate(dateOfCreation, description, category)) {
            viewModelScope.launch {
//                var name = saveNameToFirestore().toString()
//                val nameRef = db.collection("Companies/${auth.currentUser!!.uid}").document("auth.currentUser!!.uid")
//                val name = nameRef.get().await().getString("name")
//                companyRepoTwo.saveUserToFirestore(dateOfCreation, description, category, name)
                companyRepoTwo.saveUserToFirestore(dateOfCreation, description, category)
            }
        } else {
            return
        }
    }

//    fun saveNameToFirestore() {
//        viewModelScope.launch {
//            name.let {
//                companyRepoTwo.saveNameToFirestore()
//            }
//        }
//    }

}