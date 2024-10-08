package com.training.starthub.ui.signup

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSecPageCompanyBinding


class SecPageCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSecPageCompanyBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var selectedItem : String

    val list : List<String> = listOf("Choose Category","Tech", "Clothes", "Food" , "Sports", "Other")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecPageCompanyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToast("Sec Page Company Fragment")

        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SecPageCompanyFragment_to_loginCompanyFragment)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                selectedItem = parent?.getItemAtPosition(position).toString().trim()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                showToast("Nothing Category selected")
//            }
//
//        }


        binding.DateOfCreation.setOnClickListener {
            showDatePickerDialog()
        }
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        binding.signup.setOnClickListener {
            val dateOfCreation = binding.DateOfCreation.text.toString().trim()
            val description = binding.description.text.toString().trim()
            val category = binding.spinner.selectedItem.toString().trim()

            if (isValidDate(dateOfCreation, description, category)){
                val userId = auth.currentUser?.uid.toString()
                saveCompanyData(userId,dateOfCreation, description, category)
            }
        }

    }

    private fun isValidDate( dateOfCreation: String, description: String, category: String): Boolean {
        when {
            dateOfCreation.isEmpty() -> {
                showToast("Date of creation is required")
                return false
            }

            description.isEmpty() -> {
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

    private fun saveCompanyData(userId: String, dateOfCreation: String, description: String, category: String) {
        val companyData = hashMapOf(
            "dateOfCreation" to dateOfCreation,
            "description" to description,
            "category" to category
        )

        db.collection("Companies/$userId/secPage").document(userId)
            .set(companyData).addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Company data successfully added to Firestore.")
                } else {
                    showToast("Error adding company data to Firestore: ${it.exception?.message}")
                }
            }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val date = "$dayOfMonth/${month + 1}/$year"
                binding.DateOfCreation.setText(date)
                selectedItem = date
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }



    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}