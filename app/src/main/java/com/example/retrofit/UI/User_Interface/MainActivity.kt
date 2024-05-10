package com.example.retrofit.UI.User_Interface

import com.example.retrofit.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.internal.notify

class MainActivity : AppCompatActivity(), FragmentDataListner {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // stored iamge  & shared preference
    val selectedImage = mutableListOf<Document>()

    // SearchFragment -> MainActivity -> StoreFragment
    override fun onDataReceived(item: Document) {
        selectedImage.add(item)
        //StoreFragment.newInstance(storedImage) //  1)
        Log.d("store", "2단계: main에서 store로 아이템 전달: ${selectedImage.size}")
    }


    // stroreFragment -> MainActivity
    fun deletaData(item: Document) {
        TODO("아이템 삭제")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewPager()

    }

    // ViewPager2
    private fun viewPager() {
        val fragmentList = ArrayList<Fragment>().apply {
            add(SearchFragment()) // index 0
            add(StoreFragment()) // index 1
        }
        Log.d("view", fragmentList[0].toString())
        binding.viewPager.adapter = ViewPagerAdapter(this)
        // TabLayout
        val tabTitle = listOf("이미지 검색", "내 보관함")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->  //2
            tab.text = tabTitle[position]
        }.attach()
    }
}




//    // fragmen tag
//    private val TAG_SEARCH = "search_fragment"
//    private val TAG_STORE = "store_fragment"
//
//    private fun initFragment() {
//        // 초기화면
//        setFragment(SearchFragment(),TAG_SEARCH)
//        // bottomNavigationView
//        binding.bottomNavigationView.run {
//            setOnItemSelectedListener { item ->
//                when (item.itemId) {
//                    R.id.bottom_search -> {
//                        setFragment(SearchFragment(), TAG_SEARCH)
//                        true // true를 반환하여 아이템을 선택 상태로 유지
//                    }
//                    R.id.bottom_store -> {
//                        setFragment(StoreFragment(), TAG_STORE)
//                        true
//                    }
//                    else -> false
//                }
//
//            }
//        }
//
//    }
//
//    // fragment set
//    private fun setFragment(fragment: Fragment, tag: String) {
//        val manager:FragmentManager = supportFragmentManager
//        val transaction = manager.beginTransaction()
//
//
//        // pair: fragmentTag & fragment
//        val fragmentsAndTags = listOf(
//            TAG_SEARCH to manager.findFragmentByTag(TAG_SEARCH),
//            TAG_STORE to manager.findFragmentByTag(TAG_STORE)
//        )
//
//        // 생성되어 있지 않을 경우 add
//        if (manager.findFragmentByTag(tag) == null) {
//            transaction.add(R.id.fragment_container_view,fragment,tag)
//
//        }
//        // 이미 add되어 있는 경우, 전부 hide 후, 선택된 프래그먼트만 show
//        fragmentsAndTags.forEach { (framentTag, fragment) ->
//            if (fragment != null) {
//                if (framentTag == tag) {
//                    transaction.show(fragment)
//                } else {
//                    transaction.hide(fragment)
//                }
//            }
//        }
//        transaction.commit()
//
//    }



// 1) companion object 내부에 있는 것이기 때문에, 바로 호출가능한 것. 아직 직접적으로 storefragment의 생성자가 호출된 것은 아니고, newInstance 메소드 안에서 생성자 호출됨.
// 2) 뷰페이저와 텝레이아웃 연결 후, 텝에 제목설정
// add는 프레그먼트를 레이아웃에 추가하는 것 뿐이지, 인스턴스를 만들어 주는 것은 아니다.
// root: 현재 바인딩된 레이아웃의 루트 뷰(최상위 뷰)
// replace 사용 X -> 리소스 낭비 줄이고, 상태 유지 기능




