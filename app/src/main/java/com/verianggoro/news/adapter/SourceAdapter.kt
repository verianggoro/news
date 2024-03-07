package com.tiketapasaja.taaccess.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.verianggoro.news.databinding.SourceItemBinding
import com.verianggoro.news.model.Articles
import com.verianggoro.news.model.Resource
import java.text.SimpleDateFormat
import java.util.Locale

class SourceAdapter(
    private val listEvent: List<Resource>
): RecyclerView.Adapter<SourceAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SourceAdapter.EventViewHolder {
        return EventViewHolder(
            SourceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SourceAdapter.EventViewHolder, position: Int) {
        listEvent.let {
            holder.bind(it, position)
            holder.itemView.setOnClickListener { onCallBack?.itemEventClick(listEvent[position]) }
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class EventViewHolder(private val binding: SourceItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: List<Resource>, position: Int){
            binding.titleSourceItem.text = data[position].name
        }
    }

    private var onCallBack: onItemCallback? = null
    fun setOnClicked(onItemCallback: onItemCallback){
        this.onCallBack = onItemCallback
    }

    interface onItemCallback{
        fun itemEventClick(dataEvent: Resource)
    }
}

