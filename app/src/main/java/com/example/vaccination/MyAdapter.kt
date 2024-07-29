package com.example.vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vaccination.databinding.ItemRecyclerviewBinding


//new -> new kotlin 으로 생성,

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

//전달받은 데이터가 null일 수도 있다는 것을 나타냄 = > ?
class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){ // 2-1
    override fun getItemCount(): Int {
        return datas?.size ?:0;     // 2-2  ?:일때 0을 리턴
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        datas?.let {
            val binding = (holder as MyViewHolder).binding
            binding.itemData.text = it[position]
        }
    }
}
