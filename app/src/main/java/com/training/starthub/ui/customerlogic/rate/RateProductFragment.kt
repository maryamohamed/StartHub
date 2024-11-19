package com.training.starthub.ui.customerlogic.rate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth

import com.training.starthub.databinding.FragmentRateProductBinding

class RateProductFragment(val sheredPosition: String): Fragment() {
    private val viewModel : RateProductViewModel by viewModels()
    private lateinit var binding: FragmentRateProductBinding
    private val auth = FirebaseAuth.getInstance()
    private var finalPosition = 0
    private var productId = ""


    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        binding = FragmentRateProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            val rating = binding.rating.rating.toDouble()
            val feedback = binding.feedback.text.toString()


            if (isNumericDouble(sheredPosition)) {
                finalPosition = sheredPosition.toInt()
            }
            else{
                productId = sheredPosition
            }

//            finalPosition = sheredPosition.toInt()

            Log.d("ItemDetailsFragment", "Final Received position: $finalPosition")

            if (productId.isNotEmpty()) {
                viewModel.fetchUserData(auth.currentUser?.uid.toString())
                viewModel.userData.observe(viewLifecycleOwner) { userData ->
                    val imageUrl = userData["imageUrl"] ?: ""
                    val name = userData["name"] ?: ""
                    viewModel.setProductRating(productId, rating, feedback, imageUrl, name)
                }
            }
            else{
                viewModel.getIds()
                viewModel.listOfIds.observe(viewLifecycleOwner) { mapOfIds ->
                    val idList = mapOfIds.entries.toList()
                    if (finalPosition in idList.indices) {
                        val selectedEntry = idList[finalPosition]
                        val productId = selectedEntry.value
                        Log.d("ItemDetailsFragment", "Product ID: $productId")
                        viewModel.fetchUserData(auth.currentUser?.uid.toString())
                        viewModel.userData.observe(viewLifecycleOwner) { userData ->
                            val imageUrl = userData["imageUrl"] ?: ""
                            val name = userData["name"] ?: ""
                            viewModel.setProductRating(productId, rating, feedback, imageUrl, name)
                        }
                    } else {
                        Log.e("ItemDetailsFragment", "Invalid position: $finalPosition")
                        Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.feedback.text?.clear()
            binding.submitButton.isEnabled = false
            binding.submitButton.isClickable = false
            binding.submitButton.visibility = View.INVISIBLE
            binding.feedback.visibility = View.INVISIBLE
            binding.rating.visibility = View.INVISIBLE
            binding.submitted.visibility = View.VISIBLE

            Toast.makeText(requireContext(), "Rating submitted successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun isNumericDouble(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }

}
