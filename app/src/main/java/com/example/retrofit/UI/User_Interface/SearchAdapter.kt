package com.example.retrofit.UI.User_Interface

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.R
import com.example.retrofit.databinding.ItemRecyclerviewBinding

class SearchAdapter(
    private val usingItem: MutableList<Document>,
    private val onclick: (Document) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {


    class ItemViewHolder(
        private var binding: ItemRecyclerviewBinding,
        val onclick: (Document) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Document) {
            binding.apply {
                tvSite.text = item.display_sitename
                tvTime.text = item.datetime
                Glide.with(binding.root.context)// context가 들어가야 하는데, 만약 this를 쓸경우, viewBinding을 참조하게 될 것임.
                    .load(item.image_url)
                    .into(ivImage)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onclick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemPosition = usingItem[position]
        holder.apply {
            bind(itemPosition)
            holder.itemView.setOnClickListener {
                onclick(itemPosition)
            }
        }

    }

    override fun getItemCount(): Int {
        return usingItem.size
    }

}