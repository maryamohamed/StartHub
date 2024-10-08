package com.training.starthub.ui.signup

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
import com.training.starthub.databinding.FragmentSignupInvestorBinding


class SignupInvestorFragment : Fragment() {

    private lateinit var binding: FragmentSignupInvestorBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupInvestorBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SignupInvestorFragment_to_loginInvestorFragment)
        }
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.next.setOnClickListener {

            var name = binding.name.text.toString().trim()
            var email = binding.email.text.toString().trim()
            var pass = binding.pass.text.toString().trim()
            var confirmPass = binding.confirmPass.text.toString().trim()
            var phone = binding.phone.text.toString().trim()

            if (!validateInputs(name, email, pass, confirmPass, phone))return@setOnClickListener
                registerUser(name, email, pass, phone)

            findNavController().navigate(R.id.action_SignupInvestorFragment_to_SecPageInvestorFragment)


        }


    }


    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String , phone: String): Boolean {
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


    private fun registerUser(name: String, email: String, pass: String, phone: String){
        auth.createUserWithEmailAndPassword(email, pass)
        .addOnCompleteListener { task ->
            if (task.isSuccessful){
                auth.currentUser?.let { user ->
                    sendEmailVerification(user)
                    saveUserToFirestore(user.uid, name, email, phone, pass)
                    showToast("Your account has been successfully created!")
                }
            }
        }

    }


    private fun saveUserToFirestore(userId2: String, name: String, email: String, phone: String, password: String){
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "password" to password
        )
        db.collection("Investors").document(userId2)
            .set(user).addOnSuccessListener {
                showToast("User data successfully added to Firestore.")
            }
            .addOnFailureListener { e ->
                showToast("Error adding user data to Firestore: ${e.message}")
            }

    }

    private fun sendEmailVerification(user : FirebaseUser){
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Verification email sent to ${user.email}")
                } else {
                    showToast("Error sending verification email: ${task.exception?.message}")
                }
            }
    }


    private fun showToast(message: String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }
}