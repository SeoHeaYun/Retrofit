package com.example.retrofit.UI.User_Interface

import android.annotation.SuppressLint
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

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private var storedImage = mutableListOf<Document>()

    val storeAdapter: StoreAdapter by lazy {
        StoreAdapter(
            storedImage,
            onClick = { item -> adapterOnclick(item) }
        )
    }

    private fun adapterOnclick(item: Document) {
        TODO("클릭시 리스트에서 삭제 후, 어뎁터 업데이트(itemchanged)")
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
                val documents: ArrayList<Document>? = bundle.getParcelableArrayList(IntentKey.STORE)
                documents?.let {
                    this.storedImage = it
                    Log.d("store", "3단계: main으로부터 store로 item 받기: ${storedImage.size}")
                    storeAdapter.updateData(storedImage)
                }
            }
        }
    }
}


/*
처음에 arguments 받는 코드를 onCreatView단계 때, 설정해줬었는데, 이 경우, fragment를 클릭하는 순간 mainactivity에서 onCreat되므로 클릭시 새로운 데이터를 받을 수 없음.
따라서, 클릭할 때마다 호출되는 newInstance 함수에서 실시간으로 바로 처리해주도록 코드를 짬.
-> 문제발생: newInstance에서 프래그먼트 초기화 후, 곧바로 어댑터를 업데이트 하려고 하고 있는데, 프래그먼트가 완전히 초기화되지 않은 상태 즉 여기서는, 아직 mainActivity에서 bottom_store가 눌리지 않았기 때문에
, onViewCreated()단계에서 adapter초기화가 되지 않은 상태해서 adapter에 접근하려고 하다보니 오류 발생.
-> 문제해결: ★adapter가 확실히 초기화되고 난 후, adapter update가 이루어지해야한다.★ -> 뷰페이저로 구현하여, adapter 연결이 main에서 미리 되도록 만들었음.
+
만약, main에서 store버튼을 안누르면 store fragment가 add가 안되기 때문에 선택한 것이 리스트에 반영이 안되지 않나.
-> creat만 안되는 거지, 외적인 변수(storedimage)는 활용가능
(add할 경우, 프래그먼트 생명주기가 시작됨. 그 외적인 변수나 함수는 프래그먼트 클래스 인스턴스와 관련됨)
 */



/* MockUpList (-> recyclerview 문제없음.)
  private var mockUpList = mutableListOf(
        Document(
            collection = "1",
            datetime = "1",
            display_sitename = "1",
            doc_url = "1",
            height = 1,
            image_url = "1",
            thumbnail_url = "1",
            width = 1
        )
    )

 */

