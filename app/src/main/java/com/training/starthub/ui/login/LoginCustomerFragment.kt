package com.training.starthub.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.training.starthub.databinding.FragmentLoginCustomerBinding
import com.training.starthub.ui.CustomerHomeActivity

class LoginCustomerFragment : Fragment() {

    private var _binding: FragmentLoginCustomerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the click listener here
        binding.CustomerloginButton.setOnClickListener {
            startHomeActivity()
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(requireActivity(), CustomerHomeActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
