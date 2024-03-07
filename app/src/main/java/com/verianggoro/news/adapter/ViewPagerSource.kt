package com.verianggoro.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.verianggoro.news.fragment.FragmentGeneral

class ViewPagerSource(fragmentManager: FragmentManager,
                    lifecycle: Lifecycle,
                    private var fragmentActivity: FragmentActivity,
                    private var fragmentData: Array<String>,
                    private var numPage: Int): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return numPage
    }

    override fun createFragment(position: Int): Fragment {
        return FragmentGeneral.newInstance(fragmentData[position], "Source")
    }
}