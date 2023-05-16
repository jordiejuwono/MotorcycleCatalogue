package com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.databinding.MotorcycleItemBinding
import java.text.NumberFormat
import java.util.Locale

class MotorcycleAdapter(private val onClickListener: OnItemClick) :
    RecyclerView.Adapter<MotorcycleAdapter.ListViewHolder>() {

    interface OnItemClick {
        fun onClick(motorcycle: MotorcycleEntity)
    }

    private val differCallback = object : DiffUtil.ItemCallback<MotorcycleEntity>() {
        override fun areItemsTheSame(
            oldItem: MotorcycleEntity,
            newItem: MotorcycleEntity
        ): Boolean {
            return oldItem.motorcycleId == newItem.motorcycleId
        }

        override fun areContentsTheSame(
            oldItem: MotorcycleEntity,
            newItem: MotorcycleEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class ListViewHolder(private val binding: MotorcycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MotorcycleEntity) {

            val localeId = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
            val price = formatRupiah.format(data.price)

            with(binding) {
                Glide.with(itemView.context)
                    .load(data.motorcycleImage)
                    .into(ivMotorcycle)
                tvName.text = data.motorcycleName
                tvCategory.text = data.category
                tvPrice.text = price
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(differ.currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            MotorcycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

}