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
import com.verianggoro.news.model.SearchNewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel: ViewModel() {
    private val TAG = SearchViewModel::class.java.simpleName
    private var listNews : MutableLiveData<SearchNewsModel> = MutableLiveData()

    fun getListNews(): LiveData<SearchNewsModel> = listNews

    fun sendingRequest(context: Context, query: String){
        val getToken = "87321fcf8a8d43a0af6725546c48b5cd"
        val params: HashMap<String, String>? = hashMapOf(
            "apiKey" to getToken,
            "q" to query
        )
        val form: HashMap<String, String?> = hashMapOf()
        Log.i(TAG, params.toString())
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val url = "${Config.Main_Url}${Config.SEARCH_EVERYTHING}"
                try {
                    val response = OkHttpHelper.executeGet(url, params, form, null)
                    Log.i(TAG, response!!)
                    val gson = Gson()
                    val mapper = gson.fromJson(response, SearchNewsModel::class.java)
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