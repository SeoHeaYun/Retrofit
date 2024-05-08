package com.example.retrofit.UI.User_Interface

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.Data.entity.kakaoDTO
import com.example.retrofit.Data.network.NetWorkClient
import com.example.retrofit.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // retrofit 통해 얻어온 데이터 중 body
    private var usingItem = listOf<Document>()

    private val searchAdapter: SearchAdapter by lazy {
            SearchAdapter(
                usingItem,
                onclick = { item -> adapterOnClick(item) }  // -1)
            )
        }

    // SearchFagment -> MainActivity  0)
    private fun adapterOnClick(item: Document) {
        val mActivity = activity as? MainActivity
        mActivity?.receiveData(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false) // 2)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        // 검색버튼 아이콘
        binding.searchView.isSubmitButtonEnabled = true
        // 리싸이클러뷰 어댑터 연결
        binding.searchRecyclerview.apply {
            adapter = searchAdapter // 6)
            layoutManager = GridLayoutManager(requireContext(), 2) // 4)
        }
        // 검색값 입력
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            // 호출되면, retrofit2 통해 데이터 얻기
            override fun onQueryTextSubmit(query: String): Boolean {
                val authKy = "KakaoAK 5fa6ed2d2fa1d42d803552bc40a7c820"
                NetWorkClient.networkService.getNetworkData(authKy,query).enqueue(object : // 5)
                    Callback<kakaoDTO> // 6)
                {
                    override fun onResponse(call: Call<kakaoDTO>, response: Response<kakaoDTO>) {
                        if(response.isSuccessful) {
                            // 응답 데이터 7)
                            val result = response.body()
                            usingItem = result!!.documents
                            searchAdapter.updateData(usingItem) // 8)
                        }

                    }
                    override fun onFailure(call: Call<kakaoDTO>, t: Throwable) {
                        Toast.makeText(requireContext(),"통신 실패 ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })
                return true
            }

            // 검색 창에 텍스트 변경될 때마다 호출(실시간 검색 기능 혹은 자동 완성 기능 구현 가능)
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }



    // binding 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }
}



        /* 각주
       -1)  item의 출처: SearchAdpater에서 onBind될 때, cnCLick변수에 item의 position이 담긴다. 그 후, 아이템이 큶릭될 때마다, onClick[(Document) -> Unit 타입의 함수로, 아이템 클릭 이벤트가 발생했을 때 호출될 함수 / ★adapter class의 onClick과 동일한 것 가리킴★]에서 새로운 데이터의 Document 타입의 item(변수명 설정은 자유롭다)이 매개변수가 된다.
        0) 통신이 하나밖에 없어서 interface & context가 아닌 upcasting으로 간단히 구현하였음
        1) 어뎁터 온클릭: itemView UI 관련 / 액티비티 온클릭: 어뎁터로부터 itemView의 포지션 data 받아와서 처리
        2) attachToParent: 프래그먼트의 뷰를 자동으로 뷰그룹(container)의 자식으로 추가할 것인가.
        3) 29번째 줄에서 생성자 호출하도록 하여 어댑터와 연결
        4) requireContext를 통해, activity와 연결시키기
        5) 네트워크 클라이언트(레트로핏 연결) -> 인터페이스(요청) / queue: FIFO)(선입선출 방식 데이터 구조) / enqueue: 데이터 입력 함수 (비동기 HTTP 요청을 큐에 추가, 통신이 끝나고 DTO 타입의 결괏값을 처리를 위해 콜백을 사용하는 메서드, onResponse()와 onFailure()를 오버라이드할 것)
        6) 콜백 패턴을 사용하여 요청 결과 비동기적 처리(코루틴 suspend 함수 사용X)
        7)  ※헤더: 메타데이터(형식) / 바디: 응답본문(내용)
        8) 처음에는 리싸이클러뷰와 어뎁터 연결하는 것 자체를 매번 재호출하도록 했는데, 위에서 한번만 호출한 후, 데이터 리스트만 바꾸도록 변경
         */
