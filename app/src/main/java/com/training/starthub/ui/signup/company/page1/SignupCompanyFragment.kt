package com.training.starthub.ui.signup.company.page1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSignUpCompanyBinding


class SignUpCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSignUpCompanyBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var companyRepoOne  : CompanyRepoOne
    private lateinit var viewModel  : CompanyViewModOne

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        binding = FragmentSignUpCompanyBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyRepoOne  = CompanyRepoOne()
        viewModel  = CompanyViewModOne()

        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SignupCompanyFragment_to_loginCompanyFragment)
        }

        binding.signupNext.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.signupGmail.text.toString().trim()
            val pass = binding.pass.text.toString().trim()
            val confirmPass = binding.confirmPass.text.toString().trim()
            val phone = binding.phone.text.toString().trim()

            if (validateInputs(name, email, pass, confirmPass, phone)) {
                viewModel.registerUser(name, email, pass, phone, ::onRegistrationSuccess, ::onRegistrationError)
            }
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String, phone: String): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() -> {
                showToast("All fields are required.")
                false
            }
            password != confirmPassword -> {
                showToast("Passwords do not match.")
                false
            }
            else -> true
        }
    }

    private fun onRegistrationSuccess(user: FirebaseUser) {
        showToast("Registration successful! Please verify your email.")
        viewModel.checkEmailVerification(user, {
            showToast("Email successfully verified!")
            findNavController().navigate(R.id.action_SignupCompanyFragment_to_SecPageCompanyFragment)
        }, {
            showToast("Please verify your email.")
        })
    }

    private fun onRegistrationError(errorMessage: String) {
        showToast("Failed to create account: $errorMessage")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}