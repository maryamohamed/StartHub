package com.training.starthub.ui.signup.company.page2

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
    private lateinit var companyRepoTwo  : CompanyRepoTwo
    private lateinit var viewModel  : CompanyViewModelTwo

    val list : List<String> = listOf("Choose Category","Tech", "Clothes", "Food" , "Sports", "Other")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding = FragmentSecPageCompanyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        companyRepoTwo  = CompanyRepoTwo(view,requireContext(),db,auth)
        viewModel  = CompanyViewModelTwo(requireContext(),companyRepoTwo)


        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SecPageCompanyFragment_to_loginCompanyFragment)
        }


        // for spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter


        // for date picker
        binding.DateOfCreation.setOnClickListener {
            showDatePickerDialog()
        }



        binding.signup.setOnClickListener {
            val dateOfCreation = binding.DateOfCreation.text.toString().trim()
            val description = binding.description.text.toString().trim()
            val category = binding.spinner.selectedItem.toString().trim()

            viewModel.registerUser(dateOfCreation, description, category)
            findNavController().navigate(R.id.action_SecPageCompanyFragment_to_CompanyAddProductFragment)
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