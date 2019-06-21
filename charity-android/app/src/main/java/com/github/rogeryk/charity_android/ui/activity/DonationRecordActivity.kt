package com.github.rogeryk.charity_android.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.data.Transaction
import com.github.rogeryk.charity_android.modal.UserModal
import com.github.rogeryk.charity_android.utils.castTo
import com.github.rogeryk.charity_android.utils.launch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_donation_record_activity.*
import javax.inject.Inject

class DonationRecordActivity : AppCompatActivity() {

    lateinit var api: Api
        @Inject set

    lateinit var userModal: UserModal
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_record_activity)

        application.castTo<App>().appComponent.inject(this)
        pullRecords()
    }

    fun pullRecords() = launch {
        val res =
                api.transaction.donation(userModal.currentUser.id).await()
        if (res.errCode != 0) {
            Toast.makeText(this@DonationRecordActivity, res.msg, Toast.LENGTH_LONG).show()
            finish()
        }
        donation_record_list_view.adapter = DonationRecordAdapter(this@DonationRecordActivity, res.data!!.content)

    }
}

class DonationRecordAdapter(
        private val context: Context,
        private val records: List<Transaction>
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView != null) return convertView

        val view = View.inflate(context, R.layout.donation_recorder_view, null)
        view.apply {
            val projectImg = findViewById<ImageView>(R.id.project_img)
            val projectName = findViewById<TextView>(R.id.project_name)
            val orderRemark = findViewById<TextView>(R.id.order_remark)
            val orderMoney = findViewById<TextView>(R.id.order_money)
            val record = records[position]


            Picasso.with(context)
                    .load(record.project!!.img)
                    .into(projectImg)

            projectName.text = record.project!!.name
            orderMoney.text = "￥${record.money}"
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return records[position]
    }

    override fun getItemId(position: Int): Long {
        return records[position].id
    }

    override fun getCount(): Int {
        return records.size
    }

}
