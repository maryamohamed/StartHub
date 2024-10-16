package com.training.starthub.ui.signup.investor.page2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSecPageInvestorBinding


class SecPageInvestorFragment : Fragment() {

    private lateinit var binding: FragmentSecPageInvestorBinding
    private val viewModel: InvestorViewModTwo by viewModels()

    private val list: List<String> = listOf("Choose Category", "Tech", "Clothes", "Food", "Sports", "Other")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecPageInvestorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecPageInvestorFragment_to_loginInvestorFragment)
        }
        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecPageInvestorFragment_to_InvestorProfileFragment)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerP1.adapter = adapter

        binding.signupButton.setOnClickListener {
            val from = binding.fromInvestment.text.toString().trim()
            val to = binding.toInvestment.text.toString().trim()
            val category = binding.spinnerP1.selectedItem.toString().trim()

            if (isValidData(from, to, category)) {
                viewModel.saveInvestorData(from, to, category, onSuccess = { showToast(it) }, onError = { showToast(it) })
            }
        }
    }

    private fun isValidData(from: String, to: String, category: String): Boolean {
        return when {
            from.isEmpty() -> {
                showToast("Date of creation is required")
                false
            }
            to.isEmpty() -> {
                showToast("Description is required")
                false
            }
            category.isEmpty() || category == "Choose Category" -> {
                showToast("Category is required")
                false
            }
            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}