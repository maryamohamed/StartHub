package com.training.starthub.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.R
import com.training.starthub.databinding.FragmentLoginInvestorBinding

class LoginInvestorFragment : Fragment() {

    private lateinit var binding : FragmentLoginInvestorBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        binding = FragmentLoginInvestorBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpText.setOnClickListener{
            findNavController().navigate(R.id.action_loginInvestorFragment_to_SignupInvestorFragment)
        }
        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener{
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            if(validateInputs(email,password)){
                signInUser(email,password)
            }
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

    private fun signInUser(email: String,password: String){
        auth.signInWithEmailAndPassword(email,password)
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
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

}