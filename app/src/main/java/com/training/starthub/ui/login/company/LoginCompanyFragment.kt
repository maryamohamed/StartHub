package com.training.starthub.ui.login.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.R
import com.training.starthub.databinding.FragmentLoginCompanyBinding

class LoginCompanyFragment : Fragment() {

    private lateinit var binding : FragmentLoginCompanyBinding
    private val viewModel : LoginCompanyViewModel by viewModels()

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        binding = FragmentLoginCompanyBinding.inflate(inflater, container , false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginCompanyFragment_to_SignupCompanyFragment)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }

        }



        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = {
                    showToast("Sign-in successful!")
                    // Navigate to the next fragment
                    findNavController().navigate(R.id.action_loginCompanyFragment_to_CompanyHomeFragment)
                },
                onFailure = { e ->
                    showToast("Failed to sign in: ${e.message}")
                }
            )
        })
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}