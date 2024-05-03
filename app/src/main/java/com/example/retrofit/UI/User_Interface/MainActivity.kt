package com.example.retrofit.UI.User_Interface

import com.example.retrofit.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.retrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // fragment set
        initFragment()
    }


    private fun initFragment() {
        // 초기화면
        replaceFragment(SearchFragment())
        // bottomNavigationView
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.bottom_search -> {
                        replaceFragment(SearchFragment())
                        true // true를 반환하여 아이템을 선택 상태로 유지
                    }

                    R.id.bottom_store -> {
                       replaceFragment(StoreFragment())
                        true
                    }

                    else -> false
                }

            }
        }

    }
    // replace 함수
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit()
    }
}
