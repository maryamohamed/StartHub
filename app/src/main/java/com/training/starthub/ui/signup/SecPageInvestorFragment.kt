package com.training.starthub.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSecPageInvestorBinding


class SecPageInvestorFragment : Fragment() {

    private lateinit var binding: FragmentSecPageInvestorBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var selectedItem : String
    val list : List<String> = listOf("Choose Category","Tech", "Clothes", "Food" , "Sports", "Other")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecPageInvestorBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SecPageInvestorFragment_to_loginInvestorFragment)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerP1.adapter = adapter



        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.signupButton.setOnClickListener {
            val from = binding.fromInvestment.text.toString().trim()
            val to = binding.toInvestment.text.toString().trim()
            val category = binding.spinnerP1.selectedItem.toString().trim()

            if (isValidDate(from, to, category)){
                val userId = auth.currentUser?.uid.toString()
                saveInvestorData(userId,from, to, category)
            }

        }

    }

    private fun isValidDate( from: String, to: String, category: String): Boolean {
        when {
            from.isEmpty() -> {
                showToast("Date of creation is required")
                return false
            }

            to.isEmpty() -> {
                showToast("Description is required")
                return false
            }

            category.isEmpty()|| category == "Choose Category" -> {
                showToast("Category is required")
            }
            else -> return true
        }
        return true
    }

    private fun saveInvestorData(userId2: String, from: String, to: String, category: String){
        val investorData = hashMapOf(
            "from" to from,
            "to" to to,
            "category" to category
        )
        db.collection("Investors/$userId2/secPage").document(userId2)
            .set(investorData).addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Investor data successfully added to Firestore.")
                } else {
                    showToast("Error adding investor data to Firestore: ${it.exception?.message}")
                }
            }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}