package com.training.starthub.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSecondScreenBinding
import com.training.starthub.utils.declareViewPager


class SecondScreen : Fragment() {

    private lateinit var binding : FragmentSecondScreenBinding

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // handle click events
        onClicks()
    }

    private fun onClicks() {
        binding.next2.setOnClickListener {
            declareViewPager(2)
        }
        binding.skipButton2.setOnClickListener {
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