package com.github.rogeryk.charity_android.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.api.CommentForm
import com.github.rogeryk.charity_android.api.DonationForm
import com.github.rogeryk.charity_android.modal.UserModal
import com.github.rogeryk.charity_android.ui.dialog.CommentDialog
import com.github.rogeryk.charity_android.ui.login.LoginActivity
import com.github.rogeryk.charity_android.utils.CircleTransform
import com.github.rogeryk.charity_android.utils.castTo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProjectDetailActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    lateinit var api: Api
        @Inject set

    lateinit var userModal: UserModal
        @Inject set

    private var projectId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)


        application.castTo<App>().appComponent.inject(this)
        val id = intent.getLongExtra("projectId", -1)
        Log.i("project detail page", "id=$id")
        if (id != -1L) {
            projectId = id
            pullProject(id)
            comment_list.api = api
            comment_list.projectId = id
        }
        initListeners()
    }

    fun initListeners() {
        btn_support.setOnClickListener {showDonateDialog()}
        comment_list.commentListener = this::onComment
    }


    fun onComment(parentId: Long?, replyId: Long?) {
        if (userModal.isLogined.value != true) {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val commentDialog = CommentDialog(this)
        commentDialog.confirmListener = {content ->
            val form = CommentForm(
                    projectId = projectId,
                    parentId = parentId,
                    replyId = replyId,
                    content = content
            )
            comment(form)
        }
        commentDialog.show()
    }

    fun comment(form: CommentForm) = launch {
        val res = api.comment.save(form).await()
        val msg = if (res.errCode != 0) res.msg else "评论成功"
        Toast.makeText(this@ProjectDetailActivity, msg, Toast.LENGTH_LONG).show()
    }


    private fun showDonateDialog() {
        if (userModal.isLogined.value != true) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        val builder =  AlertDialog.Builder(this)
        builder.apply {
            title = "捐助金额"
            val inflater = layoutInflater
            val view = inflater.inflate(R.layout.donate_dialog_view, null)
            val moneyEdit = view.findViewById<EditText>(R.id.money_edit)
            setView(view)
            setPositiveButton("确定") { _, _ ->
                Log.i("donate", "money: ${moneyEdit.text}")
                donate(BigDecimal(moneyEdit.text.toString()))
            }
            setNegativeButton("取消") {dialog, _ ->
                dialog.cancel()
            }
        }
        builder.create().show()

    }


    fun donate(amount: BigDecimal) = launch {
        val form = DonationForm(projectId = projectId, amount = amount)
        val res =  api.transaction.donate(form).await()
        val msg =   if (res.errCode != 0)  res.msg else  "捐款成功"
        Toast.makeText(this@ProjectDetailActivity, msg, Toast.LENGTH_LONG).show()
    }

    fun pullProject(id: Long) = launch {
        val project =  api.project.detail(id).await().data

        Log.i("project", project.toString())

        project?.let {
            Picasso.with(applicationContext)
                    .load(it.img)
                    .into(project_img)
            Picasso.with(applicationContext)
                    .load(it.author.avatar)
                    .transform(CircleTransform())
                    .into(user_icon)
            project_name.text = project.name
            user_nickname.text = project.author.nickName
            val percent = project.raisedMoney.divide(project.targetMoney).toFloat()*100
            project_progress.progress = percent.toInt()
            project_raised_money.text = "￥${project.raisedMoney.toFloat()}"
            project_raised_money_percent.text = "已筹款 ${percent}%"
            project_target_money.text = "￥${project.targetMoney.toFloat()}"

            val endTime = project.endTime
            val now = Date()
            val endTimeText: String
            val endTimeTitle: String
            if (endTime.before(now)) {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                endTimeText = formatter.format(endTime)
                endTimeTitle = "结束时间"
            } else {
                val days = (endTime.time - now.time) / 3600 / 24 / 1000
                endTimeText = "${days}天"
                endTimeTitle = "剩余时间"
            }
            project_remain_time.text = endTimeText
            project_remain_time_title.text = endTimeTitle
            project_content.text = project.summary

        }


    }
}

