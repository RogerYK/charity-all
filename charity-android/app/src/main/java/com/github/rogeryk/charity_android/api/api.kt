package com.github.rogeryk.charity_android.api

import com.github.rogeryk.charity_android.data.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*


const val BASE_URL = "http://192.168.1.112:8080/"

interface AuthApi {

    @POST("/api/auth/login")
    fun login(@Body form: LoginForm): Deferred<Response<String>>


}

interface UserApi {

    @GET("/api/user/current")
    fun currentUser(): Deferred<Response<UserInfo>>
}


interface BannerApi {

    @GET("/api/banner/all")
    fun all(): Deferred<Response<List<Banner>>>
}

interface ProjectApi {

    @GET("/api/project/hot")
    fun hot(@Query("n") n:Int=4): Deferred<Response<List<ProjectBasic>>>

    @GET("/api/project/recommend")
    fun recommend(@Query("n") n:Int=6): Deferred<Response<List<ProjectBasic>>>

    @GET("/api/project/")
    fun detail(@Query("id") id: Long): Deferred<Response<ProjectDetail>>

    @GET("/api/project/byName")
    fun byName(@Query("name") name: String): Deferred<Response<Page<ProjectBasic>>>
}

interface CommentApi {


    @GET("/api/comment/byProjectId")
    fun byProjectId(@Query("projectId") id:Long,
                    @Query("page") page:Int=0,
                    @Query("size") size:Int=12,
                    @Query("direction") direction:String="desc",
                    @Query("field") field:String="createdTime"
    ): Deferred<Response<Page<Comment>>>

    @POST("/api/comment/")
    fun save(@Body form: CommentForm): Deferred<Response<Unit>>
}

interface NewsApi {

    @GET("/api/news/hot")
    fun hot(): Deferred<Response<List<NewsBasic>>>

    @GET("/api/news/latest")
    fun latest(): Deferred<Response<List<NewsBasic>>>

    @GET("/api/news/")
    fun byId(@Query("id") id: Long): Deferred<Response<NewsDetail>>
}

interface TransactionApi {

    @POST("/api/transaction/donation")
    fun donate(@Body donationForm: DonationForm): Deferred<Response<Unit>>

    @GET("/api/transaction/donation")
    fun donation(@Query("userId") userId: Long, @QueryMap pageParam: Map<String, String> = mapOf()): Deferred<Response<Page<Transaction>>>
}

data class Api(
        val banner: BannerApi,
        val project: ProjectApi,
        val comment: CommentApi,
        val news: NewsApi,
        val auth: AuthApi,
        val user: UserApi,
        val transaction: TransactionApi
)


