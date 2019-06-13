package com.github.rogeryk.charity_android.dagger

import com.github.rogeryk.charity_android.ui.DonationRecordActivity
import com.github.rogeryk.charity_android.ui.NewsActivity
import com.github.rogeryk.charity_android.ui.ProjectDetailActivity
import com.github.rogeryk.charity_android.ui.fragment.HomeFragment
import com.github.rogeryk.charity_android.ui.fragment.MyFragment
import com.github.rogeryk.charity_android.ui.fragment.NewsFragment
import com.github.rogeryk.charity_android.ui.fragment.SearchFragment
import com.github.rogeryk.charity_android.ui.login.LoginActivity
import com.github.rogeryk.charity_android.ui.view.CommentListView
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class, UserModule::class, LoginViewModalModule::class, ApplicationModule::class])
@Singleton
interface AppComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(loginActivity: LoginActivity)

    fun inject(newsActivity: NewsActivity)

    fun inject(newsActivity: NewsFragment)

    fun inject(projectDetailActivity: ProjectDetailActivity)

    fun inject(myFragment: MyFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(donationRecordActivity: DonationRecordActivity)

    fun inject(commentListView: CommentListView)
}