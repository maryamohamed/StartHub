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
import com.training.starthub.databinding.FragmentSignUpCompanyBinding


class SignUpCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSignUpCompanyBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpCompanyBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SignupCompanyFragment_to_loginCompanyFragment)
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.signupNext.setOnClickListener {
            val name : String = binding.name.text.toString().trim()
            val gmail : String = binding.signupGmail.text.toString().trim()
            val pass : String = binding.pass.text.toString().trim()
            val confirmPass : String = binding.confirmPass.text.toString().trim()
            val phone : String = binding.phone.text.toString()

            if (!validateInputs(name, gmail, pass, confirmPass ,phone)) return@setOnClickListener
            registerUser(name, gmail, pass , phone)

            findNavController().navigate(R.id.action_SignupCompanyFragment_to_SecPageCompanyFragment)


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

    private fun registerUser(name: String, email: String, password: String,phone: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                     auth.currentUser?.let { user ->
                        sendEmailVerification(user)
                        saveUserToFirestore(user.uid, name, email, phone, password)
                        showToast("Your account has been successfully created!")
                    }

                }
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

     private fun saveUserToFirestore(userId: String, name: String, email: String, phone: String, password: String){
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "password" to password
        )
        db.collection("Companies").document(userId)
            .set(user).addOnSuccessListener {
                showToast("User data successfully added to Firestore.")
            }
            .addOnFailureListener { e ->
                showToast("Error adding user data to Firestore: ${e.message}")
            }

//        db.collection("Company/$userId/Sec Page").document(userId2)
//            .set(user).addOnSuccessListener {
//                 showToast("User data successfully added to Firestore.")
//            }
//            .addOnFailureListener { e ->
//                showToast("Error adding user data to Firestore: ${e.message}")
//            }
    }


     private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }



}