package com.verianggoro.news.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiketapasaja.taaccess.adapter.NewsAdapter
import com.verianggoro.news.R
import com.verianggoro.news.databinding.ActivityNewsSourceBinding
import com.verianggoro.news.model.Articles
import com.verianggoro.news.viewmodel.HomeListViewModel

class NewsSourceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsSourceBinding
    private lateinit var viewmodelHome : HomeListViewModel
    private lateinit var newsAdapter: NewsAdapter
    var idSource : String? = null
    companion object {
        const val EVENT_ID = "event_id_source"
    }

    private lateinit var lisNewsArticles: ArrayList<Articles>
    private var initialPage = 1
    private val layoutManager = LinearLayoutManager(
        this,
        LinearLayoutManager.VERTICAL,
        false
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_source)
        lisNewsArticles = ArrayList()
        viewmodelHome = ViewModelProvider(this)[HomeListViewModel::class.java]
        if (intent.extras != null) {
            idSource = intent.getStringExtra(EVENT_ID)
            val getToken = "87321fcf8a8d43a0af6725546c48b5cd"
            val params: HashMap<String, *> = hashMapOf(
                "apiKey" to getToken,
                "sources" to idSource!!,
                "page" to initialPage
            )
            viewmodelHome.sendingRequest(this, params)
            viewmodelHome.getListNews().observe(this){
                if (!it.results!!.isNullOrEmpty()) {
                    if (lisNewsArticles.isNullOrEmpty()){
                        lisNewsArticles = it.results!!
                        newsAdapter = NewsAdapter(lisNewsArticles)
                        binding.rvSourceNews.adapter = newsAdapter
                        binding.rvSourceNews.layoutManager = layoutManager
                    }else{
                        lisNewsArticles.addAll(it.results!!)
                        newsAdapter.notifyDataSetChanged()
                    }
                    newsAdapter.setOnClicked(object : NewsAdapter.onItemCallback{
                        override fun itemEventClick(dataEvent: Articles) {
                            toWebView(dataEvent.url!!)
                        }
                    })
                    binding.rvSourceNews.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                            val sizer = lisNewsArticles.size
                            if (lastVisibleItemPosition == (sizer - 1)){
                                initialPage++
                                val params: HashMap<String, *> = hashMapOf(
                                    "apiKey" to getToken,
                                    "sources" to idSource!!,
                                    "page" to initialPage
                                )
                                viewmodelHome.sendingRequest(this@NewsSourceActivity , params)
                            }}})
                }else{
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun toWebView(urlNews: String){
        val intent = Intent(this, WebViewClientActivity::class.java)
        intent.putExtra(WebViewClientActivity.EVENT_URL, urlNews)
        startActivity(intent)
    }
}