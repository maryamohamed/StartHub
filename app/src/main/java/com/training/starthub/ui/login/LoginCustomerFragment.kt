package com.training.starthub.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.R
import com.training.starthub.databinding.FragmentLoginCustomerBinding

class LoginCustomerFragment : Fragment() {

    private lateinit var binding: FragmentLoginCustomerBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                signInUser(email, password)
            }
        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginCustomerFragment_to_SignupCustomerFragment)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showToast("Email is required.")
                false
            }
            password.isEmpty() -> {
                showToast("Password is required.")
                false
            }
            else -> true
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Sign-in successful!")
                    // Navigate to the next fragment
                } else {
                    showToast("Failed to sign in: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
