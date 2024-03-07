package com.verianggoro.news.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.verianggoro.news.R
import com.verianggoro.news.activity.SearchActivity
import com.verianggoro.news.adapter.ViewPagerSource
import com.verianggoro.news.databinding.FragmentSourceBinding

class FragmentSource : Fragment() {

    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentSource.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSource().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initial(){
        val viewPager = binding.pagerMenuSource
        val tabLayout = binding.containerTabSource
        val getLength = resources.getStringArray(R.array.tab_home_list)
        val adapter = ViewPagerSource(childFragmentManager, lifecycle, requireActivity(), getLength, getLength.size)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = resources.getStringArray(R.array.tab_home_list)[position]
            viewPager.currentItem = 0
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }.attach()

        binding.btnSearch.setOnClickListener {
            goSearch()
        }
    }

    private fun goSearch(){
        val intent = Intent(requireContext(), SearchActivity::class.java)
        startActivity(intent)
    }
}