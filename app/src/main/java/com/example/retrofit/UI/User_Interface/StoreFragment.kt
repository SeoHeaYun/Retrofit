package com.example.retrofit.UI.User_Interface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.retrofit.databinding.FragmentStoreBinding

class StoreFragment: Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    // binding 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}