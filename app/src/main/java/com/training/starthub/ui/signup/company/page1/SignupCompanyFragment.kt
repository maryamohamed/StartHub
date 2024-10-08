package com.training.starthub.ui.signup.company.page1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSignUpCompanyBinding


class SignUpCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSignUpCompanyBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var companyRepoOne  : CompanyRepoOne
    private lateinit var viewModel  : CompanyViewModOne

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        binding = FragmentSignUpCompanyBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyRepoOne  = CompanyRepoOne(view,requireContext(),db,auth)
        viewModel  = CompanyViewModOne(requireContext(),companyRepoOne)

        binding.loginTextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_SignupCompanyFragment_to_loginCompanyFragment)
        }



        binding.signupNext.setOnClickListener {
            val name : String = binding.name.text.toString().trim()
            val email : String = binding.signupGmail.text.toString().trim()
            val pass : String = binding.pass.text.toString().trim()
            val confirmPass : String = binding.confirmPass.text.toString().trim()
            val phone : String = binding.phone.text.toString()

            viewModel.registerUser(name, email, pass, confirmPass, phone)



        }

    }



}