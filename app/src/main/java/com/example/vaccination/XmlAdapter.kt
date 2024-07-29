package com.example.vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.vaccination.databinding.ItemMainBinding


class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
class XmlAdapter(val datas:MutableList<myXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {     // 1-1
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]           // 1-2

        // 1-3
        binding.name.text = model.sickCd
        binding.message.text = model.sickNm


//        //
//        Glide.with(binding.root)
//            .load(model.imgurl1)
//            .override(400,300)
//            .into(binding.urlImage)
//
//        Glide.with(binding.root)
//            .load(model.imgurl2)
//            .override(400,300)
//            .into(binding.urlImage2)
    }

    // 외부에서 데이터를 설정하는 메서드
    fun setData(newData: List<myXmlItem>) {
        datas?.clear()
        datas?.addAll(newData)
        notifyDataSetChanged()
    }
}