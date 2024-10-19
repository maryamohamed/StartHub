package com.training.starthub.ui.login.investor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentLoginInvestorBinding

class LoginInvestorFragment : Fragment() {

    private lateinit var binding: FragmentLoginInvestorBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginInvestorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginInvestorFragment_to_SignupInvestorFragment)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = {
                    showToast("Sign-in successful!")
//                    findNavController().navigate(R.id.action_loginInvestorFragment_to_InvestorProfileFragment)
                    findNavController().navigate(R.id.action_loginInvestorFragment_to_HomeFragment)
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