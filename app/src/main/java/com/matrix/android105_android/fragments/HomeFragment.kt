package com.matrix.android105_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.matrix.android105_android.adapters.PageAdapter
import com.matrix.android105_android.R
import com.matrix.android105_android.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter(){
        val viewPager = binding.viewPagerHome
        val adapter = PageAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        binding.bottomNavigationBar.setOnNavigationItemSelectedListener{menuItem ->
            viewPager.currentItem = when (menuItem.itemId) {
                R.id.products ->0
                R.id.favorites -> 1
                R.id.account -> 2
                else -> 0
            }
            true
        }

        // Bottom navigation bar will work fine without code below, it just makes sure navigation bar also changes, not just pages.

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationBar.selectedItemId = when (position) {
                    0 -> R.id.products
                    1 -> R.id.favorites
                    2 -> R.id.account
                    else -> R.id.products
                }
            }
        })
}
}
