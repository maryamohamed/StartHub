package com.training.starthub.ui.signup.customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSignupClientBinding
import com.training.starthub.ui.CustomerHomeActivity

class SignupClientFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentSignupClientBinding
    private lateinit var viewModel: CustomerViewModel
    private lateinit var customerRepo: CustomerRepo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupClientBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        customerRepo = CustomerRepo()
        viewModel = CustomerViewModel()

        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SignupCustomerFragment_to_loginCustomerFragment)
        }
        binding.signUpButton.setOnClickListener {
            val name = binding.signupName.text.toString().trim()
            val email = binding.signupGmail.text.toString().trim()
            val password = binding.signupPass.text.toString().trim()
            val confirmPassword = binding.signupConfirmPass.text.toString().trim()
            val phone = binding.signupPhone.text.toString().trim()

            if (validateInputs(name, email, password, confirmPassword, phone)) {
                viewModel.registerUser(name, email, password , phone, ::onRegistrationSuccess, ::onRegistrationError)
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
            val intent = Intent(requireContext(), CustomerHomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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