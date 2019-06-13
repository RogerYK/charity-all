package com.github.rogeryk.charity_android.api


import kotlinx.coroutines.runBlocking
import org.junit.Test




class BannerTest {
       @Test
       fun all() {
           val api = Api.getApiService()
           runBlocking {
               val res = api.banner.all().await()
               print(res)
           }

       }
}

