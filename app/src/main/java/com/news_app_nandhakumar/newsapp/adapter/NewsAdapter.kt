package com.news_app_nandhakumar.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news_app_nandhakumar.newsapp.databinding.NewsAdapterLayoutBinding
import com.news_app_nandhakumar.newsapp.response.NewsResponse

class NewsAdapter(var getNews: ArrayList<NewsResponse.Source>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private lateinit var recyclerListener: RecyclerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NewsAdapterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            holder.binding.title.text = (getNews[position].name ?: "")
            holder.binding.descrition.text = (getNews[position].description ?: "")
           /* if ((getNews[position].url ?: "").isNotEmpty())
                Glide.with(holder.itemView.context).load(getNews[position].url).into(holder.binding.image)*/
            holder.binding.image.setOnClickListener { recyclerListener.onclick(position, getNews, "Click") }

        } catch (e: Exception) {

        }


    }

    override fun getItemCount(): Int {
        return getNews.size
    }

    class ViewHolder(val binding: NewsAdapterLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    interface RecyclerListener {
        fun onclick(position: Int,getNews: ArrayList<NewsResponse.Source>,title: String)
    }

    fun setRecyclerListener(recyclerListener: RecyclerListener) {
        this.recyclerListener = recyclerListener
    }


}