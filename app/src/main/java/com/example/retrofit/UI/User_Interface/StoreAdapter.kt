package com.example.retrofit.UI.User_Interface

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.databinding.StoreRecyclerviewBinding


class StoreAdapter(
    private var storedImage: MutableList<Document>,
    private val onClick: (Document) -> Unit //1)
) : RecyclerView.Adapter<StoreAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        val binding: StoreRecyclerviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Document) {
            binding.apply {
                tvSite.text = item.display_sitename
                tvTime.text = item.datetime
                Glide.with(root.context)
                    .load(item.image_url)
                    .into(ivImage)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StoreRecyclerviewBinding.inflate(inflater, parent, false)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemPosition = storedImage[position]
        holder.apply {
            bind(itemPosition)
            itemView.setOnClickListener {
                onClick(itemPosition) // 2)
            }
        }
        //notifyItemInserted(position)
    }

    override fun getItemCount(): Int {
        Log.d("store", "5단계: getItemCount: ${storedImage.size}")
        return storedImage.size
    }


    fun updateData(newData: MutableList<Document>) { //3)
        storedImage = newData
        notifyDataSetChanged()
        Log.d("store", "4단계: store 어댑터 리스트 업데이트 해주기: ${storedImage.size}")
    }
}

/*
1) 아이템 클릭이벤트 처리하는 함수 = fragment의 onClick과 같은 것을 가리킴. (item position이 ★서로 연결되어 동기화되는 이유★)
(SearchAdapter 클래스의 인스턴스를 생성할 때 SearchAdapter(usingItem, onclick = { item -> adapterOnClick(item) }) 코드를 통해 onclick 함수를 전달. 이 함수는 SearchAdapter의 생성자에 전달되어 onClick 프로퍼티에 할당됨. 이 때문에 SearchAdapter 클래스 내에서 아이템 클릭 이벤트를 처리할 때 사용되는 onClick 함수는 생성자 호출 시 전달된 onclick 함수와 동일)
2) onClick이 ItemViewHolder의 onClick이 아니라 StoreAdapter의 onClick을 지시하고 있어서, 값을 뿌리지 못하는 오류 발생했었음

 */