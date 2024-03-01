package com.belajar.newsappproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belajar.newsappproject.NewsWebView
import com.belajar.newsappproject.R
import com.belajar.newsappproject.data.Article
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class HeadLineAdapter(private val context : Context) : RecyclerView.Adapter<HeadLineAdapter.ViewHolder>() {

    private val articles : ArrayList<Article> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.newsheadline, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article : Article = articles[position]

        //titletv
        holder.title.text = article.title

        //authortv
        if(article.author != null){
            holder.author.text = article.author.toString()
        }else{
            holder.author.setText(R.string.unknown)
        }

        //date format change
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val inputDate = article.publishedAt
        val outputDate = outputDateFormat.format(inputDateFormat.parse(inputDate)!!)

        //datetv
        holder.date.text = outputDate

        //newsImage
        Glide.with(context)
            .load(article.urlToImage)
            .placeholder(R.drawable.newsplaceholder)
            .into(holder.imageView)

        //urlWebView
        holder.itemView.setOnClickListener {
            val newsWeb = Intent(holder.itemView.context, NewsWebView::class.java)
            newsWeb.putExtra("url", article.url)
            holder.itemView.context.startActivity(newsWeb)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.findViewById(R.id.headlineImgView)
        val title : TextView = itemView.findViewById(R.id.headlineTitleTv)
        val author : TextView = itemView.findViewById(R.id.headlineAuthorTv)
        val date : TextView = itemView.findViewById(R.id.headlineDateTv)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(art: List<Article>) {
        articles.addAll(art)
        notifyDataSetChanged()
    }
}