package com.training.starthub.ui.Signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSignupInvestorBinding


class SignupInvestorFragment : Fragment() {

    private lateinit var binding : FragmentSignupInvestorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupInvestorBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.investorSignupNext.setOnClickListener{
            findNavController().navigate(R.id.action_SignupInvestorFragment_to_SecPageInvestorFragment)
        }
    }
}