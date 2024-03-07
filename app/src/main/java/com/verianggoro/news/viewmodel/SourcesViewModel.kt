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
import com.verianggoro.news.model.Sources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SourcesViewModel: ViewModel() {
    private val TAG = SourcesViewModel::class.java.simpleName
    private var listSource : MutableLiveData<Sources> = MutableLiveData()

    fun getListSource(): LiveData<Sources> = listSource

    fun sendingRequest(context: Context, category: String){
        val getToken = "87321fcf8a8d43a0af6725546c48b5cd"
        val params: HashMap<String, String>? = hashMapOf(
            "apiKey" to getToken,
            "category" to category
        )
        val form: HashMap<String, String?> = hashMapOf()
        Log.i(TAG, params.toString())
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val url = "${Config.Main_Url}${Config.All_Source}"
                try {
                    val response = OkHttpHelper.executeGet(url, params, form, null)
                    Log.i(TAG, response!!)
                    val gson = Gson()
                    val mapper = gson.fromJson(response, Sources::class.java)
                    listSource.postValue(mapper)
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