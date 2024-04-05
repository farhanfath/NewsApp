package com.belajar.newsappproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajar.newsappproject.api.NewsService
import com.belajar.newsappproject.data.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _allNews = MutableLiveData<News>()
    val allNews: LiveData<News> = _allNews

    private val _headlines = MutableLiveData<News>()
    val headlines: LiveData<News> = _headlines

    fun getAllNews(query: String, page: Int){
        val news = NewsService.newsInstance.getAllNews(query,page)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    _allNews.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(MainActivity.TAG, "Failed Fetching News", t)
            }
        })
    }

    fun getHeadLines(country: String, page: Int) {
        NewsService.newsInstance
            .getHeadLines(country,page)
            .enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    _headlines.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(MainActivity.TAG, "Failed Fetching News", t)
            }
        })
    }
}