package com.example.retrofit.UI.User_Interface

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.UI.Intent.IntentKey
import com.example.retrofit.databinding.FragmentStoreBinding
import kotlin.math.log

class StoreFragment: Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private var storedImage = mutableListOf<Document>()

    private val storeAdapter: StoreAdapter by lazy {
        StoreAdapter(
            storedImage,
            onClick = { item -> adapterOnclick(item) }
        )
    }

    private fun adapterOnclick(item: Document) {
        TODO("클릭시 리스트에서 삭제 후, 리스트 업데이트")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    // binding 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initView() {
        binding.storeRecyclerview.apply {
            adapter = storeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    companion object {
        fun newInstance(item: MutableList<Document>): StoreFragment {
            val bundle = Bundle().apply {
                putParcelableArrayList(IntentKey.STORE, ArrayList(item))
            }
            return StoreFragment().apply {
                    val documents: ArrayList<Document>? =
                        bundle.getParcelableArrayList(IntentKey.STORE)
                    documents?.let {
                        this.storedImage = it

                    }
                }
            }
        }
    }


/*
처음에 arguments 받는 코드를 onCreatView단계 때, 설정해줬었는데, 이 fragment는 이미 mainactivity에서 생성된 것이므로,
newInstance 함수에서 바로 처리해주었음. 또한, adapter 연결도 이미 진행되었기에, adpater에 list업데이트도 알려야함.
 */

