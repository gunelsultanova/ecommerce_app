package com.matrix.android105_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.matrix.android105_android.fragments.AccountFragment
import com.matrix.android105_android.fragments.ProductsFragment
import com.matrix.android105_android.fragments.WishListFragment


class PageAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductsFragment()
            1 -> WishListFragment()
            2 -> AccountFragment()
            else -> ProductsFragment()

        }
    }
}