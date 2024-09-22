
package com.matrix.android105_android.fragments

import com.matrix.android105_android.Product
import com.matrix.android105_android.adapters.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.matrix.android105_android.databinding.FragmentProductsBinding

@AndroidEntryPoint

class ProductsFragment : Fragment() {
    lateinit var binding: FragmentProductsBinding
    lateinit var db: FirebaseFirestore
    private lateinit var adapter: ProductAdapter
    lateinit var productList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productList = arrayListOf()
        getData()
    }

    fun getData() {

        val products = db.collection("products").get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {

                        val name = document.get("name").toString()
                        val category = document.get("category").toString()
                        val price = document.get("price").toString()
                        val image = document.get("image").toString()

                        val productDocument = Product(name, category, price, image)
                        productList.add(productDocument)
//                    Log.i("MyFirebase", "Product: ${item.get("name")} Price: ${item.get("price")}")
                    }
                }
                adapter = ProductAdapter(productList) { product ->
                    addFavList(product)
                }
                initRecyclerView()
            }
            .addOnFailureListener {
            }

    }


    fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.notifyDataSetChanged()
    }

    private fun addFavList(product: Product) {

        if (product.favStatus) {

            val favProductMap = hashMapOf(
                "name" to product.name,
                "image" to product.image,
                "category" to product.category,
                "price" to product.price,
                "favstatus" to product.favStatus
            )

            val dbFav = FirebaseFirestore.getInstance()


            dbFav.collection("favorites")
                .add(favProductMap)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error adding document", e)
                }
        }
    }
}