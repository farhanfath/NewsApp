package com.belajar.newsappproject.api

import com.belajar.newsappproject.data.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY&pageSize=100")
    fun getHeadLines(
        @Query("country") country: String,
        @Query("Page") page: Int
    ) : Call<News>

    @GET("v2/everything?apiKey=$API_KEY")
    fun getAllNews(
        @Query("q") query: String,
        @Query("Page") page: Int
    ): Call<News>

    companion object {
        const val API_KEY = "961e7609fd264e679b86297ff001344d"
    }
}

//object NewsService {
//    val newsInstance: NewsInterface
//
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        newsInstance = retrofit.create(NewsInterface::class.java)
//    }
//}