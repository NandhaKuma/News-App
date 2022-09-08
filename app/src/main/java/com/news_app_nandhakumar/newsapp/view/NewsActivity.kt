package com.news_app_nandhakumar.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news_app_nandhakumar.newsapp.R
import com.news_app_nandhakumar.newsapp.adapter.NewsAdapter
import com.news_app_nandhakumar.newsapp.databinding.ActivityNewsBinding
import com.news_app_nandhakumar.newsapp.dialog.Loader
import com.news_app_nandhakumar.newsapp.response.NewsResponse
import com.news_app_nandhakumar.newsapp.utlis.Status
import com.news_app_nandhakumar.newsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var activityNewsBinding: ActivityNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private var newsAdapter: NewsAdapter? = null
    private var getNews = ArrayList<NewsResponse.Source>()
    @Inject lateinit var loader: Loader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel.progressbar.observe(this) { loader.show() }
        activityNewsBinding.newsRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        activityNewsBinding.newsRecycler.setHasFixedSize(true)
        newsViewModel.getNews().observe(this) {
            loader.dismiss()
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null && it.data.status == "ok") {
                        getNews.clear()
                        getNews.addAll(it.data.sources)

                        if (getNews == null || getNews.isEmpty()) {
                            activityNewsBinding.newsRecycler.visibility = View.GONE
                            activityNewsBinding.noDataFound.visibility = View.VISIBLE
                        } else {

                            activityNewsBinding.newsRecycler.visibility = View.VISIBLE
                            activityNewsBinding.noDataFound.visibility = View.GONE


                            newsAdapter = NewsAdapter(getNews)
                            activityNewsBinding.newsRecycler.adapter = newsAdapter

                            newsAdapter!!.setRecyclerListener(object : NewsAdapter.RecyclerListener {
                                override fun onclick(position: Int, getNews: ArrayList<NewsResponse.Source>, title: String) {
                                    if (title == "Click")
                                        startActivity(
                                            Intent(this@NewsActivity, NewsHistoryActivity::class.java).putExtra("id", getNews[position].id)
                                                .putExtra("name", getNews[position].name)
                                                .putExtra("description", getNews[position].description)
                                                .putExtra("url", getNews[position].url)
                                                .putExtra("category", getNews[position].category)
                                                .putExtra("language", getNews[position].language)
                                                .putExtra("country", getNews[position].country)
                                        )

                                }
                            })
                        }


                    }
                }
                Status.ERROR -> {
                    loader.dismiss()
                    makeToast(it.message)
                }
                else -> {
                    loader.dismiss()
                    makeToast(resources.getString(R.string.something_went_wrong))
                }
            }
        }


    }

    private fun makeToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}