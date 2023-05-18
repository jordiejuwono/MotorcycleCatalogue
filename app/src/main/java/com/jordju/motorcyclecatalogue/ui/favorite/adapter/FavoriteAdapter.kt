package com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.MotorcycleItemBinding
import java.text.NumberFormat
import java.util.Locale

class FavoriteAdapter(private val onClickListener: OnItemClick) :
    RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    interface OnItemClick {
        fun onClick(motorcycle: MotorcycleEntity)
        fun onCheckOutClick(motorcycle: MotorcycleEntity)
        fun onFavoriteClick(motorcycle: MotorcycleEntity)
    }

    private val differCallback = object : DiffUtil.ItemCallback<MotorcycleEntity>() {
        override fun areItemsTheSame(
            oldItem: MotorcycleEntity,
            newItem: MotorcycleEntity
        ): Boolean {
            return oldItem.motorcycleName == newItem.motorcycleName
        }

        override fun areContentsTheSame(
            oldItem: MotorcycleEntity,
            newItem: MotorcycleEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class ListViewHolder(val binding: MotorcycleItemBinding) :
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
                if (data.isFavorite) {
                    ivFavorite.setColorFilter(ContextCompat.getColor(itemView.context, R.color.red))
                } else {
                    ivFavorite.setColorFilter(ContextCompat.getColor(itemView.context, R.color.light_grey))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.binding.btnBuy.setOnClickListener {
            onClickListener.onCheckOutClick(item)
        }
        holder.binding.ivFavorite.setOnClickListener {
            onClickListener.onFavoriteClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            MotorcycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

}