package com.verianggoro.news.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiketapasaja.taaccess.adapter.NewsAdapter
import com.verianggoro.news.R
import com.verianggoro.news.common.Utility
import com.verianggoro.news.databinding.ActivitySearchBinding
import com.verianggoro.news.model.Articles
import com.verianggoro.news.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var viewModelSearch : SearchViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModelSearch = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.searchQuery.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                Utility.hideKeyBoard(binding.searchQuery)
                search()
            }
            true
        }

    }

    private fun search(){
        if (!binding.searchQuery.text.isNullOrEmpty() ){
            binding.loadingBar.visibility = View.VISIBLE
            viewModelSearch.sendingRequest(this, binding.searchQuery.text.toString())
            viewModelSearch.getListNews().observe(this){
                binding.loadingBar.visibility = View.GONE
                if (!it.results.isNullOrEmpty()){
                    newsAdapter = NewsAdapter(it.results!!)
                    binding.rvResultSearch.adapter = newsAdapter
                    binding.rvResultSearch.layoutManager = LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    newsAdapter.notifyDataSetChanged()
                    newsAdapter.setOnClicked(object : NewsAdapter.onItemCallback{
                        override fun itemEventClick(dataEvent: Articles) {
                            toWebView(dataEvent.url!!)
                        }
                    })
                }else{
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "Keyword Harus Terisi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toWebView(urlNews: String){
        val intent = Intent(this, WebViewClientActivity::class.java)
        intent.putExtra(WebViewClientActivity.EVENT_URL, urlNews)
        startActivity(intent)
    }
}