package com.training.starthub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.training.starthub.R
import com.training.starthub.databinding.FragmentFilterDialogBinding

class FilterDialog : DialogFragment() {

    private var _binding : FragmentFilterDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentFilterDialogBinding.inflate(inflater, container, false)

        setupSpinner()
        setupButtonListeners()

        return binding.root
    }

    private fun setupSpinner() {
        val categoryOptions = resources.getStringArray(R.array.category_options)


        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCategory.adapter = adapter
    }

    private fun setupButtonListeners() {
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonOk.setOnClickListener {
            val selectedCategory = binding.spinnerCategory.selectedItem.toString()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
