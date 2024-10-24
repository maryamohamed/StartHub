package com.training.starthub.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSelectBinding

class SelectFragment : Fragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize view binding
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set OnClickListener for each button
        binding.customerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SelectFragment_to_loginCustomerFragment)
        }
        binding.companyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SelectFragment_to_loginCompanyFragment)
        }
        binding.investorBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SelectFragment_to_loginInvestorFragment)
        }
        binding.visitorBtn.setOnClickListener {
            val intent = Intent(requireContext(), CustomerHomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
