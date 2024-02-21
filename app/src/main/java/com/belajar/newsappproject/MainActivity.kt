package com.belajar.newsappproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.belajar.newsappproject.adapter.AllNewsAdapter
import com.belajar.newsappproject.adapter.HeadLineAdapter
import com.belajar.newsappproject.data.News
import com.belajar.newsappproject.network.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var headlineRV : RecyclerView
    private lateinit var allNewsRV : RecyclerView

    private lateinit var headLineAdapter: HeadLineAdapter
    private lateinit var allNewsAdapter: AllNewsAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBarActivityMain : ProgressBar

    private lateinit var breakingNewsLayout : LinearLayout
    private lateinit var allNewsLayout: LinearLayout

    companion object {
        const val TAG = "FARHAN"
    }

    val allNewsLayoutManager = LinearLayoutManager(this)

    var pageNum = 1
    var totalAllNews = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headlineRV = findViewById(R.id.terkinirv)
        allNewsRV = findViewById(R.id.semuaBeritarv)
        swipeRefreshLayout = findViewById(R.id.swipeContainer_ActivityMain)
        breakingNewsLayout = findViewById(R.id.terkiniLayout)
        allNewsLayout = findViewById(R.id.semuaBeritaLayout)
        progressBarActivityMain = findViewById(R.id.progressBar_ActivityMain)

        hideAll()
        getAllNews()
        getHeadLines()
        showAll()

        // untuk refresh dengan swipe
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getHeadLines()
            getAllNews()
        }

        allNewsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(totalAllNews > allNewsLayoutManager.itemCount && allNewsLayoutManager.findFirstVisibleItemPosition() >= allNewsLayoutManager.itemCount -1) {
                    pageNum++
                    getAllNews()
                }
            }
        })

    }

    private fun showAll(){
        progressBarActivityMain.visibility = View.INVISIBLE
        breakingNewsLayout.visibility = View.VISIBLE
        allNewsLayout.visibility = View.VISIBLE
    }

    private fun hideAll(){
        breakingNewsLayout.visibility = View.INVISIBLE
        allNewsLayout.visibility = View.INVISIBLE
        progressBarActivityMain.visibility = View.VISIBLE
    }

    private fun getAllNews(){

        //memanggil object dari NewsInterface

        val news = NewsService.newsInstance.getAllNews("keuangan",pageNum)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val allNews = response.body()
                if (allNews != null) {
                    totalAllNews = allNews.totalResults
                    allNewsAdapter = AllNewsAdapter(this@MainActivity)
                    allNewsAdapter.clear()
                    allNewsAdapter.addAll(allNews.articles)
                    allNewsRV.adapter = allNewsAdapter
                    allNewsRV.layoutManager = allNewsLayoutManager

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG, "Failed Fetching News", t)
            }
        })
    }

    private fun getHeadLines(){
        val news = NewsService.newsInstance.getHeadLines("id",1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val headNews = response.body()
                if (headNews != null) {
                    headLineAdapter = HeadLineAdapter(this@MainActivity)
                    headLineAdapter.clear()
                    headLineAdapter.addAll(headNews.articles)
                    headlineRV.adapter = headLineAdapter
                    headlineRV.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG, "Failed Fetching News", t)
            }
        })
    }

}