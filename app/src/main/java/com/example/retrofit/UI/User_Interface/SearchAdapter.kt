package com.example.retrofit.UI.User_Interface

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.ItemRecyclerviewBinding

class SearchAdapter():
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

        class ItemViewHolder(
            private var binding: ItemRecyclerviewBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}