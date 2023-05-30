package com.varalakshmiakella.republicServices.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.varalakshmiakella.republicServices.databinding.DriverItemPreviewBinding
import com.varalakshmiakella.republicServices.model.Driver

class DriverAdapter:RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {
    inner class DriverViewHolder(val binding: DriverItemPreviewBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object: DiffUtil.ItemCallback<Driver>(){
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val binding = DriverItemPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DriverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driver=differ.currentList[position]
        with(holder){
            holder.itemView.apply{
                binding.tvDriverName.text =driver.name
                setOnClickListener{
                    onItemClickListener?.let{it(driver)}
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener:((Driver)->Unit)?=null
    fun setOnItemClickListener(listener:(Driver)->Unit){
        onItemClickListener = listener
    }


}