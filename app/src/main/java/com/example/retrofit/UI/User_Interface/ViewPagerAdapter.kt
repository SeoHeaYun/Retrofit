package com.example.retrofit.UI.User_Interface

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
  fragemtnActivity: FragmentActivity // 1.2
): FragmentStateAdapter(fragemtnActivity) {
    // 프래그먼트 인스턴스와 태그를 매핑할 맵
    private val fragments = mutableMapOf<String, Fragment>()

    override fun createFragment(position: Int): Fragment { //3
        val fragment: Fragment
        val tag: String

        when(position){
            0 -> {
                fragment = SearchFragment()
                tag = "SearchFragment"
            }
            1 -> {
                fragment = StoreFragment()
                tag = "StoreFragment"
            }
            else -> throw IllegalArgumentException("Invalid position")
        }

        // 프래그먼트와 태그를 매핑한 맵에 저장
        fragments[tag] = fragment

        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }

    fun getFragmentByTag(tag:String):Fragment? {
        return fragments[tag]
    }






}






// 1) FragmentStateAdapter와 FragmentActivity: 프래그먼트 생명 주기를 관리하는 Activity. FragmentManager 제공.
// 2) Fragment Instance를 main에서 미리 만들어 놓은 후, 리스트에 미리 담아두고 creat 때 return 하는 방법도 있음.(data class를 만들어 tag도 한번에 설정가능)
// 3) fragmemt의 instance를 position값으로 찾아올 때 사용가능. (ViewPager2는 프래그먼트의 수명 주기를 어댑터를 통해 관리하며, 프래그먼트의 인스턴스를 직접 생성하거나 추가하는 것은 ViewPager2의 관리 방식과 충돌할 수 있기때문에, 직접 가져오는 것이 나음.)