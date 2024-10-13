package com.training.starthub.ui

import CompanyProfileViewModel
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyProfileBinding
import java.io.File
import java.util.UUID
/*private lateinit var binding: FragmentCompanyProfileBinding
   private lateinit var storage: FirebaseStorage
   private val REQUEST_IMAGE_PICK = 1
   private lateinit var selectedImageUri: Uri
   private lateinit var profileImageUrl: String
   private lateinit var viewModel: CompanyProfileViewModel

   override fun onSaveInstanceState(outState: Bundle) {
       super.onSaveInstanceState(outState)
       // Save the image URL
       outState.putString("imageUrl", viewModel.getCompanyProfile())
   }

   override fun onViewStateRestored(savedInstanceState: Bundle?) {
       super.onViewStateRestored(savedInstanceState)
       // Restore the image URL
       profileImageUrl = savedInstanceState?.getString("imageUrl").toString()
       profileImageUrl?.let {
           // Reload the image into the ImageView
           Glide.with(requireContext()).load(it).into(binding.profileImage)
       }
   }

   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       binding = FragmentCompanyProfileBinding.inflate(inflater, container, false)
       viewModel = CompanyProfileViewModel(requireContext(), binding)
       if (viewModel.getCompanyProfile() != null) {
           profileImageUrl = viewModel.getCompanyProfile()!!
           Toast.makeText(requireContext(), "Image URL: $profileImageUrl", Toast.LENGTH_SHORT).show()
           profileImageUrl.let { viewModel.getData(it) }
       }
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

   private fun openGallery() {
       val intent = Intent(Intent.ACTION_GET_CONTENT)
       intent.type = "image/*"
       startActivityForResult(intent, REQUEST_IMAGE_PICK)
   }

   private fun uploadImage(fileUri: Uri) {
       storage = FirebaseStorage.getInstance()
       val storageRef = storage.reference
       val imageRef = storageRef.child("profile_images/${UUID.randomUUID()}.jpg")

       val uploadTask = imageRef.putFile(fileUri)
       uploadTask.addOnCompleteListener {
           if (it.isSuccessful) {
               imageRef.downloadUrl.addOnSuccessListener { uri ->
                   profileImageUrl = uri.toString()
                   viewModel.setCompanyProfile(profileImageUrl)
                   Glide.with(requireContext()).load(profileImageUrl).into(binding.profileImage)
                   Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
               }
           } else {
               Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
           }
       }
   }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)
       if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
           selectedImageUri = data.data!!
           selectedImageUri.let {
               uploadImage(it)
           }
       }
   }

   override fun onDestroy() {
       super.onDestroy()
       profileImageUrl?.let { viewModel.setCompanyProfile(it) }
   }*/

    */

/* private lateinit var binding: FragmentCompanyProfileBinding
 private lateinit var storage: FirebaseStorage
 private val REQUEST_IMAGE_PICK = 1
 private lateinit var viewModel: CompanyProfileViewModel

 override fun onCreateView(
     inflater: LayoutInflater, container: ViewGroup?,
     savedInstanceState: Bundle?
 ): View? {
     binding = FragmentCompanyProfileBinding.inflate(inflater, container, false)
     viewModel = ViewModelProvider(requireActivity()).get(CompanyProfileViewModel::class.java)

     viewModel.imageUrl.observe(viewLifecycleOwner, { url ->
         url?.let {
             Glide.with(requireContext()).load(it).into(binding.profileImage)
         }
     })

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

 private fun openGallery() {
     val intent = Intent(Intent.ACTION_GET_CONTENT)
     intent.type = "image/*"
     startActivityForResult(intent, REQUEST_IMAGE_PICK)
 }

 private fun uploadImage(fileUri: Uri) {
     storage = FirebaseStorage.getInstance()
     val storageRef = storage.reference
     val imageRef = storageRef.child("profile_images/${UUID.randomUUID()}.jpg")

     imageRef.putFile(fileUri).addOnCompleteListener {
         if (it.isSuccessful) {
             imageRef.downloadUrl.addOnSuccessListener { uri ->
                 viewModel.setImageUrl(uri.toString())
                 Glide.with(requireContext()).load(uri).into(binding.profileImage)
                 Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
             }
         } else {
             Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
         }
     }
 }

 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     super.onActivityResult(requestCode, resultCode, data)
     if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
         val selectedImageUri = data.data!!
         uploadImage(selectedImageUri)
     }
 }*/

 */

/*
class CompanyProfileFragment : Fragment() {
    private lateinit var binding: FragmentCompanyProfileBinding
    private val REQUEST_IMAGE_PICK = 1
    private lateinit var viewModel: CompanyProfileViewModel
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CompanyProfileViewModel::class.java)
        viewModel.initialize(requireContext(), auth)

        viewModel.imageUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                Glide.with(requireContext()).load(it).into(binding.profileImage)
            }
        })

        viewModel.userData.observe(viewLifecycleOwner, { data ->
            data?.let {
                binding.inputDec.setText(it["description"])
                binding.userName.setText(it["name"])
                val imageId = it["imageId"]

                if (imageId != null) {
                    viewModel.downloadImage(imageId)
                }
            }
        })
//        viewModel.personalData.observe(viewLifecycleOwner, { data ->
//            data?.let {
//                binding.userName.setText(it["name"])
//            }
//        })

        savedInstanceState?.let {
            binding.userName.setText(it.getString("name", ""))
            binding.inputDec.setText(it.getString("description", ""))
        }

        fetchUserData()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.userName.text.toString())
        outState.putString("description", binding.inputDec.text.toString())

    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        savedInstanceState?.let {
//            binding.userName.setText(it.getString("name", ""))
//            binding.inputDec.setText(it.getString("description", ""))
//
//
////            db.collection("Companies/${auth.currentUser!!.uid}/secPage").document(auth.currentUser!!.uid).set(it)
//        }


//        savedInstanceState?.let {
//            binding.userName.setText(it.getString("name", ""))
//            db.collection("Companies/${auth.currentUser!!.uid}").document(auth.currentUser!!.uid).set(it)
//        }

//    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data!!
            viewModel.uploadImage(selectedImageUri)
        }
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        viewModel.fetchUserDescriptionAndCategory(userId)
//        viewModel.fetchPersonalData(userId)
    }
}

*/

 */




class CompanyProfileFragment : Fragment() {
    private lateinit var binding: FragmentCompanyProfileBinding
    private val REQUEST_IMAGE_PICK = 1
    private lateinit var viewModel: CompanyProfileViewModel
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CompanyProfileViewModel::class.java)
        viewModel.initialize(requireContext(), auth)

        viewModel.imageUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                Glide.with(requireContext()).load(it).into(binding.profileImage)
            }
        })

        viewModel.userData.observe(viewLifecycleOwner, { data ->
            data?.let {
                binding.userName.setText(it["name"])
                binding.inputDec.setText(it["description"])
                val imageId = it["imageId"]
                if (imageId != null) {
                    Glide.with(requireContext()).load(imageId).into(binding.profileImage)
                }
            }
        })

        savedInstanceState?.let {
            binding.userName.setText(it.getString("name", ""))
            binding.inputDec.setText(it.getString("description", ""))

        }

        fetchUserData()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.userName.text.toString())
        outState.putString("description", binding.inputDec.text.toString())
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data!!
            viewModel.uploadImage(selectedImageUri)
        }
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        viewModel.fetchUserDescriptionAndCategory(userId)
    }
}

