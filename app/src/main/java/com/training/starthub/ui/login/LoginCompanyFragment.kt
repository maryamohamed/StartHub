package com.training.starthub.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentLoginCompanyBinding
import com.training.starthub.databinding.FragmentLoginCustomerBinding

class LoginCompanyFragment : Fragment() {

    private lateinit var binding : FragmentLoginCompanyBinding

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        binding = FragmentLoginCompanyBinding.inflate(inflater, container , false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpText.setOnClickListener{
            findNavController().navigate(R.id.action_loginCompanyFragment_to_SignupCompanyFragment)
        }

    }

}