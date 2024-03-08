package com.verianggoro.news.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiketapasaja.taaccess.adapter.NewsAdapter
import com.tiketapasaja.taaccess.adapter.SourceAdapter
import com.verianggoro.news.activity.NewsSourceActivity
import com.verianggoro.news.activity.WebViewClientActivity
import com.verianggoro.news.common.Config
import com.verianggoro.news.databinding.FragmentGeneralBinding
import com.verianggoro.news.model.Articles
import com.verianggoro.news.model.Resource
import com.verianggoro.news.viewmodel.HomeListViewModel
import com.verianggoro.news.viewmodel.SourcesViewModel

private const val ARG_PARAM2 = "param2"
private const val ARG_INDICATE_SCREEN = "indicated"

class FragmentGeneral : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelNews: HomeListViewModel
    private lateinit var viewModelSource : SourcesViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var sourceAdapter: SourceAdapter
    private val layoutManager = LinearLayoutManager(
        this.context,
        LinearLayoutManager.VERTICAL,
        false
    )

    private lateinit var lisNewsArticles: ArrayList<Articles>
    private var initialPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getString(ARG_PARAM2)
            param1 = it.getString(ARG_INDICATE_SCREEN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentGeneralBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNews = ViewModelProvider(this)[HomeListViewModel::class.java]
        viewModelSource = ViewModelProvider(this)[SourcesViewModel::class.java]
        binding.loadingBar.visibility = View.VISIBLE
        initialData(view, param2!!, param1!!)
    }

    companion object {
        fun newInstance(tabSelect: String, screenIndicate: String) =
            FragmentGeneral().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM2, tabSelect)
                    putString(ARG_INDICATE_SCREEN, screenIndicate)
                }
            }
    }

    private fun initialData(view: View, paramsCategory: String, indicateScreen: String){
        if (indicateScreen.equals("Home")){
            lisNewsArticles = ArrayList()
            when (paramsCategory){
                "Business" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "business",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Entertainment" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "entertainment",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "General" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "general",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Health" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "health",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Science" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "science",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Sport" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "sport",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Technology" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to Config.TOKEN,
                        "country" to Config.COUNTRY,
                        "category" to "technology",
                        "page" to initialPage
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
            }

            viewModelNews.getListNews().observe(requireActivity()){
                binding.loadingBar.visibility = View.GONE
                if (!it.results!!.isNullOrEmpty()) {
                    if (lisNewsArticles.isNullOrEmpty()){
                        lisNewsArticles = it.results!!
                        newsAdapter = NewsAdapter(lisNewsArticles)
                        binding.rvNewsList.adapter = newsAdapter
                    }else{
                        lisNewsArticles.addAll(it.results!!)
                        newsAdapter.notifyDataSetChanged()
                    }
                    binding.rvNewsList.layoutManager = layoutManager
                    newsAdapter.setOnClicked(object : NewsAdapter.onItemCallback{
                        override fun itemEventClick(dataEvent: Articles) {
                            toWebView(dataEvent.url!!)
                        }
                    })
                    binding.rvNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                            val sizer = lisNewsArticles.size
                            if (lastVisibleItemPosition == (sizer - 1)){
                                when (paramsCategory){
                                    "Business" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "business",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                    "Entertainment" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "entertainment",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                    "General" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "general",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                    "Health" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "health",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                    "Science" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "science",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                    "Sport" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "sport",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                    "Technology" -> {
                                        initialPage++
                                        val params: HashMap<String, *> = hashMapOf(
                                            "apiKey" to Config.TOKEN,
                                            "country" to Config.COUNTRY,
                                            "category" to "technology",
                                            "page" to initialPage
                                        )
                                        viewModelNews.sendingRequest(requireContext(), params)
                                    }
                                }
                            }
                        }
                    })
                }else{
                    Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            when (paramsCategory){
                "Business" -> {
                    viewModelSource.sendingRequest(view.context, "business")
                }
                "Entertainment" -> {
                    viewModelSource.sendingRequest(view.context, "entertainment")
                }
                "General" -> {
                    viewModelSource.sendingRequest(view.context, "general")
                }
                "Health" -> {
                    viewModelSource.sendingRequest(view.context, "health")
                }
                "Science" -> {
                    viewModelSource.sendingRequest(view.context, "science")
                }
                "Sport" -> {
                    viewModelSource.sendingRequest(view.context, "sport")
                }
                "Technology" -> {
                    viewModelSource.sendingRequest(view.context, "technology")
                }
            }

            viewModelSource.getListSource().observe(requireActivity()){
                if (!it.results!!.isNullOrEmpty()) {
                    sourceAdapter = SourceAdapter(it.results!!)
                    binding.rvNewsList.adapter = sourceAdapter
                    binding.rvNewsList.layoutManager = layoutManager
                    sourceAdapter.setOnClicked(object : SourceAdapter.onItemCallback{
                        override fun itemEventClick(dataEvent: Resource) {
                            toSource(dataEvent.id!!)
                        }
                    })
                }else{
                    Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toWebView(urlNews: String){
        val intent = Intent(context, WebViewClientActivity::class.java)
        intent.putExtra(WebViewClientActivity.EVENT_URL, urlNews)
        startActivity(intent)
    }

    private fun toSource(sourceId: String){
        val intent = Intent(context, NewsSourceActivity::class.java)
        intent.putExtra(NewsSourceActivity.EVENT_ID, sourceId)
        startActivity(intent)
    }

}