package com.github.rogeryk.charity_android

import android.app.Application
import com.github.rogeryk.charity_android.dagger.AppComponent
import com.github.rogeryk.charity_android.dagger.ApplicationModule
import com.github.rogeryk.charity_android.dagger.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}