package com.verianggoro.news

import android.content.Context
import androidx.multidex.MultiDexApplication

class NewsApp: MultiDexApplication() {
    companion object{
        lateinit var instance: NewsApp private set
        fun getInstance(): Context?{
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}