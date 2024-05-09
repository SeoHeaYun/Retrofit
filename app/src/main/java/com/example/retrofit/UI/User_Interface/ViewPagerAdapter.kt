package com.example.retrofit.UI.User_Interface

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
  private val fragmentList: ArrayList<Fragment>, // 1
  fragemtnActivity: FragmentActivity // 2
): FragmentStateAdapter(fragemtnActivity) {

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    override fun getItemCount(): Int {
        return fragmentList.count()
    }




}






// 1) FragmentActivity: 프래그먼트 생명 주기를 지원하는 Activity. FragmentManager 제공.
// 2) Fragment Instance를 main에서 미리 만들어 놓은 후, 리스트에 미리 담아두고 creat 때 return