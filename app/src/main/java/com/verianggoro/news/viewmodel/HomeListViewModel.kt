package com.verianggoro.news.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.verianggoro.news.common.Config
import com.verianggoro.news.common.OkHttpHelper
import com.verianggoro.news.common.Utility
import com.verianggoro.news.model.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeListViewModel: ViewModel() {
    private val TAG = HomeListViewModel::class.java.simpleName
    private var listNews : MutableLiveData<NewsModel> = MutableLiveData()

    fun getListNews(): LiveData<NewsModel> = listNews

    fun sendingRequest(context: Context,params: HashMap<String, *>){
        val form: HashMap<String, String?> = hashMapOf()
        Log.i(TAG, params.toString())
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val url = "${Config.Main_Url}${Config.Top_Headlines}"
                try {
                    val response = OkHttpHelper.executeGet(url, params, form, null)
                    Log.i(TAG, response!!)
                    val gson = Gson()
                    val mapper = gson.fromJson(response, NewsModel::class.java)
                    listNews.postValue(mapper)
                }catch (e: JsonSyntaxException){
                    withContext(Dispatchers.Main) {
                        Utility.showErrorDialog(context, e)
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        Utility.showErrorDialog(context, e)
                    }
                }
            }
        }
    }

}