package com.verianggoro.news.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiketapasaja.taaccess.adapter.NewsAdapter
import com.tiketapasaja.taaccess.adapter.SourceAdapter
import com.verianggoro.news.activity.NewsSourceActivity
import com.verianggoro.news.activity.WebViewClientActivity
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
        initialData(view, param2!!, param1!!)
        loadMoreNews(param2!!, param1!!)
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
            val getToken = "87321fcf8a8d43a0af6725546c48b5cd"
            val country = "id"

            val PAGE_INITIAL = 1
            when (paramsCategory){
                "Business" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "business",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Entertainment" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "entertainment",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "General" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "general",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Health" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "health",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Science" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "science",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Sport" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "sport",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
                "Technology" -> {
                    val params: HashMap<String, *> = hashMapOf(
                        "apiKey" to getToken,
                        "country" to country,
                        "category" to "technology",
                        "page" to PAGE_INITIAL
                    )
                    viewModelNews.sendingRequest(view.context, params)
                }
            }

            viewModelNews.getListNews().observe(requireActivity()){
                if (!it.results!!.isNullOrEmpty()) {
                    newsAdapter = NewsAdapter(it.results!!)
                    binding.rvNewsList.adapter = newsAdapter
                    binding.rvNewsList.layoutManager = layoutManager
                    newsAdapter.setOnClicked(object : NewsAdapter.onItemCallback{
                        override fun itemEventClick(dataEvent: Articles) {
                            toWebView(dataEvent.url!!)
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

    private fun loadMoreNews(paramsCategory: String, indicateScreen: String ){
        if (indicateScreen.equals("Home")) {
            var initial = 1
            binding.rvNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModelNews.getListNews().observe(requireActivity()){
                        var listNew: List<Articles> = it.results!!
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == it.results!!.size - 1){
                            val getToken = "87321fcf8a8d43a0af6725546c48b5cd"
                            val country = "id"
                            when (paramsCategory){
                                "Business" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "business",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                                "Entertainment" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "entertainment",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                                "General" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "general",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                                "Health" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "health",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                                "Science" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "science",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                                "Sport" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "sport",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                                "Technology" -> {
                                    initial++
                                    val params: HashMap<String, *> = hashMapOf(
                                        "apiKey" to getToken,
                                        "country" to country,
                                        "category" to "technology",
                                        "page" to initial
                                    )
                                    viewModelNews.sendingRequest(requireContext(), params)
                                }
                            }
                            viewModelNews.getListNews().observe(requireActivity()){newNews ->
                                if (!it.results!!.isNullOrEmpty()) {
                                    newsAdapter = NewsAdapter(listNew.plus(newNews.results!!))
                                    binding.rvNewsList.adapter = newsAdapter
                                    binding.rvNewsList.layoutManager = layoutManager
                                    newsAdapter.setOnClicked(object : NewsAdapter.onItemCallback{
                                        override fun itemEventClick(dataEvent: Articles) {
                                            toWebView(dataEvent.url!!)
                                        }
                                    })
                                    newsAdapter.notifyDataSetChanged()
                                }else{
                                    Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            })
        }
    }
}