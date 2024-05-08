package com.example.retrofit.UI.User_Interface

import com.example.retrofit.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.databinding.ActivityMainBinding
import okhttp3.internal.notify

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // fragmen tag
    private val TAG_SEARCH = "search_fragment"
    private val TAG_STORE = "store_fragment"

    // stored iamge  & shared preference
    val storedImage = mutableListOf<Document>()

    // SearchFragment -> MainActivity -> StoreFragment
    fun receiveData(item: Document) {
        storedImage.add(item)
        StoreFragment.newInstance(storedImage)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

    // fragment set
    private fun setFragment(fragment: Fragment, tag: String) {
        val manager:FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()


        // pair: tag & fragment
        val fragmentsAndTags = listOf(
            TAG_SEARCH to manager.findFragmentByTag(TAG_SEARCH),
            TAG_STORE to manager.findFragmentByTag(TAG_STORE)
        )

        // 생성되어 있지 않을 경우 add
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

// root: 현재 바인딩된 레이아웃의 루트 뷰(최상위 뷰)
// replace 사용 X -> 리소스 낭비 줄이고, 상태 유지 기능




