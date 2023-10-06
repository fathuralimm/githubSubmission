package com.example.mysubmission.Detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fragActivity: FragmentActivity, private val fragment: MutableList<Fragment>
): FragmentStateAdapter(fragActivity) {
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }

}