package com.training.starthub.ui.Signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSecPageInvestorBinding


class SecPageInvestorFragment : Fragment() {

    private lateinit var binding: FragmentSecPageInvestorBinding

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

    }


}