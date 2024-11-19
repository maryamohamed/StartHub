package com.training.starthub.ui.companylogic.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyProfileBinding

class CompanyProfileFragment : Fragment() {
    private lateinit var binding: FragmentCompanyProfileBinding
    private val REQUEST_IMAGE_PICK = 1
    private val REQUEST_COVER_IMAGE_PICK = 2
    private lateinit var viewModel: CompanyProfileViewModel
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CompanyProfileViewModel::class.java)
        viewModel.initialize(requireContext(), auth)


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

        // Handle the cover image
        viewModel.coverImageUrl.observe(viewLifecycleOwner, { url ->
            if (url != null && url.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(url)
                    .error(R.drawable.profile_background)
                    .into(binding.coverImage)
            } else {
                Glide.with(requireContext())
                    .load(R.drawable.profile_background)
                    .into(binding.coverImage)
            }
        })

        viewModel.userData.observe(viewLifecycleOwner, { data ->
            data?.let {
                binding.userName.setText(it["name"])
                binding.inputDec.setText(it["description"])
                binding.inputDec.visibility = View.VISIBLE
                val profileImageUrl = it["imageUrl"] ?: ""
                val coverImageUrl = it["coverImageUrl"] ?: ""
                if (profileImageUrl.isEmpty()) {
                    Glide.with(requireContext()).load(R.drawable.user).into(binding.profileImage)
                } else {
                    Glide.with(requireContext()).load(profileImageUrl).into(binding.profileImage)
                }
                if (coverImageUrl.isEmpty()) {
                    Glide.with(requireContext()).load(R.drawable.profile_background).into(binding.coverImage)
                } else {
                    Glide.with(requireContext()).load(coverImageUrl).into(binding.coverImage)
                }
            }
        })

        savedInstanceState?.let {
            binding.userName.setText(it.getString("name", ""))
            binding.inputDec.setText(it.getString("description", ""))
            viewModel.setImageUrl(it.getString("imageUrl", ""))
            viewModel.setCoverImageUrl(it.getString("coverImageUrl", ""))
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyProfileFragment_to_CompanyHomeFragment)
        }
        binding.profileImage.setOnClickListener {
            openGallery(REQUEST_IMAGE_PICK)
        }
        binding.coverImage.setOnClickListener {
            openGallery(REQUEST_COVER_IMAGE_PICK)
        }

        // edited
        binding.salesButton.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyProfileFragment_to_CompanySalesFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.userName.text.toString())
        outState.putString("description", binding.inputDec.text.toString())
        viewModel.imageUrl.value?.let { outState.putString("imageUrl", it) }
        viewModel.coverImageUrl.value?.let { outState.putString("coverImageUrl", it) }
    }

    private fun openGallery(requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data!!
            when (requestCode) {
                REQUEST_IMAGE_PICK -> viewModel.uploadImage(selectedImageUri)
                REQUEST_COVER_IMAGE_PICK -> viewModel.uploadCoverImage(selectedImageUri)
            }
        }
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        viewModel.fetchUserDescriptionAndName(userId)
    }
}