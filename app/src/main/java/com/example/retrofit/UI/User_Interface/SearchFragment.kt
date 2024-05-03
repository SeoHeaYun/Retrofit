package com.example.retrofit.UI.User_Interface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.retrofit.databinding.FragmentSearchBinding

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false) // attachToParent: 프래그먼트의 뷰를 자동으로 뷰그룹(container)의 자식으로 추가할 것인가.
        return binding.root
    }

    // binding 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}