package com.matrix.android105_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.matrix.android105_android.Product
import com.matrix.android105_android.R
import com.matrix.android105_android.databinding.ItemProductBinding


class ProductAdapter(private val productList: List<Product>, private val addFavList: (Product) -> Unit): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }



    inner class ProductViewHolder(private val binding: ItemProductBinding): ViewHolder(binding.root){

        fun bind(product: Product){
            binding.textViewTitle.text = product.name
            binding.textViewCategory.text = product.category
            binding.textViewPrice.text = "${product.price}$"

            Glide.with(binding.root)
                .load(product.image)
                .into(binding.imageProduct)


            binding.imageButtonLiked.setOnClickListener {
                product.favStatus =! product.favStatus
                addFavList(product)
                updateFavStatus(product.favStatus)


            }

        }

        private fun updateFavStatus(isFavourite: Boolean){
            if (isFavourite){
                binding.imageButtonLiked.setImageResource(R.drawable.fav_icon_filled)
            }else{
                binding.imageButtonLiked.setImageResource(R.drawable.fav_icon_empty)
            }

        }
    }

}
