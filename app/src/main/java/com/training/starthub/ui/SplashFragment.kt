package com.training.starthub.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.starthub.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        Handler().postDelayed(
            { findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment) },
            3000
        )
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}