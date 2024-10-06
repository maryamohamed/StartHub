package com.training.starthub.ui.Signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.databinding.FragmentSignupClientBinding
import kotlinx.coroutines.tasks.await

class SignupClientFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentSignupClientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupClientBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.signUpButton.setOnClickListener {
            val name = binding.signupName.text.toString().trim()
            val email = binding.signupGmail.text.toString().trim()
            val password = binding.signupPass.text.toString().trim()
            val confirmPassword = binding.signupConfirmPass.text.toString().trim()
            val phone = binding.signupPhone.text.toString().trim()

            if (!validateInputs(name, email, password, confirmPassword, phone)) return@setOnClickListener

            registerUser(name, email, password, phone)
        }

        return binding.root
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

    private fun registerUser(name: String, email: String, password: String, phone: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let { user ->
                        sendEmailVerification(user)
                        saveUserToFirestore(user.uid, name, email, phone)
                        showToast("Your account has been successfully created!")
                    }
                } else {
                    showToast("Failed to create account: ${task.exception?.message}")
                }
            }
    }

    private fun sendEmailVerification(user: FirebaseUser){
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Verification email sent to ${user.email}")
                } else {
                    showToast("Error sending verification email: ${task.exception?.message}")
                }
            }
    }

    private fun saveUserToFirestore(userId: String, name: String, email: String, phone: String) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone
        )

        db.collection("customers").document(userId)
            .set(user)
            .addOnSuccessListener {
                showToast("User data successfully added to Firestore.")
            }
            .addOnFailureListener { e ->
                showToast("Error adding user data to Firestore: ${e.message}")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
