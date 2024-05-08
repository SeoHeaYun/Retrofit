package com.example.retrofit.UI.User_Interface

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.Data.entity.Document
import com.example.retrofit.databinding.ItemRecyclerviewBinding

class SearchAdapter(
    private var usingItem: List<Document>,
    private val onclick: (Document) -> Unit // Document 타입의 매개변수, 반환값 Unit(void)
) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {


    class ItemViewHolder(
        var binding: ItemRecyclerviewBinding,
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
                // visible toggle & toast
                val clickBgr = binding.clickBgr
                clickBgr.isVisible = !clickBgr.isVisible
            }
        }

    }

    override fun getItemCount(): Int {
        return usingItem.size
    }

    fun updateData(newData: List<Document>) {
        usingItem = newData
        notifyDataSetChanged()
    }
}





/*by lazy를 사용하였기 때문에, 어댑터가 처음 사용될 때만 초기화 됨.
즉, 비록 검색버튼을 눌러서 usingItem 변수가 초기화되었다 하더라도, 계속 searchAdpater는 계속의 기존의 인스턴스를 사용하기 때문에 직접 데이터 변경사항을 리싸이클러뷰에 알리는 코드가 필요.
(자동으로 되는 것은 없다.)
이때, 어댑터는 리싸이클러뷰 내부 데이터 관리와 관련된 '책임'을 담당해야 하므로, 데이터 업데이트 로직은 어댑터 내부에 배치하는 것이 좋음.
 */

/*
isVisible 코드의 경우, 속성값을 변경하는 것이 아니라, 속성 값 자체를 표현하는 표현식이기 때문에, true,false로 값을 넣어줘야 한다.
clickBgr.isVisible = !clickBgr.isVisible : 가시성 토글(두 가지 옵션 사이의 전환) -> 한항이 true(false)이면 다른 항은 false(true)가 된다.ㅣ

 */