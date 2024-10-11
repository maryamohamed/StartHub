package com.training.starthub.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyProfileBinding
import java.io.File
import java.util.UUID


class CompanyProfileFragment : Fragment() {

    private lateinit var binding : FragmentCompanyProfileBinding
    private lateinit var storage : FirebaseStorage
    private val REQUEST_IMAGE_PICK = 1
    private lateinit var selectedImageUri: Uri
    private var profileImageUrl: String? = null
    private lateinit var viewModel: CompanyProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel = ViewModelProvider(this).get(CompanyProfileViewModel::class.java)
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        // Save the image URL
//        outState.putString("imageUrl", profileImageUrl)
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        // Restore the image URL
//        profileImageUrl = savedInstanceState?.getString("imageUrl")
//        profileImageUrl?.let {
//            // Reload the image into the ImageView
//            Glide.with(requireContext()).load(it).into(binding.profileImage)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileImageUrl?.let { getData(it) }
        binding = FragmentCompanyProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyProfileFragment_to_CompanyHomeFragment)
        }
        binding.profileImage.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun uploadImage(fileUri: Uri){
        storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val downloadUrl: UUID? = UUID.randomUUID()
        val imageId = downloadUrl.toString()
        profileImageUrl = imageId
        val imageRef = storageRef.child("profile_images/${downloadUrl}.jpg")
        val uploadTask = imageRef.putFile(fileUri)


        uploadTask.addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(),"Image uploaded successfully", Toast.LENGTH_SHORT).show()
                getData(imageId)
            }else{
                Toast.makeText(requireContext(),"Image upload failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data!!
//            selectedImageUri?.let {
//                binding.profileImage.setImageURI(it)
//            }
            selectedImageUri?.let {
                // Upload the image to Firebase Storage
                uploadImage(it)
            }


        }
    }

    private fun getData(imageId : String){

        val storageReference = FirebaseStorage.getInstance().getReference("profile_images/${imageId}.jpg")
        var file = File.createTempFile("tempImage", ".jpg")
        storageReference.getFile(file).addOnSuccessListener {
            Glide.with(requireContext())
                .load(file)
                .into(binding.profileImage)
//            binding.profileImage.
            Toast.makeText(requireContext(), "Image downloaded successfully", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to download image", Toast.LENGTH_SHORT).show()
        }

    }
}