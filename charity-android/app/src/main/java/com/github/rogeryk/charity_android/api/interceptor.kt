package com.github.rogeryk.charity_android.api

import android.util.Log
import com.github.rogeryk.charity_android.modal.UserModal
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor: Interceptor {

    var userModal: UserModal

    @Inject
    constructor(userModal: UserModal){
        this.userModal = userModal
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        userModal.accessToken?.let {
            Log.i("auth", it)
            request = request.newBuilder()
                    .addHeader("access_token", it)
                    .build()
        }
        Log.i("request body", request.body()?.toString() ?: "")
        return  chain.proceed(request)

    }

}