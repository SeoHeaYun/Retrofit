package com.example.retrofit.UI.User_Interface

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private var storedImage = mutableListOf<Document>()

    private val storeAdapter: StoreAdapter by lazy {
        StoreAdapter(
            storedImage,
            onClick = { item -> adapterOnclick(item) }
        )
    }

    // 클릭시 삭제
    private fun adapterOnclick(item: Document) {
        val position = storedImage.indexOf(item) // item에 해당하는 index 반환
        if (position >= 0) {
            storedImage.removeAt(position)
            storeAdapter.notifyItemRemoved(position)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storedImage = (requireActivity() as MainActivity).selectedImage
        initView()
    }


    override fun onPause() {
        super.onPause()
        storeAdapter.updateData(storedImage)
    }

    fun initView() {
        binding.storeRecyclerview.apply {
            adapter = storeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    // binding 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



/*
ISSUE
: 처음에 newInstance함수를 만들어 activity와 list 교환을 해서 만듦. 이 경우, 클릭할 때마다 store fragment의 instance가 여러번 생성된다. 이 경우, 리소스 낭비뿐만 아니라 +  class안에 지역변수로 설정된 storedImage의 아이템 개수의 초기값은 0인데, instance 관점에서는
호출될 때마다 각각의 인스턴스는 서로 다른 '복사본 storeImage'를 가지게 되기 때문에(로그캣에서 올바르게 찍힌 이유), adapter에 전달되는 것은 여전히 아이템 개수가 0인 storedimage이다. ㅁㄴㅇ
SOLVE
:


mainActivity에서 viewpager를 통해 fragment를 바로 load하는 게 아니라, 클릭해야 load[생명주기 시작]된다.
바로 load해주고 싶으면 offscreenPageLimit 값 설정해주기.(이 경우 onStarted 단계까지만 load된다.)

만약, main에서 store버튼을 안누르면 store fragment가 add가 안되기 때문에 선택한 것이 리스트에 반영이 안되지 않나.
-> creat만 안되는 거지, 외적인 변수(storedimage)는 활용가능
(add할 경우, 프래그먼트 생명주기가 시작됨. 그 외적인 변수나 함수는 프래그먼트 클래스 인스턴스와 관련됨)
 */

