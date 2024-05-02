package com.example.retrofit.UI.User_Interface

import com.example.retrofit.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        binding.bottomNavigationView.run {
            setOnItemReselectedListener {item ->
                when(item.itemId) {
                    R.id.bottom_search -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_view, SearchFragment())
                            .commit()
                    }
                    R.id.bottom_store -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_view, StoreFragment())
                            .commit()
                    }

                }

            }
        }

    }

}
