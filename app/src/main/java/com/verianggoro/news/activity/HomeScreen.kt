package com.verianggoro.news.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.verianggoro.news.R
import com.verianggoro.news.adapter.ViewPagerHome
import com.verianggoro.news.databinding.ActivityHomeScreenBinding
import com.verianggoro.news.fragment.FragmentHome
import com.verianggoro.news.fragment.FragmentSource

class HomeScreen : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private var eventHome: Long = 0

    companion object {
        const val EVENT_ID = "event_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        if (intent.extras != null) {
            eventHome = intent.getLongExtra(EVENT_ID, 0)
            defaultFragment()
            changeNavigation()
        }else{
            return
        }

    }

    private fun changeNavigation(){
        binding.bottomNavigationNews.setOnItemSelectedListener {
            var fragment: Fragment? = null

            when (it.itemId){
                R.id.item_menu_home -> {
                    fragment = FragmentHome()
                }
                R.id.item_menu_source -> {
                    fragment = FragmentSource()
                }
            }
            setFragment(fragment)

        }
    }

    private fun defaultFragment(){
        val default = FragmentHome()
        setFragment(default)
    }

    private fun setFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_container_news, fragment)
                .commit()
            return true
        }
        return false
    }


}