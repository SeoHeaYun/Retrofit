package com.example.retrofit.UI.User_Interface

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.Data.entity.kakaoDTO
import com.example.retrofit.Data.network.NetWorkClient
import com.example.retrofit.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // retrofit 통해 얻어온 kakao api 데이터
    private var usingItem = mutableListOf<Document>()

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(
            usingItem,
            onclick = { item -> adapterOnClick(item) }
        )
    }

    private fun adapterOnClick(item: Document) {
        TODO("클릭 시 좋아요 표시와 함께, Store fragment에 sharedPrefence형식으로 넣어주세요.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false) // 1)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        // 리싸이클러뷰 어댑터 연결
        binding.recyclerView.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        // 검색버튼 아이콘
        binding.searchView.isSubmitButtonEnabled = true
        // 검색값 입력
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            // 호출되면, retrofit2 통해 데이터 얻기
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String): Boolean {
                val authKy = "5fa6ed2d2fa1d42d803552bc40a7c820"
                // 네트워크 클라이언트(레트로핏 연결) -> 인터페이스(요청)
                NetWorkClient.networkService.getNetworkData(authKy,query).enqueue(object : // 1)
                    Callback<kakaoDTO> // 2)
                {
                    override fun onResponse(call: Call<kakaoDTO>, response: Response<kakaoDTO>) {
                        if(response.isSuccessful) {
                            // 응답 데이터 3)
                            val result = response.body()
                        }

                    }

                    override fun onFailure(call: Call<kakaoDTO>, t: Throwable) {
                        Toast.makeText(requireContext(),"통신 실패 ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })

                // 검색 버튼 누를 때마다 list업데이트 -> 리싸이클러뷰 업데이트
                searchAdapter.notifyDataSetChanged()
                return true
            }

            // 검색 창에 텍스트 변경될 때마다 호출(실시간 검색 기능 혹은 자동 완성 기능 구현 가능)
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

//    // 요청 받은 후, data 변수에 담기
//    private fun communucateNetwork(param: HashMap<String,String>) = lifecycleScope.launch { // http 통신이므로 UI메인 스레드(OnCreat~)가 아닌 코루틴 활성화
//       val authKy = "5fa6ed2d2fa1d42d803552bc40a7c820"
//       val responseData =
//           NetWorkClient.networkService.getNetworkData(authKy, param) // entity 형태로 응답받는다.
//       // 뽑아서 쓸 data
//       usingItem = responseData.documents
//
//   }
//
//    // 요청변수 생성(hashmap 형태로 만든 후, interface에 던질 것)
//    private fun setUpParameter(search: String): HashMap<String,String> {
//
//        return hashMapOf(
//            "query" to search,
//            "sort" to "accuracy",
//            "page" to "6",
//            "size" to "15"
//        )
//    }

    // binding 메모리 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }
}



        /* 각주
        1) attachToParent: 프래그먼트의 뷰를 자동으로 뷰그룹(container)의 자식으로 추가할 것인가.

        2) requireContext를 통해, activity와 연결시키기
        3) queue: FIFO)(선입선출 방식 데이터 구조) / enqueue: 데이터 입력 함수 (비동기 HTTP 요청을 큐에 추가, 통신이 끝나고 DTO 타입의 결괏값을 처리를 위해 콜백을 사용하는 메서드, onResponse()와 onFailure()를 오버라이드할 것)
        4) 콜백 패턴을 사용하여 요청 결과 비동기적 처리(suspend 함수 사용X)
        5)  ※헤더: 메타데이터(형식) / 바디: 응답본문(내용)
         */
