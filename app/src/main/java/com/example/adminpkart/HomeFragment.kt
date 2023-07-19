package com.example.adminpkart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adminpkart.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.AddCategory.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        binding.AddProducts.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_products_Fragment)
        }

        binding.AddSlider.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_add_slider_Fragment)
        }
        binding.AllOrderActivities.setOnClickListener {

           startActivity(Intent(requireContext(), All_order_activity::class.java))
        }







        return binding.root

    }

}