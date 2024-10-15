package com.training.starthub.ui.customerlogic.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCustomerProfileBinding


class CustomerProfileFragment : Fragment() {
    private lateinit var binding: FragmentCustomerProfileBinding
    private val REQUEST_IMAGE_PICK = 1
    private lateinit var viewModel: CustomerProfileViewModel
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCustomerProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CustomerProfileViewModel::class.java)
        viewModel.initialize(requireContext(), auth)
        binding.bottomNavigationBar.selectedItemId = R.id.navigation_profile

        fetchUserData()

        // Handle the profile image
        viewModel.imageUrl.observe(viewLifecycleOwner, { url ->
            if (url != null && url.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(url)
                    .error(R.drawable.user)
                    .into(binding.profileImage)
            } else {
                Glide.with(requireContext())
                    .load(R.drawable.user)
                    .into(binding.profileImage)
            }
        })

        viewModel.userData.observe(viewLifecycleOwner, { data ->
            data?.let {
                binding.nameAppeared.setText(it["name"])
                binding.nameAppeared.visibility = View.VISIBLE
                val profileImageUrl = it["imageUrl"] ?: ""

                if (profileImageUrl.isEmpty()) {
                    Glide.with(requireContext()).load(R.drawable.user).into(binding.profileImage)
                } else {
                    Glide.with(requireContext()).load(profileImageUrl).into(binding.profileImage)
                }
            }
        })

        savedInstanceState?.let {
            binding.nameAppeared.setText(it.getString("name", ""))
            viewModel.setImageUrl(it.getString("imageUrl", ""))
        }
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController().navigate(R.id.action_CustomerProfileFragment_to_CompanyHomeFragment)
                    true
                }

                else -> false
            }
        }
        binding.profileImage.setOnClickListener {
            openGallery()
        }
        binding.edit.setOnClickListener {
            binding.nameAppeared.visibility = View.INVISIBLE
            binding.editName.visibility = View.VISIBLE
            binding.save.visibility = View.VISIBLE
        }
        binding.save.setOnClickListener {
            val newName = binding.editName.text.toString()
            binding.nameAppeared.text = newName
            binding.editName.visibility = View.INVISIBLE
            binding.nameAppeared.visibility = View.VISIBLE
            binding.save.visibility = View.INVISIBLE
            viewModel.updateUserName(newName)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.nameAppeared.text.toString())
        viewModel.imageUrl.value?.let { outState.putString("imageUrl", it) }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data!!

            viewModel.uploadImage(selectedImageUri)
        }
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        viewModel.fetchUserName(userId)
    }
}
