package com.github.rogeryk.charity_android.ui.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.rogeryk.charity_android.App

import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.data.NewsBasic
import com.github.rogeryk.charity_android.ui.NewsActivity
import com.github.rogeryk.charity_android.utils.CircleTransform
import com.github.rogeryk.charity_android.utils.castTo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class NewsFragment : Fragment(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    lateinit var api: Api
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.let { it.application.castTo<App>().appComponent.inject(this)}
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        news_list_view.setOnItemClickListener { _, _, _, id ->
            val intent = Intent(activity, NewsActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        pullNews()
    }

    fun pullNews() = launch {
        val res = api.news.latest().await()
        res.data?.let {
            Log.i("news", it.toString())
            news_list_view.adapter = NewsAdapter(context!!, it)
        }
    }


}

class NewsAdapter(
      private  val context: Context,
      private val newsList: List<NewsBasic>): BaseAdapter() {

    class ViewHolder(
            val userIcon: ImageView,
            val userNickname: TextView,
            val createdTime: TextView,
            val introduce: TextView,
            val img: ImageView

    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView != null) {
            view = convertView
        } else {
            view = View.inflate(context, R.layout.news_list_item, null)
            view.apply {
                tag = ViewHolder(
                        userIcon = findViewById(R.id.user_icon),
                        userNickname = findViewById(R.id.user_nickname),
                        createdTime = findViewById(R.id.created_time),
                        introduce = findViewById(R.id.introduce),
                        img = findViewById(R.id.news_img)
                )
            }
        }
        val holder = view.tag as ViewHolder
        val news = newsList[position]
        Picasso.with(context)
                .load(news.author.avatar)
                .transform(CircleTransform())
                .into(holder.userIcon)
        Picasso.with(context)
                .load(news.img)
                .into(holder.img)
        holder.apply {
            userNickname.text = news.author.nickName
            val now = System.currentTimeMillis()
            val old = news.createdTime.time
            val period = now - old
            val minute = period/1000/60
            if (minute < 60) {
                createdTime.text = "${minute}分钟前 发布"
            } else {
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm 发布")
                createdTime.text = formatter.format(news.createdTime)
            }
            introduce.text = news.introduce
        }
        return view;
    }

    override fun getItem(position: Int): Any {
        return newsList[position]
    }

    override fun getItemId(position: Int): Long {
        return newsList[position].id
    }

    override fun getCount(): Int {
        return newsList.size
    }

}
