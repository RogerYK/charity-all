package com.github.rogeryk.charity_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.utils.CircleTransform
import com.github.rogeryk.charity_android.utils.castTo
import com.squareup.picasso.Picasso
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

class NewsActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    lateinit var api: Api
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        application.castTo<App>().appComponent.inject(this)

        val id = intent.getLongExtra("id", -1)
        if (id != -1L) {
            pullNews(id)
        }
    }

    fun pullNews(id: Long) = launch {
        val data = api.news.byId(id).await().data
        data?.let {
            Picasso.with(baseContext)
                    .load(it.author.avatar)
                    .transform(CircleTransform())
                    .into(user_icon)
            user_nickname.text = it.author.nickName
            val formatter = SimpleDateFormat("发布于 yyyy-MM-dd HH:mm")
            created_time.text = formatter.format(it.createdTime)
            RichText.fromHtml(it.content).into(news_content)
        }
    }


}
