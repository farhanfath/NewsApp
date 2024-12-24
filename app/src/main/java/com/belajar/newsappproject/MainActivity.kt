package com.belajar.newsappproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.belajar.newsappproject.adapter.AllNewsAdapter
import com.belajar.newsappproject.adapter.HeadLineAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var headlineRV : RecyclerView
    private lateinit var allNewsRV : RecyclerView

    private lateinit var headLineAdapter: HeadLineAdapter
    private lateinit var allNewsAdapter: AllNewsAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    val allNewsLayoutManager = LinearLayoutManager(this)

    companion object {
        const val TAG = "FARHAN"
        var pageNum = 1
        var totalAllNews = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        headlineRV = findViewById(R.id.terkinirv)
        allNewsRV = findViewById(R.id.semuaBeritarv)
        swipeRefreshLayout = findViewById(R.id.swipeContainer_ActivityMain)

        showNewsHandler()

        swipeRefreshLayoutHandler()

        rvOnScrollHandler()

    }

    private fun showNewsHandler() {
        hideAll()
        getAllHandler()
        getHeadLinesHandler()
        showAll()
    }

    private fun rvOnScrollHandler() {
        allNewsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(totalAllNews > allNewsLayoutManager.itemCount &&
                    allNewsLayoutManager.findFirstVisibleItemPosition() >= allNewsLayoutManager.itemCount -1) {
                    pageNum++
                    getAllHandler()
                }
            }
        })
    }

    private fun swipeRefreshLayoutHandler() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getAllHandler()
            getHeadLinesHandler()
        }
    }

    private fun showAll(){
        headlineRV.visibility = View.VISIBLE
        allNewsRV.visibility = View.VISIBLE
    }

    private fun hideAll(){
        headlineRV.visibility = View.INVISIBLE
        allNewsRV.visibility = View.INVISIBLE
    }

    private fun getAllHandler() {
        viewModel.getAllNews("bank", 1)
        viewModel.allNews.observe(this) { news ->
            totalAllNews = news.totalResults
            allNewsAdapterHandler()
            allNewsAdapter.clear()
            allNewsAdapter.addAll(news.articles)
        }
    }

    private fun getHeadLinesHandler(){
        viewModel.getHeadLines("us",1)
        viewModel.headlines.observe(this) { news ->
            headlinesNewsAdapterHandler()
            headLineAdapter.clear()
            headLineAdapter.addAll(news.articles)
        }
    }

    private fun allNewsAdapterHandler() {
        allNewsAdapter = AllNewsAdapter(this@MainActivity)
        allNewsRV.adapter = allNewsAdapter
        allNewsRV.layoutManager = allNewsLayoutManager
    }

    private fun headlinesNewsAdapterHandler() {
        headLineAdapter = HeadLineAdapter(this@MainActivity)
        headlineRV.adapter = headLineAdapter
        headlineRV.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
    }
}
