package com.training.starthub.ui.signup.investor.page1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSignupInvestorBinding

class SignupInvestorFragment : Fragment() {

    private lateinit var binding: FragmentSignupInvestorBinding
    private val viewModel: InvestorViewModOne by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupInvestorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SignupInvestorFragment_to_loginInvestorFragment)
        }

        binding.next.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
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

        viewModel.checkEmailVerification(user, {
            showToast("Email successfully verified!")
            findNavController().navigate(R.id.action_SignupInvestorFragment_to_SecPageInvestorFragment)
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