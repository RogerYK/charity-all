package com.github.rogeryk.charity_android.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.modal.UserModal
import com.github.rogeryk.charity_android.ui.activity.DonationRecordActivity
import com.github.rogeryk.charity_android.ui.login.LoginActivity
import com.github.rogeryk.charity_android.utils.BlurTransformation
import com.github.rogeryk.charity_android.utils.CircleTransform
import com.github.rogeryk.charity_android.utils.castTo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class MyFragment : Fragment(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    lateinit var userModal: UserModal
        @Inject set

    lateinit var api: Api
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.let { it.application.castTo<App>().appComponent.inject(this) }

        return inflater.inflate(R.layout.fragment_my, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        userModal.isLogined.observe(this, Observer { userModalListen(it) })
        pullUserInfo()
    }

    override fun onStart() {
        super.onStart()
        if (userModal.isLogined.value != true) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun userModalListen(isLogined: Boolean) {
        if (isLogined) {
            pullUserInfo()
        }
    }

    private fun setListeners() {
        user_donated_num.setOnClickListener {
            startActivity(Intent(context, DonationRecordActivity::class.java))
        }
    }

    fun pullUserInfo() = launch {
        if (userModal.isLogined.value != true) return@launch
        val res = api.user.currentUser().await()
        if (res.errCode == 0) {
            val user = res.data!!
            userModal.currentUser = user
            Picasso.with(context)
                    .load(user.avatar)
                    .transform(BlurTransformation(context!!))
                    .into(header_blur_bg)
            Picasso.with(context)
                    .load(user.avatar)
                    .transform(CircleTransform())
                    .into(user_icon)
            user_focus_num.text = user.favorUserCount.toString()
            user_donated_num.text = "捐款记录 ${user.donatedCount}"
            user_released_count.text = "发起的项目 ${user.releasedProjectCount}"
        }
    }


}
