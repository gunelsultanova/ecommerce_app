package com.matrix.android105_android.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.matrix.android105_android.Product
import com.matrix.android105_android.adapters.WishListAdapter
import com.matrix.android105_android.databinding.FragmentWishListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint


class WishListFragment : Fragment() {
    lateinit var binding: FragmentWishListBinding
//    private val firestore = FirebaseFirestore.getInstance()
    lateinit var FavDb: FirebaseFirestore
    lateinit var productFavList: ArrayList<Product>
    lateinit var adapter: WishListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FavDb = Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productFavList = arrayListOf()
        fetchFavorites()

    }


    private fun fetchFavorites() {
        val products = FavDb.collection("favorites").addSnapshotListener { documents, error ->
            if (error != null) {

                return@addSnapshotListener
            }
            productFavList.clear()

            if (documents != null) {
                    for (document in documents) {

                        val name = document.get("name").toString()
                        val category = document.get("category").toString()
                        val price = document.get("price").toString()
                        val image = document.get("image").toString()
                        val favstatus = document.get("favstatus")

                        val productDocument =
                            Product(name, category, price, image, favStatus = true)
                        productFavList.add(productDocument)
//                    Log.i("MyFirebase", "Product: ${item.get("name")} Price: ${item.get("price")}")
                    }
                }
                adapter = WishListAdapter(productFavList) { product ->
                        deleteItem(product)
                 }
                initRecyclerView()
            }
//            .addOnFailureListener {
//            }
    }

    fun deleteItem(item: Product) {
        FavDb.collection("favorite").document("name")
            .delete()
            .addOnSuccessListener {
                Log.d("successCode", "DocumentSnapshot successfully deleted!")

            }
            .addOnFailureListener { e ->
                Log.w("failCode", "Error deleting document", e)

            }
    }

    fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.notifyDataSetChanged()
    }
}


