package com.github.rogeryk.charity_android.modal

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.data.UserInfo

class UserModal(private val app: App) {


    var isLogined: MutableLiveData<Boolean> = MutableLiveData()

    var accessToken: String? = null

    lateinit var currentUser: UserInfo

    init {
        readUserModalFromLocal()
        isLogined.observeForever {if (it) writeUserModalToLocal()}
    }


    private fun readUserModalFromLocal() {
        val sharePre = app.getSharedPreferences("user", Context.MODE_PRIVATE)
        val accessToken = sharePre.getString("access_token", null)
        val accessTime = sharePre.getLong("access_token_time", -1)
        if (accessToken != null && accessTime != -1L) {
            val now = System.currentTimeMillis()
            if (now - accessTime < 2* 60 * 60 * 1000) {
                this.accessToken = accessToken
                isLogined.value = true
            }
        }
    }

    private fun writeUserModalToLocal() {
        val sharePre = app.getSharedPreferences("user", Context.MODE_PRIVATE)
        sharePre.edit().apply {
            if (accessToken != null) {
                putString("access_token", accessToken)
                putLong("access_token_time", System.currentTimeMillis())
                apply()
            }
        }

    }
}
