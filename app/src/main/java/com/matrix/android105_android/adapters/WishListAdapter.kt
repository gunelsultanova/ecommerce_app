package com.matrix.android105_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.matrix.android105_android.Product
import com.matrix.android105_android.R
import com.matrix.android105_android.databinding.ItemProductBinding

class WishListAdapter(private val productFavList: List<Product>, private val deleteItem:(Product)->Unit): RecyclerView.Adapter<WishListAdapter.WishListViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productFavList.size
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        holder.bind(productFavList[position])
    }


    inner class WishListViewHolder(private val binding: ItemProductBinding) :ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.textViewTitle.text = product.name
            binding.textViewCategory.text = product.category
            binding.textViewPrice.text = product.price
            binding.imageButtonLiked.setImageResource(if (product.favStatus) R.drawable.fav_icon_filled else R.drawable.fav_icon_empty)


            Glide.with(binding.root)
                .load(product.image)
                .into(binding.imageProduct)


            binding.imageButtonLiked.setOnClickListener {
                product.favStatus = !product.favStatus
                deleteItem(product)
                updateFavStatus(product.favStatus)

            }

        }

        private fun updateFavStatus(isFavourite: Boolean) {
            if (isFavourite) {
                binding.imageButtonLiked.setImageResource(R.drawable.fav_icon_filled)
            } else {
                binding.imageButtonLiked.setImageResource(R.drawable.fav_icon_empty)
            }

        }
    }
    }

