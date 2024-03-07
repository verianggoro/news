package com.verianggoro.news.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.verianggoro.news.R
import com.verianggoro.news.activity.HomeScreen
import com.verianggoro.news.activity.SearchActivity
import com.verianggoro.news.adapter.ViewPagerHome
import com.verianggoro.news.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initial(){
        val viewPager = binding.pagerMenu
        val tabLayout = binding.containerTab
        val fragmentData = resources.getStringArray(R.array.tab_home_list)
        val adapter = ViewPagerHome(childFragmentManager, lifecycle, requireActivity(), fragmentData, fragmentData.size)
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