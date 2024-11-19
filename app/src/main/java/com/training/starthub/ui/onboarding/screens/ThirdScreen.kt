package com.training.starthub.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentThirdScreenBinding
import com.training.starthub.utils.declareViewPager


class ThirdScreen : Fragment() {

    private lateinit var binding : FragmentThirdScreenBinding

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // handle click events
        onClick()
    }

    private fun onClick() {
        binding.next3.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_SelectFragment)
        }
        binding.skipButton3.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_SelectFragment)
        }

        onBoardingFinished()
    }

    // handle on boarding when finish
    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}