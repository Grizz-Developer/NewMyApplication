package com.guit.edu.myapplication

import com.guit.edu.myapplication.entity.History
import com.guit.edu.myapplication.entity.LoginResult
import com.guit.edu.myapplication.entity.RegisterResult
import com.guit.edu.myapplication.entity.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.Body

interface RetrofitApi {
    @POST("user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<LoginResult>

    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<RegisterResult>


    @GET("user/info")
    fun getUserInfo(@Header("Authorization") token : String): Observable<User>


    @GET("user/history")
    fun getUserHistory(@Query("username") username : String, @Query("date") date : String): Observable<List<History>>

    @POST("user/info/update")
    fun updateUserInfo(@Query("id") id: Int, @Body user: User): Observable<User>

}
