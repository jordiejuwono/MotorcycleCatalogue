package com.jordju.motorcyclecatalogue.ui.home.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.motorcyclecatalogue.databinding.TransactionItemBinding
import java.text.NumberFormat
import java.util.Locale

class TransactionAdapter(private val onClickListener: OnItemClick) :
    RecyclerView.Adapter<TransactionAdapter.ListViewHolder>() {

    interface OnItemClick {
        fun onClick(motorcycle: MotorcycleOrderDetails)
    }

    private val differCallback = object : DiffUtil.ItemCallback<MotorcycleOrderDetails>() {
        override fun areItemsTheSame(
            oldItem: MotorcycleOrderDetails,
            newItem: MotorcycleOrderDetails
        ): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(
            oldItem: MotorcycleOrderDetails,
            newItem: MotorcycleOrderDetails
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class ListViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MotorcycleOrderDetails) {

            val localeId = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
            val price = formatRupiah.format(data.price)

            with(binding) {
                Glide.with(itemView.context)
                    .load(data.motorcycleImage)
                    .into(ivMotorcycle)
                tvName.text = data.motorcycleName
                tvPrice.text = price
                tvStatus.text = data.status
                tvAddress.text = data.addressTo
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
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

}