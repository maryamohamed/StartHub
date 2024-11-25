package com.training.starthub.utils

import com.training.starthub.R

object SignupUtil{

    fun checkSignUpValid(fullName : String, email: String, phone: String, password: String, confirmPassword: String): Int {
        if(fullName.trim().isEmpty()){return R.string.FullName_Is_Required}
        if(email.trim().isEmpty()){return R.string.Email_Is_Required}
        if(phone.trim().isEmpty()){return R.string.Phone_Number_Is_Required}
        if(password.trim().isEmpty()){return R.string.Password_Is_Required}
        if(confirmPassword.trim().isEmpty()){return R.string.Confirm_Password_Is_Required}
        if(password.trim() != confirmPassword.trim()){return R.string.Password_Do_not_match}

        return R.string.success
    }
}