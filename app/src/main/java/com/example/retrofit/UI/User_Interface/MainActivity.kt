package com.example.retrofit.UI.User_Interface

import com.example.retrofit.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // stored iamge  + shared preference -> store fragment recycler view
    val stroredImage = mutableListOf<Document>()

    private val TAG_SEARCH = "search_fragment"
    private val TAG_STORE = "store_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // root: 현재 바인딩된 레이아웃의 루트 뷰(최상위 뷰)

        initFragment()
    }




    private fun initFragment() {
        // 초기화면
        setFragment(SearchFragment(),TAG_SEARCH)
        // bottomNavigationView
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.bottom_search -> {
                        setFragment(SearchFragment(), TAG_SEARCH)
                        true // true를 반환하여 아이템을 선택 상태로 유지
                    }

                    R.id.bottom_store -> {
                        setFragment(StoreFragment(), TAG_STORE)
                        true
                    }
                    else -> false
                }

            }
        }

    }
    // fragment set 함수(replace 사용 X -> 리소스 낭비 줄이고, 상태 유지)
    private fun setFragment(fragment: Fragment, tag: String) {
        val manager:FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()


        // 각 프래그먼트 TAG로 구분
        val fragmentsAndTags = listOf(
            TAG_SEARCH to manager.findFragmentByTag(TAG_SEARCH),
            TAG_STORE to manager.findFragmentByTag(TAG_STORE)
        )


        // 선택시, 생성되어 있지 않을 경우 add
        if (manager.findFragmentByTag(tag) == null) {
            transaction.add(R.id.fragment_container_view,fragment,tag)

        }
        // 이미 add되어 있는 경우, 전부 hide 후, 선택된 프래그먼트만 show
        fragmentsAndTags.forEach { (framentTag, fragment) ->
            if (fragment != null) {
                if (framentTag == tag) {
                    transaction.show(fragment)
                } else {
                    transaction.hide(fragment)
                }
            }
        }
        transaction.commit()

    }

}






