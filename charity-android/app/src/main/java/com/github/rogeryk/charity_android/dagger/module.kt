package com.github.rogeryk.charity_android.dagger

import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.api.*
import com.github.rogeryk.charity_android.modal.UserModal
import com.github.rogeryk.charity_android.ui.login.LoginViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: App) {

    @Singleton
    @Provides
    fun provideApp(): App {
        return app
    }
}

@Module
class UserModule {

    @Singleton
    @Provides
    fun provideUserModal(app: App): UserModal {
        return UserModal(app)
    }
}

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApi(interceptor: AuthInterceptor): Api {
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return Api(
                builder.create(BannerApi::class.java),
                builder.create(ProjectApi::class.java),
                builder.create(CommentApi::class.java),
                builder.create(NewsApi::class.java),
                builder.create(AuthApi::class.java),
                builder.create(UserApi::class.java),
                builder.create(TransactionApi::class.java)
        )
    }

}


@Module
class LoginViewModalModule {

    @Singleton
    @Provides
    fun provideLoginViewModal(api: Api): LoginViewModel {
        return LoginViewModel(api)
    }
}


