package com.github.rogeryk.charity_android.ui.view

import android.content.Context
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.data.Comment
import com.github.rogeryk.charity_android.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class CommentListView(context: Context, attrs: AttributeSet)
    : FrameLayout(context, attrs), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    var projectId: Long? = null
        set(value) {
            field = value
            pullComments()
        }

    var api: Api? = null

    private var page = 0

    private var total:Long = 0

    private val rootListView: ListView

    var commentListener: ((Long?, Long?) -> Unit)? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val linearLayout = inflater.inflate(R.layout.coment_list_view, this, true)
        rootListView = linearLayout.findViewById<ListView>(R.id.comment_list_view)
        setListeners()
    }

    private fun setListeners() {
        rootListView.setOnItemClickListener { parent, view, position, id ->
            commentListener?.invoke(null, null)
        }
    }





    fun pullComments() = launch {
        val projectId = projectId ?: return@launch
        api?.let { api ->
            val res = api.comment.byProjectId(projectId, page=page).await()
            Log.i("comments", res.toString())
            if (res.errCode == 0) {
                val page = res.data
                page?.let {
                    total = it.total
                    rootListView.adapter = CommentAdapter(context, it.content)
                }
            }
        }
    }
}

class CommentAdapter(
        private val context: Context,
        private val comments: List<Comment>
) : BaseAdapter() {

    class ViewHolder(
        val userIcon: ImageView,
        val userNickname: TextView,
        val createdTime: TextView,
        val content: TextView,
        val subCommentList: ListView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = View.inflate(context, R.layout.comment_item_view, null)
            view.apply {
                tag = ViewHolder(
                        userIcon = findViewById(R.id.user_icon),
                        userNickname = findViewById(R.id.user_nickname),
                        createdTime = findViewById(R.id.comment_created_time),
                        content = findViewById(R.id.comment_content),
                        subCommentList = findViewById(R.id.comment_sub_comment_list)
                )
            }
        } else {
            view = convertView
        }

        val holder = view.tag as ViewHolder
        val comment = comments[position]
        Picasso.with(context)
                .load(comment.commenter.avatar)
                .transform(CircleTransform())
                .into(holder.userIcon)
        holder.apply {
            userNickname.text = comment.commenter.nickName
            val fomatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
            createdTime.text = fomatter.format(comment.createdTime)
            content.text = comment.content
            subCommentList.adapter = SubCommentAdapter(context, comment.subComments)
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return comments[position]
    }

    override fun getItemId(position: Int): Long {
        return comments[position].id
    }

    override fun getCount(): Int {
        return comments.size
    }

}

class SubCommentAdapter(
        private val context: Context,
        private val comments: List<Comment>
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView == null) {
            View.inflate(context, R.layout.sub_comment_item_view, null) as TextView
        } else {
            convertView as TextView
        }

        view.text = ""

        val comment = comments[position]
        val commenterText = comment.commenter.nickName
        val huifu = " 回复 "
        val replyUserText = "${comment.replyComment?.commenter?.nickName ?: ""}:  "
        val contentText = comment.content
        val text = SpannableString( commenterText +
                huifu +
                replyUserText +
                contentText)

        val newBoldStyle = { TextAppearanceSpan(context, R.style.bold_text_appearance)}

        var start = 0
        text.setSpan(newBoldStyle(), start, start + commenterText.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)
        start += commenterText.length
        start += huifu.length
        text.setSpan(newBoldStyle(), start, start + replyUserText.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)

        view.text = text

        return view;
    }

    override fun getItem(position: Int): Any {
        return comments[position]
    }

    override fun getItemId(position: Int): Long {
        return comments[position].id
    }

    override fun getCount(): Int {
        return comments.size
    }

}