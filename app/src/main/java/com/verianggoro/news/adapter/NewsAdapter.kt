package com.tiketapasaja.taaccess.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.verianggoro.news.R
import com.verianggoro.news.databinding.NewsItemBinding
import com.verianggoro.news.model.Articles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsAdapter(
    private val listEvent: List<Articles>
): RecyclerView.Adapter<NewsAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.EventViewHolder {
        return EventViewHolder(
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.EventViewHolder, position: Int) {
        listEvent.let {
            holder.bind(it, position)
            holder.itemView.setOnClickListener { onCallBack?.itemEventClick(listEvent[position]) }
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class EventViewHolder(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: List<Articles>, position: Int){
            Glide.with(binding.root)
                .load(data[position].urlImg)
                .error(R.drawable.image_break)
                .into(binding.imgNewsItem)
            binding.authorNewsItem.text = data[position].author
            binding.titleNewsItem.text = data[position].title
            var dateFormate = convertISO8601ToDate(data[position].published!!)
            binding.dateNewsItem.text = formatDateToDDMMYYYY(dateFormate)
        }
    }
    fun convertISO8601ToDate(iso8601String: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        return dateFormat.parse(iso8601String)
    }

    fun formatDateToDDMMYYYY(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    private var onCallBack: onItemCallback? = null
    fun setOnClicked(onItemCallback: onItemCallback){
        this.onCallBack = onItemCallback
    }

    interface onItemCallback{
        fun itemEventClick(dataEvent: Articles)
    }
}

