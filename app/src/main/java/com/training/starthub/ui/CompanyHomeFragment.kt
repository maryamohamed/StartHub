package com.training.starthub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage

import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyHomeBinding


class CompanyHomeFragment : Fragment() {

    private lateinit var storage : FirebaseStorage
    private lateinit var binding : FragmentCompanyHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyHomeBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyAddProductFragment)
        }

        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyProfileFragment)
        }

    }

}